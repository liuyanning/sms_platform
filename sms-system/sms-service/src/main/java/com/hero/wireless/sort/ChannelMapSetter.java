package com.hero.wireless.sort;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.DataStatus;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ProductStatus;
import com.hero.wireless.sort.diversion.impl.SignatureDiversion;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @version V3.0.0
 * @description: 设置产品路由通道
 * @author: 刘彦宁
 * @date: 2020年09月17日14:37
 **/
public class ChannelMapSetter {

    /**
     * 初始化通道,channelMap的key是SMS(短信类型) + China_unicom(运营商)
     *
     * @param ctx
     * @author volcano
     * @date 2019年9月14日上午11:30:12
     * @version V1.0
     */
    public static boolean initChannelMap(SortContext ctx) {
        SortChannelMap channelMap = new SortChannelMap();
        ctx.setChannelMap(channelMap);
        if (ctx.getEnterpriseUser() == null) {
            SuperLogger.warn("user is null =====>" + ctx.getInput());
            return false;
        }
        String productNo = StringUtils.defaultIfEmpty(ctx.getInput().getProduct_No(),
                ctx.getEnterpriseUser().getProduct_No());
        //重发的产品编号
        if (DataStatus.RESEND.toString().equals(ctx.getInput().getData_Status_Code())) {
            productNo = ctx.getEnterpriseUser().getBackup_Product_No();
        }

        if (StringUtils.isEmpty(productNo)) {
            return false;
        }
        Product product = DatabaseCache.getProductCachedByNo(productNo);
        if (product == null || !ProductStatus.START.toString().equalsIgnoreCase(product.getStatus_Code())) {
            return false;
        }
        ctx.setProduct(product);
        ctx.getInputLog().setProduct_No(productNo);
        // 拦截策略
        int interceptStrategyId = ObjectUtils.defaultIfNull(product.getIntercept_Strategy_Id(), 0);
        if (interceptStrategyId > 0) {
            ctx.setInterceptStrategy(DatabaseCache.getInterceptStrategyCachedById(interceptStrategyId));
        }
        // 获取产品配置的所有通道
        List<ProductChannels> productChannels = DatabaseCache.getProductCachedChannels(productNo);
        productChannels.forEach(productChannel -> {
            Channel channel = DatabaseCache.getChannelCachedByNo(productChannel.getChannel_No());
            if(channel == null){
                return;
            }
            SortChannelMap.Key key = channelMap.newKey(productChannel.getMessage_Type_Code(), productChannel.getOperator());

            //根据通道对应的运营商获取用户设置的费用
            EnterpriseUserFee userFee = DatabaseCache.getEnterpriseUserFeeCached(ctx.getInput().getEnterprise_No(),
                    ctx.getInput().getEnterprise_User_Id(), productChannel.getMessage_Type_Code(),
                    productChannel.getOperator());
            if (userFee == null || userFee instanceof SMSUtil.NullEnterpriseUserFee) {
                return;
            }

            //判断通道是否支持emoji符号
            if (SMSUtil.containsEmoji(ctx.getInput().getContent())) {
                Map<String, ChannelUtil.OtherParameter> otherParameters = ChannelUtil.getParameter(channel);
                boolean isSupportEmoji = ChannelUtil.getParameterBooleanValue(otherParameters,
                        "is_support_emoji", true);
                if(!isSupportEmoji){
                    return;
                }
            }

            String signatureContent = "";
            if (MessageType.SMS.toString().equalsIgnoreCase(ctx.getInput().getMessage_Type_Code())) {
                signatureContent = ctx.getInput().getContent();
            }
            //根据短信内容获取签名
            String signature = SMSUtil.getSignature(signatureContent);
            ctx.setSignature(signature);
            SortChannelMap.SortChannel sortChannel = channelMap.new SortChannel(channel, userFee, productChannel);
            // 初始化扩展码,根据签名导流获取扩展码
            sortChannel.setSubCode(getSubCode(sortChannel, ctx));
            channelMap.putChannel(key, sortChannel);
        });
        return true;
    }

    /**
     *
     * @param sortChannel
     * @param ctx
     * @author volcano
     * @date 2019年10月2日下午7:49:32
     * @version V1.0
     */
    private static String getSubCode(SortChannelMap.SortChannel sortChannel, SortContext ctx) {
        String defaultSubCode = SMSUtil.getSubCode(ctx.getEnterpriseUser().getSub_Code(), ctx.getInput().getSub_Code(), sortChannel.getChannel());
        try {
            ChannelUtil.IncludeSignature include = (ChannelUtil.IncludeSignature) new SignatureDiversion().diversion(ctx,
                    sortChannel);
            if (include == null) {
                return defaultSubCode;
            }
            String userSubCode = StringUtils.defaultString(ctx.getEnterpriseUser().getSub_Code(), "");
            String includeSubCode = StringUtils.defaultString(include.getSubCode(), "");
            return SMSUtil.getSubCode(includeSubCode + userSubCode, ctx.getInput().getSub_Code(), sortChannel.getChannel());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return defaultSubCode;
        }
    }
}
