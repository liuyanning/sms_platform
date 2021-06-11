package com.hero.wireless.sort;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.DataStatus;
import com.hero.wireless.enums.ReportNativeStatus;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.remote.filter.IPhoneNoFilterService;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.util.BlackListUtil;
import com.hero.wireless.web.util.DFA;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @version V3.0.0
 * @description: 分拣的校验
 * @author: 刘彦宁
 * @date: 2020年09月17日14:50
 **/
public class SortValidator {

    /**
     * 基础数据和配置校验
     * @param ctx
     * @return
     */
    public static boolean baseValidator(SortContext ctx) {

        //检测产品
        if (ObjectUtils.isEmpty(ctx.getProduct())) {
            addAllPhone2FaildList(ctx, ReportNativeStatus.DD0001, "未配置产品");
            return false;
        }

        //检测敏感字
        if (!checkContent(ctx)) {
            return false;
        }

        return true;
    }

    public static boolean checkContent(SortContext ctx) {
        // 如果拦截策略为空，直接返回true
        if (ctx.getInterceptStrategy() == null) {
            return true;
        }
        // 如果敏感词池为空直接返回true
        if (StringUtils.isEmpty(ctx.getInterceptStrategy().getSensitive_Word_Pool_Code())) {
            return true;
        }
        // 敏感字
        DFA dfa = DatabaseCache.getDFA(ctx.getInterceptStrategy().getSensitive_Word_Pool_Code());
        if (dfa == null) {
            return true;
        }
        // 内容中是否存在敏感字
        List<String> sensitives = dfa.searchWord(ctx.getInput().getContent());
        if (ObjectUtils.isEmpty(sensitives)) {
            return true;
        }
        // 如果是模版内容直接返回true;
        if(SMSUtil.matchSmsTemplate(ctx.getEnterpriseUser(), ctx.getInput().getContent())){
            return true;
        }
        String composeSensitive = StringUtils.join(sensitives, ",");
        ctx.setContainsSensitives(composeSensitive);
        if (StringUtils.isNotEmpty(ctx.getContainsSensitives())) {
            addAllPhone2FaildList(ctx, ReportNativeStatus.DD0002, "敏感字(" + ctx.getContainsSensitives() + ")");
            return false;
        }
        return false;
    }

    /**
     * 校验号码是否合法
     * @param ctx
     * @param input
     * @param phone
     * @return
     */
    public static boolean checkPhone(SortContext ctx, Input input, String phone) {
        if (StringUtils.isEmpty(phone)) {
            ctx.addFaild(phone, ReportNativeStatus.DD0003, "号码为空");
            return false;
        }

        if (phone.length() != 11 && input.getCountry_Code().equals("86")) {
            // 号码长度错误错误
            ctx.addFaild(phone, ReportNativeStatus.DD0004, "号码长度错误");
            return false;
        }
        // 号码错误
        if (!NumberUtils.isDigits(phone) && input.getCountry_Code().equals("86")) {
            ctx.addFaild(phone, ReportNativeStatus.DD0004, "号码不是数字");
            return false;
        }
        return true;
    }

