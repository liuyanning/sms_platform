package com.hero.wireless.sort;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ChannelStatus;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.SignatureType;
import com.hero.wireless.sort.diversion.ISortDiversionService;
import com.hero.wireless.sort.diversion.impl.AreaDiversion;
import com.hero.wireless.sort.diversion.impl.ContentLengthLimit;
import com.hero.wireless.sort.diversion.impl.ExcludeKeywordDiversion;
import com.hero.wireless.sort.diversion.impl.IncludeKeywordDiversion;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.MobileArea;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;


/**
 * @version V3.0.0
 * @description: 过滤合法channel
 * @author: 刘彦宁
 * @date: 2020年09月17日17:38
 **/
public class SortChannelPredicate {

    private SortContext ctx;
    private String phone;
    private boolean isWhite;

    public SortChannelPredicate(SortContext ctx, String phone, boolean isWhite) {
        this.ctx = ctx;
        this.phone = phone;
        this.isWhite = isWhite;
    }

    /**
     * 校验channel是否合法，通过校验则作为备选通道
     * @param channel
     * @return
     */
    public boolean test(SortChannelMap.SortChannel channel) {
        if (!channel.getChannel().getStatus_Code().equalsIgnoreCase(ChannelStatus.START.toString())) {
            return false;
        }
        ProductChannels productChannel = channel.getProductChannels();

        // 导流策略区域限制
        if (!isWhite && !((ISortDiversionService.BooleanResult) new AreaDiversion().diversion(ctx, productChannel, phone)).isResult()) {
            channel.setErrorMsg(channel.getErrorMsg().append("导流策略，区域限制;"));
            return false;
        }

        // 通道区域限制
        if (!isWhite && !checkChannelAreaCode(ctx, channel.getChannel(), phone)) {
            channel.setErrorMsg(channel.getErrorMsg().append("通道区域限制;"));
            return false;
        }

        // 内容不匹配
        if (!isWhite && !contentRule(ctx, channel)) {
            channel.setErrorMsg(channel.getErrorMsg().append("导流策略，内容限制;"));
            return false;
        }
        // 检查通道限制
        int limitChannelMinute = ctx.getSortLimitRepeat().validateRepeatPhoneNo(phone, channel);
        if (!isWhite && limitChannelMinute > 0) {
            channel.setErrorMsg(channel.getErrorMsg().append("导流策略，同手机号超限;"));
            return false;
        }
        limitChannelMinute = ctx.getSortLimitRepeat().validateRepeatContent(phone, channel);
        if (!isWhite && limitChannelMinute > 0) {
            channel.setErrorMsg(channel.getErrorMsg().append("导流策略，同内容超限;"));
            return false;
        }

        // 普通短信 通道支持的字符数，默认1000
        if (((ISortDiversionService.BooleanResult) new ContentLengthLimit().diversion(ctx, productChannel)).isResult()) {
            SuperLogger.warn("超过通道支持的最大字数");
            channel.setErrorMsg(channel.getErrorMsg().append("导流策略，内容长度超限;"));
            return false;
        }

        Input input = ctx.getInput();
        int signatureLength = StringUtils.isEmpty(ctx.getSignature())? -1 : ctx.getSignature().length();
        // 签名长度判断
        int limtSignatureLength = NumberUtils
                .toInt(ChannelUtil.getParameter(channel.getChannel(), "signature_length", "-1"), -1);
        if (MessageType.SMS.toString().equalsIgnoreCase(input.getMessage_Type_Code())
                && limtSignatureLength != -1 && signatureLength != -1
                && limtSignatureLength < signatureLength) {
            SuperLogger.warn("签名长度超过通道设置的最大长度值");
            channel.setErrorMsg(channel.getErrorMsg().append("签名长度超限;"));
            return false;
        }

        if (StringUtils.isEmpty(ctx.getSignature()) &&
                StringUtils.defaultIfEmpty(channel.getChannel().getSignature_Direction_Code(), SignatureType.CUSTOMIZE.toString())
                        .equalsIgnoreCase(SignatureType.CHANNEL_FIXED.toString())) {
            channel.setErrorMsg(channel.getErrorMsg().append("固签通道无签名;"));
            return false;
        }
        return true;
    }

    /**
     * 校验通道区域限制 2020.10.30
     */
    private boolean checkChannelAreaCode(SortContext ctx, Channel channel, String phone) {
        if (StringUtils.isEmpty(channel.getArea_Code())) {
            return true;//无限制
        }
        MobileArea mobileArea = ctx.getMobileAreas().get(phone);
        boolean isMatch = Arrays.asList(channel.getArea_Code().split(",")).stream().anyMatch(item -> {
            if (item.equalsIgnoreCase("all")) {
                return true;
            }
            if (StringUtils.isEmpty(mobileArea.getProvince_Code())) {
                SuperLogger.error("号码归属地为空："+ phone);
                return false;
            }
            return mobileArea.getProvince_Code().equalsIgnoreCase(item);
        });
        return isMatch;
    }

    private boolean contentRule(SortContext ctx, SortChannelMap.SortChannel sortChannel) {
        // 通道敏感字检测
        boolean sensitivesResult = ((ISortDiversionService.BooleanResult) new ExcludeKeywordDiversion().diversion(ctx, sortChannel))
                .isResult();
        if (sensitivesResult) {
            return false;
        }
        // 必须包含某类字符,例如给某企业单独申请的通道，只有固定的签名才可以
        return ((ISortDiversionService.BooleanResult) new IncludeKeywordDiversion().diversion(ctx, sortChannel)).isResult();
    }
}