    /**
     * 校验黑名单和内容和手机号发送频率（用户频率）
     * @param ctx
     * @param input
     * @param phone
     * @return
     */
    public static boolean checkBlackAndRepeat(SortContext ctx, Input input, String phone) {
        //重发的不拦截黑名单和频次
        if (DataStatus.RESEND.toString().equals(ctx.getInput().getData_Status_Code())) {
            return true;
        }

        //是否根据黑名单拦截策略拦截
        boolean isFilterBlack = false;
        String poolCode = null;
        if (ctx.getInterceptStrategy() != null) {
            poolCode = ctx.getInterceptStrategy().getBlack_Pool_Code();
            isFilterBlack = true;
        }
        //是否需要回T的策略判断
        boolean isAutoFilterBlack = ctx.isBlackSwitch();

        //要判断黑名单
        if (isFilterBlack || isAutoFilterBlack) {
            String phoneNo = ctx.getInput().getCountry_Code() + phone;
            boolean isBlack = BlackListUtil.isBlack(phoneNo, poolCode, input.getEnterprise_No(),
                    input.getEnterprise_User_Id(), isFilterBlack, isAutoFilterBlack);
            if(isBlack){
                ctx.addFaild(phone, ReportNativeStatus.DD0006, "黑名单");
                return false;
            }
        }

        //配置了拦截策略要再次判断是否配置外部黑名单
        if(isFilterBlack) {
            boolean isBlack = filterOutBlack(ctx, phone, input);
            if(isBlack){
                ctx.addFaild(phone, ReportNativeStatus.DD0006, "黑名单");
                return false;
            }
        }

        int limitMinute = 0;
        // 相同内容重复发送限制=======================================================
        limitMinute = ctx.getSortLimitRepeat().validateRepeatContent(phone);
        if (limitMinute > 0) {
            ctx.addFaild(phone, ReportNativeStatus.DD0007, limitMinute + "分钟内内容重复");
            return false;
        }
        // 相同号码每天发送次数限制------------------------------------------------------------------------------------
        limitMinute = ctx.getSortLimitRepeat().validateRepeatPhoneNo(phone);
        if (limitMinute > 0) {
            ctx.addFaild(phone, ReportNativeStatus.DD0008, limitMinute + "分钟内号码重复");
            return false;
        }
        return true;
    }

    /**
     *
     * 黑名单过滤 如果是白名单池里面的号码，直接返回false，不再进行黑名单的检查 如果在内部黑名单池中，直接返回true是黑名单
     * 如果配置了外部黑名单，先检查是否在本地缓存的外部黑名单中，如果存在，直接返回true 如果不存在，调用外部黑名单接口查询，如果是黑名单直接返回true
     *
     * @param ctx
     * @param phoneNo
     * @param input
     * @return
     */
    private static boolean filterOutBlack(SortContext ctx, String phoneNo, Input input) {
        // 根据国家编号+手机号去匹配
        phoneNo = ctx.getInput().getCountry_Code() + phoneNo;
        // 是否配置了检查外部黑名单
        String externalBlackCode = ctx.getInterceptStrategy().getExternal_Black_Pool();
        if (ObjectUtils.isEmpty(externalBlackCode)) {
            return false;
        }

        // 本地缓存外部黑名单
        String key = DatabaseCache.genRedisKey(Constant.REDIS_KEY_EXTERNAL_BLACK_DIR_NAME, phoneNo);
        if (DatabaseCache.hashCacheBlackListByNumber(key)) {
            SuperLogger.error("缓存内部黑名单");
            ctx.addFaild(phoneNo, ReportNativeStatus.DD0006, "外部黑名单");
            return true;
        }
        // 外部黑名单
        Code code = DatabaseCache.getCodeByNo(externalBlackCode);
        if (code != null) {
            try {
                Map<String, String> resultMap = JsonUtil.STANDARD.readValue(code.getValue(),
                        new TypeReference<Map<String, String>>() {
                        });
                String fullClassImpl = resultMap.get("fullClassImpl");
                IPhoneNoFilterService filterService = (IPhoneNoFilterService) Class.forName(fullClassImpl)
                        .newInstance();
                filterService.init(resultMap);
                boolean isExternalBlack = filterService.filterPhoneNo(phoneNo);
                if (isExternalBlack) {
                    ctx.addFaild(phoneNo, ReportNativeStatus.DD0006, "外部黑名单");
                    return true;
                }
            } catch (Exception e) {
                SuperLogger.error("外部黑名单检查异常，直接通过", e);
                return false;
            }
        }
        return false;
    }

    /**
     * 基础检测不通过，所有号码都回执错误
     * @param ctx
     * @param status
     * @param failMsg
     */
    private static void addAllPhone2FaildList(SortContext ctx, ReportNativeStatus status, String failMsg) {
        Input input = ctx.getInput();
        String[] mobiles = input.getPhone_Nos().split(SortContext.MUTL_MOBILE_SPLIT);
        ctx.getInputLog().setPhone_Nos_Count(mobiles.length);
        Arrays.asList(mobiles).forEach(phone -> {
            ctx.addFaild(phone, status, failMsg);
        });
    }
}
