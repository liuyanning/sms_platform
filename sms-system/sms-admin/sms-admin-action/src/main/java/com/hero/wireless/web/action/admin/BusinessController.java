package com.hero.wireless.web.action.admin;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.AuditStatus;
import com.hero.wireless.enums.MaterialType;
import com.hero.wireless.json.*;
import com.hero.wireless.web.action.AdminControllerBase;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.service.*;
import com.hero.wireless.web.util.UploadUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/")
public class BusinessController extends BaseAdminController {

    @Resource(name = "businessManage")
    private IBusinessManage businessManage;
    @Resource(name = "sendManage")
    private ISendManage sendManage;
    @Resource(name = "materialManage")
    private IMaterialManage materialManage;
    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseManage;

    @InitBinder
    public void initDateFormate(WebDataBinder dataBinder) {
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }
    //?????????????????????
    @RequestMapping("business_preEditBlackList")
    public ModelAndView preBlackList(BaseParamEntity entity, @RequestParam Map<String, String> conditionMap) {
        ModelAndView mv = new ModelAndView("/business/black_add");
        if (entity.getCkIds().size() > 0) {
            Inbox inbox = sendManage.queryInboxByPrimaryKey(entity.getCkIds().get(0));
            mv.addObject("inbox", inbox);
        }
        return mv;
    }

    /**
     * ???????????????
     */
    @RequestMapping("business_addBlack")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addBlack")
    public LayUiJsonObjectFmt addBlack(BlackList black) {
        black.setCreate_User(getLoginRealName());
        if (black.getPhone_No().isEmpty()){
            return LayuiResultUtil.fail("?????????????????????");
        }
        try {
            this.businessManage.addBlackList(black);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_removeBlack")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    public LayUiJsonObjectFmt removeBlack(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.businessManage.deleteBlackByIdList(ckIds);
        return LayuiResultUtil.success();
    }

    //???????????????
    @RequestMapping("business_importBlackList")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_importBlackList")
    public LayUiJsonObjectFmt importBlackList(BlackList blackList,
                                              @RequestParam(value = "phoneNosFile") MultipartFile phoneNosFile) {
        try {
            blackList.setCreate_User(getLoginRealName());
            blackList.setRemark(getLoginRealName() + "???????????????");
            this.businessManage.importBlackList(blackList, phoneNosFile);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }


    /**
     * admin-->????????????-->?????????
     */
    @RequestMapping("business_blackList")
    @ResponseBody
    public String blackList(BlackListExt black) {
        List<BlackListExt> blackList = businessManage.queryBlackList(black);
        return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(blackList, black.getPagination().getTotalCount());
    }

    /**
     * ??????????????????
     */
    @RequestMapping("business_codeList")
    @ResponseBody
    public String codeList(CodeExt code) {
        List<CodeExt> list = businessManage.queryCodeList(code);
        return new LayUiObjectMapper().asSuccessString(list, code.getPagination().getTotalCount());
    }

    /**
     * ??????sortCode??????????????????
     */
    @RequestMapping("{sortCode}/business_codeListBySortCode")
    @ResponseBody
    public String queryCodeListBySortCode(@PathVariable String sortCode) {
        List<? extends Code> list = DatabaseCache.getCodeListBySortCode(sortCode);
        return new LayUiObjectMapper().asSuccessString(list);
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping("business_addCode")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addCode")
    public LayUiJsonObjectFmt addCode(CodeExt code) {
        businessManage.addCode(code);
        return LayuiResultUtil.success();
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping("business_editCode")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    public LayUiJsonObjectFmt editCodeSave(CodeExt code) {
        businessManage.editCode(code);
        return LayuiResultUtil.success();
    }

    /**
     * ????????????????????????
     */
    @RequestMapping("business_codeSortList")
    @ResponseBody
    public String codeSortList(CodeSort codeSort) {
        List<CodeSort> list = businessManage.queryCodeSortList(codeSort);
        return new LayUiObjectMapper().asSuccessString(list, codeSort.getPagination().getTotalCount());
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    @RequestMapping("business_preEditCodeSort")
    public ModelAndView preEditCodeSort(BaseParamEntity entity) {
        ModelAndView mv = new ModelAndView("/business/code_sort_edit");
        CodeSort codeSort = new CodeSort();
        codeSort.setId(entity.getCkIds().get(0));
        List<CodeSort> codeSorts = businessManage.queryCodeSortList(codeSort);
        if (codeSorts != null && !codeSorts.isEmpty()) {
            mv.addObject("codeSort", codeSorts.get(0));
        }
        return mv;
    }

    /**
     * ??????????????????
     */
    @RequestMapping("business_addCodeSort")
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addCodeSort")
    public @ResponseBody
    LayUiJsonObjectFmt addCodeSort(CodeSort codeSort) {
        businessManage.addCodeSort(codeSort);
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_sensitiveWordList")
    @ResponseBody
    public String sensitiveWordList(SensitiveWordExt sensitiveWord) {
        List<SensitiveWord> sensitiveWordList = businessManage.querySensitiveWordList(sensitiveWord);
        return new LayUiObjectMapper().asSuccessString(sensitiveWordList,
                sensitiveWord.getPagination().getTotalCount());
    }

    @RequestMapping("business_smsRouteList")
    @ResponseBody
    public String smsRouteList(SmsRoute smsRoute) {
        List<SmsRoute> smsRouteList = businessManage.querySmsRouteList(smsRoute);
        return new LayUiObjectMapper().asSuccessString(smsRouteList, smsRoute.getPagination().getTotalCount());
    }

    @RequestMapping("business_operatorListByCountry")
    @ResponseBody
    public String operatorListByCountry(String countryNumber) {
        List<SmsRouteExt> smsRouteList = businessManage.queryOperatorListByCountry(countryNumber);
        return new LayUiObjectMapper().asSuccessString(smsRouteList);
    }

    @RequestMapping("business_addSmsRoute")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addSmsRoute")
    public LayUiJsonObjectFmt addSmsRoute(SmsRoute smsRoute) {
        try {
            this.businessManage.addSmsRoute(smsRoute);
        } catch (BaseException e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_editSmsRoute")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_editSmsRoute")
    public LayUiJsonObjectFmt editSmsRoute(SmsRoute smsRoute) {
        try {
            this.businessManage.editSmsRoute(smsRoute);
        } catch (BaseException e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_preEditSmsRoute")
    public String preEditSmsRoute(BaseParamEntity entity) {
        SmsRoute smsRoute = new SmsRoute();
        smsRoute.setId(entity.getCkIds().get(0));
        List<SmsRoute> smsRouteList = businessManage.querySmsRouteList(smsRoute);
        if(smsRouteList.size() == 1){
            smsRoute = smsRouteList.get(0);
        }
        request.setAttribute("smsRoute",smsRoute);
        return "/business/sms_route_edit";
    }

    @RequestMapping("business_deleteSmsRouteList")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_deleteSmsRoute")
    public LayUiJsonObjectFmt deleteSmsRoute(BaseParamEntity entity) {
        try {
            this.businessManage.deleteSmsRoute(entity.getCkIds());
        } catch (BaseException e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * ?????????????????????
     */
    @RequestMapping("business_initSmsRouteCache")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "?????????????????????")
    public LayUiJsonObjectFmt initSmsRouteCache() {
        try {
            businessManage.initSmsRouteCache();
        } catch (Exception e) {
            SuperLogger.error("??????????????????????????????" + e.getMessage(), e);
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_addSensitiveWord")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addSensitiveWord")
    public LayUiJsonObjectFmt addSensitiveWord(SensitiveWord sensitiveWord) {
        try {
            sensitiveWord.setCreate_User(getLoginRealName());
            this.businessManage.addSensitiveWord(sensitiveWord);
        } catch (BaseException e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * ???????????????
     *
     * @param mobileArea
     * @return
     */
    @RequestMapping("business_mobileAreaList")
    @ResponseBody
    public String mobileAreaList(MobileArea mobileArea) {
        List<MobileArea> mobileAreaList = businessManage.queryMobileAreaList(mobileArea);
        return new LayUiObjectMapper().asSuccessString(mobileAreaList, mobileArea.getPagination().getTotalCount());
    }

    /**
     * ?????????????????????
     */
    @RequestMapping("business_initmobileAreaCache")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "?????????????????????")
    public LayUiJsonObjectFmt initmobileAreaCache() {
        try {
            businessManage.initMobileAreaCache();
        } catch (Exception e) {
            SuperLogger.error("??????????????????????????????" + e.getMessage(), e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * ??????????????????
     */
    @RequestMapping("business_editCodeSort")
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public @ResponseBody
    LayUiJsonObjectFmt editCodeSortSave(CodeSort codeSort) {
        businessManage.editCodeSort(codeSort);
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_deleteCode")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    public LayUiJsonObjectFmt deleteCode(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.businessManage.deleteCode(ckIds);
        return LayuiResultUtil.success();
    }

    /**
     * ??????????????????
     */
    @RequestMapping("business_deleteCodeSort")
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public @ResponseBody
    LayUiJsonObjectFmt deleteCodeSort(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        businessManage.deleteCodeSort(ckIds);
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_deleteSensitiveWord")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    public LayUiJsonObjectFmt deleteSensitiveWord(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.businessManage.deleteSensitiveWord(ckIds);
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_preEditMobileArea")
    public String preEditMobileArea(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        if (ckIds == null || ckIds.isEmpty()) {
            return "/business/edit_mobile_area";
        }
        MobileArea mobileArea = new MobileArea();
        mobileArea.setId(ckIds.get(0));
        List<MobileArea> mobileAreaList = this.businessManage.queryMobileAreaList(mobileArea);
        if (mobileAreaList == null || mobileAreaList.isEmpty()) {
            return "/business/edit_mobile_area";
        }
        mobileArea = mobileAreaList.get(0);
        request.setAttribute("mobileArea", mobileArea);
        return "/business/edit_mobile_area";
    }

    @RequestMapping("business_addMobileArea")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "?????????????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addMobileArea")
    public LayUiJsonObjectFmt addMobileArea(MobileArea mobileArea) {
        try {
            this.businessManage.addMobileArea(mobileArea);
        } catch (ServiceException e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_editMobileArea")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "?????????????????????")
    public LayUiJsonObjectFmt editMobileArea(MobileArea mobileArea) {
        this.businessManage.editMobileArea(mobileArea);
        return LayuiResultUtil.success();
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping("business_preEditCode")
    public String preEditCode(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        CodeExt code = new CodeExt();
        code.setId(ckIds.get(0));
        List<CodeExt> codeList = businessManage.queryCodeList(code);
        if (codeList == null || codeList.isEmpty()) {
            request.setAttribute("codeList", codeList);
            return "/business/code_edit";
        }
        code = codeList.get(0);
        request.setAttribute("code", code);
        return "/business/code_edit";
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping("business_closeCode")
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public @ResponseBody
    LayUiJsonObjectFmt closeCode(@RequestParam(value = "ckIds") List<Integer> ckIds) {
//		businessManage.editCode(ckIds);
        return LayuiResultUtil.success();
    }

    /**
     * ???????????????
     */
    @RequestMapping("business_addWhite")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addWhite")
    public LayUiJsonObjectFmt addWhite(WhiteList white) {
        white.setCreate_User(getLoginRealName());
        white.setCreate_User_Id(getUserId());
        if (white.getPhone_No().isEmpty()){
            return LayuiResultUtil.fail("?????????????????????");
        }
        try {
            this.businessManage.addWhiteList(white);
        } catch (Exception e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_removeWhite")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    public LayUiJsonObjectFmt removeWhite(BaseParamEntity entity) {
        this.businessManage.deleteWhiteList(entity.getCkIds());
        return LayuiResultUtil.success();
    }

    //???????????????
    @RequestMapping("business_importWhiteList")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_importWhiteList")
    public LayUiJsonObjectFmt importWhiteList(WhiteList whiteList,
                                              @RequestParam(value = "phoneNosFile") MultipartFile phoneNosFile) {
        try {
            whiteList.setCreate_User(getLoginRealName());
            whiteList.setCreate_User_Id(getLoginUser().getId());
            whiteList.setRemark(getLoginRealName() + "???????????????");
            this.businessManage.importWhiteList(whiteList, phoneNosFile);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * admin-->????????????-->?????????
     */
    @RequestMapping("business_whiteList")
    @ResponseBody
    public String whiteList(WhiteList white, @RequestParam Map<String, String> parMap) {
        List<WhiteList> whiteLists = businessManage.queryWhiteList(white, parMap);
        return new LayUiObjectMapper().asSuccessString(whiteLists, white.getPagination().getTotalCount());
    }

    /**
     * admin-->????????????-->?????????-->??????????????????
     */
//    @RequestMapping("business_initWhiteListCache")
//    @ResponseBody
//    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
//    public LayUiJsonObjectFmt initWhiteListCache() {
//        try {
//            businessManage.initWhiteListCache();
//        } catch (Exception e) {
//            SuperLogger.error("???????????????????????????" + e.getMessage(), e);
//        }
//        return LayuiResultUtil.success();
//    }

    /**
     * admin-->????????????-->?????????-->??????????????????
     */
    @RequestMapping("business_initBlackListCache")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public LayUiJsonObjectFmt initBlackListCache() {
        try {
            businessManage.initBlackListCache();
        } catch (Exception e) {
            SuperLogger.error("???????????????????????????" + e.getMessage(), e);
        }
        return LayuiResultUtil.success();
    }

    //???????????????
    @RequestMapping("business_importSensitiveWord")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "???????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_importSensitiveWord")
    public LayUiJsonObjectFmt importSensitiveWord(SensitiveWord sensitiveWord,
                                                  @RequestParam(value = "file") MultipartFile file) {
        try {
            sensitiveWord.setCreate_User(getLoginUser().getReal_Name());
            sensitiveWord.setRemark(getLoginUser().getReal_Name() + "???????????????");
            sensitiveWord.setCreate_Date(new Date());
            this.businessManage.importSensitiveWord(sensitiveWord, file);
        } catch (ServiceException e) {
            return LayuiResultUtil.error(e);
        } catch (Exception e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }


    /**
     * admin-->????????????-->??????????????????
     */
    @RequestMapping("business_InterceptStrategyList")
    @ResponseBody
    public String InterceptStrategyList(InterceptStrategy interceptStrategy) {
        List<InterceptStrategy> interceptStrategyList = businessManage.queryInterceptStrategyList(interceptStrategy);
        return new SmsUIObjectMapper().asSuccessString(interceptStrategyList, interceptStrategy.getPagination());
    }

    /**
     * ??????????????????
     */
    @RequestMapping("business_addInterceptStrategy")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addInterceptStrategy")
    public LayUiJsonObjectFmt addInterceptStrategy(InterceptStrategy interceptStrategy) {
        interceptStrategy.setCreate_Date(new Date());
        try {
            this.businessManage.addInterceptStrategy(interceptStrategy);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("????????????");
        }
        return LayuiResultUtil.success();
    }

    //??????????????????
    @RequestMapping("business_deleteInterceptStrategy")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public LayUiJsonObjectFmt deleteInterceptStrategy(BaseParamEntity entity) {
        this.businessManage.deleteInterceptStrategy(entity.getCkIds());
        return LayuiResultUtil.success();
    }

    //??????????????????
    @RequestMapping("business_getAreaNameByAreaCode")
    @ResponseBody
    public LayUiJsonObjectFmt getAreaNameByAreaCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return LayuiResultUtil.success("????????????");
        }
        String[] split = code.split(",");
        StringBuffer areaName = new StringBuffer();
        Arrays.asList(split).forEach(value -> {
            Code codeObject = DatabaseCache.getCodeBySortCodeAndCode("location", value);
            if (codeObject != null) {
                areaName.append(codeObject.getName());
                areaName.append(",");
            }
        });
        String result = areaName.toString();
        result = result.length() > 0 ? result.substring(0, result.length() - 1) : "????????????";
        return LayuiResultUtil.success(result);
    }


    /**
     * ????????????????????????
     */
    @RequestMapping("business_preEditInterceptStrategy")
    public String preEditInterceptStrategy(BaseParamEntity entity) {
        InterceptStrategy bean = new InterceptStrategy();
        bean.setId(entity.getCkIds().get(0));
        List<InterceptStrategy> list = businessManage.queryInterceptStrategyList(bean);
        request.setAttribute("interceptStrategy", ObjectUtils.isEmpty(list) ? null : list.get(0));
        return "/business/intercept_strategy_edit";
    }

    /**
     * ????????????????????????
     */
    @RequestMapping("business_editInterceptStrategy")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public LayUiJsonObjectFmt editInterceptStrategy(InterceptStrategy interceptStrategy) {
        try {
            this.businessManage.editInterceptStrategy(interceptStrategy);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("???????????????");
        }
        return LayuiResultUtil.success();
    }

    //????????????
    @RequestMapping("business_alarmList")
    @ResponseBody
    public String alarmList(AlarmExt alarmExt) {
        List<Alarm> alarmList = this.businessManage.queryAlarmList(alarmExt);
        if(alarmList != null && alarmList.size() > 0) {
            for(Alarm alarm : alarmList){
                String typeCode = alarm.getType_Code();
                if(typeCode.startsWith("channel")){
                    Channel channel = DatabaseCache.getChannelByNo(alarm.getBind_Value());
                    alarm.setBind_Value(channel.getName());
                }else if(typeCode.startsWith("product")){
                    Product product = DatabaseCache.getProductByNo(alarm.getBind_Value());
                    alarm.setBind_Value(product.getName());
                }
            }
        }
        return new SmsUIObjectMapper().asSuccessString(alarmList, alarmExt.getPagination());
    }

    //????????????
    @RequestMapping("business_addAlarm")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addAlarm")
    public LayUiJsonObjectFmt addAlarm(AlarmExt alarmExt) {
        try {
            this.businessManage.addAlarm(alarmExt);
            return LayuiResultUtil.success();
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    //??????????????????
    @RequestMapping("business_preEditAlarm")
    public String preEditAlarm(BaseParamEntity entity) {
        if (entity.getCkIds() == null || entity.getCkIds().isEmpty()) {
            return "/systemsetup/alarm_edit";
        }
        AlarmExt alarmExt = this.businessManage.queryAlarmById(entity.getCkIds().get(0));
        request.setAttribute("alarm", alarmExt);
        return "/systemsetup/alarm_edit";
    }

    //????????????
    @RequestMapping("business_editAlarm")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    public LayUiJsonObjectFmt editAlarm(AlarmExt alarmExt) {
        try {
            this.businessManage.editAlarm(alarmExt);
            return LayuiResultUtil.success();
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("???????????????");
        }
    }

    //????????????
    @RequestMapping("business_deleteAlarm")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    public LayUiJsonObjectFmt deleteAlarm(BaseParamEntity entity) {
        this.businessManage.deleteAlarm(entity.getCkIds());
        return LayuiResultUtil.success();
    }

    /**
     * ????????????
     *
     * @param ckIds
     * @return
     */

    @RequestMapping("alarm_lock")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public LayUiJsonObjectFmt lockAlarm(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.businessManage.lockAlarm(ckIds);
        return LayuiResultUtil.success();
    }

    /**
     * ????????????
     *
     * @param ckIds
     * @return
     */
    @RequestMapping("alarm_unLock")
    @ResponseBody
    @OperateAnnotation(moduleName = "????????????", option = "??????????????????")
    public LayUiJsonObjectFmt unLockAlarm(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.businessManage.unLockAlarm(ckIds);
        return LayuiResultUtil.success();
    }

    /**
     * ????????????????????????
     *
     * @param
     * @return
     */
    @RequestMapping("business_mmsTemplateList")
    @ResponseBody
    public String mmsTemplateList(MmsTemplate mmsTemplate) {
        List<MmsTemplate> smsMultimediaTemplates = materialManage.queryMmsTemplateList(mmsTemplate);
        return new SmsUIObjectMapper().asSuccessString(smsMultimediaTemplates, mmsTemplate.getPagination());
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    @RequestMapping("business_preCheckMmsTemplate")
    public String preCheckMmsTemplate(BaseParamEntity entity) {
        try {
            MmsTemplate mmsTemplate = materialManage.queryMmsTemplate(entity.getCkIds().get(0));
            String templateContent = mmsTemplate.getTemplate_Content();
            Map<String, Object> contentMap = JsonUtil.STANDARD.readValue(templateContent,
                    new TypeReference<Map<String, Object>>() {
                    });
            if (ObjectUtils.isNotEmpty(contentMap)) {
                List<Map<String, String>> fileList = JsonUtil.STANDARD.readValue(contentMap.get("data").toString(),
                        new TypeReference<List<Map<String, String>>>() {
                        });
                request.setAttribute("mmsTitle", contentMap.get("title"));
                request.setAttribute("fileList", fileList);
            }
            request.setAttribute("mmsTemplate", mmsTemplate);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return "/business/mms_template_check";
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping("business_checkMmsTemplate")
    @ResponseBody
    public LayUiJsonObjectFmt checkMmsTemplate(MmsTemplate mmsTemplate) {
        try {
            if (Constant.SMS_TEMPLAT_CHECK_STATUS_PASS.equalsIgnoreCase(mmsTemplate.getApprove_Status())) {
                if (StringUtils.isEmpty(mmsTemplate.getChannel_Template_Code())) {
                    throw new ServiceException("??????????????????????????????");
                }
            }
            this.materialManage.editMmsTemplate(mmsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    @RequestMapping("business_perAddMmsTemplate")
    public String perAddMmsTemplate() {
        String maxSize = DatabaseCache.getStringValueBySystemEnvAndCode("mms_template_max_size", "1945");
        request.setAttribute("maxSize", maxSize);
        return "/business/mms_template_add";
    }

    /**
     * ??????????????????
     *
     * @param
     * @return
     */
    @RequestMapping("business_addMmsTemplate")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addMmsTemplate")
    public LayUiJsonObjectFmt addMmsTemplate(MmsTemplate mmsTemplate) {
        try {
            this.materialManage.addMmsTemplate(mmsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * ????????????
     */
    @RequestMapping("business_deleteMmsTemplate")
    @ResponseBody
    public LayUiJsonObjectFmt deleteMmsTemplate(BaseParamEntity entity) {
        try {
            this.materialManage.deleteMmsTemplate(entity.getCkIds(), new EnterpriseUser());
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * ????????????
     */
    @RequestMapping("business_materialList")
    @ResponseBody
    public String materialList(MmsMaterial material) {
        List<MmsMaterial> list = materialManage.queryMmsMaterialList(material);
        return new SmsUIObjectMapper().asSuccessString(list, material.getPagination());
    }

    /**
     * ??????????????????
     */
    @RequestMapping("business_perAddMaterial")
    public ModelAndView perAddMaterial() {
        ModelAndView mv = new ModelAndView("/business/material_add");
        String maxMaterialSize = DatabaseCache.getStringValueBySystemEnvAndCode("material_max_size", "1945");
        mv.addObject("maxMaterialSize", maxMaterialSize);
        return mv;
    }

    /**
     * ????????????
     */
    @RequestMapping("business_addMaterial")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_addMaterial")
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    public LayUiJsonObjectFmt addMaterial(MmsMaterial material,
                                          @RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        try {
            Long maxSize = Long.valueOf(DatabaseCache.getStringValueBySystemEnvAndCode("material_max_size", "1945"))
                    * 1024L;
            if (file.getSize() > maxSize) {
                return LayuiResultUtil.fail("?????????????????????");
            }
            String path = "material" + File.separator + material.getEnterprise_No();
            Map<String, String> map = UploadUtil.uploadFile(file, path);
            if (!"true".equals(map.get("status"))) {
                return LayuiResultUtil.fail("????????????");
            }
            material.setApprove_Status(AuditStatus.PASS.value());
            materialManage.insertMaterial(material, map);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail("????????????");
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("????????????");
        }
        return LayuiResultUtil.success("????????????");
    }

    /**
     * ????????????
     */
    @RequestMapping("business_delMaterial")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "business_delMaterial")
    @OperateAnnotation(moduleName = "????????????", option = "????????????")
    public LayUiJsonObjectFmt delMaterial(BaseParamEntity entity, HttpServletRequest request) {
        if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
            throw new ServiceException("????????????????????????");
        }
        materialManage.deleteMaterial(entity.getCkIds(), new EnterpriseUser());
        return LayuiResultUtil.success("????????????");
    }

    /**
     * ????????????
     */
    @RequestMapping("business_approveMaterial")
    @ResponseBody
    public LayUiJsonObjectFmt preApproveMaterial(String approve_Status,
                                                 @RequestParam(value = "ckIds", required = true) List<Integer> ckIds) {
        if (ckIds == null || ckIds.size() < 1) {
            throw new ServiceException("????????????????????????");
        }
        materialManage.approveMmsMaterial(approve_Status, ckIds);
        return LayuiResultUtil.success("????????????");
    }

    //????????????????????????????????????
    @RequestMapping("business_preSelectMaterial")
    public String preSelectMaterial(MmsMaterial material, String comeType) {
        List<MmsMaterial> pictureList = new ArrayList<>();
        List<MmsMaterial> videoList = new ArrayList<>();
        List<MmsMaterial> audioList = new ArrayList<>();
        List<MmsMaterial> materialList = materialManage.queryMmsMaterialList(material);
        if (materialList != null && !materialList.isEmpty()) {
            materialList.forEach(item -> {
                item.setUrl(item.getUrl().replaceAll("\\\\", "/"));
                if (MaterialType.PICTURE.equals(item.getMaterial_Type_Code())) {
                    pictureList.add(item);
                } else if (MaterialType.VIDEO.equals(item.getMaterial_Type_Code())) {
                    videoList.add(item);
                } else if (MaterialType.AUDIO.equals(item.getMaterial_Type_Code())) {
                    audioList.add(item);
                }
            });
        }
        request.setAttribute("pictureList", pictureList);
        request.setAttribute("videoList", videoList);
        request.setAttribute("audioList", audioList);
        request.setAttribute("comeType", comeType);
        return "/business/mms_select_material";
    }

    /**
     * ?????????????????????????????????url
     */
    @RequestMapping("set_enterpriseDomain")
    @ResponseBody
    public LayUiJsonObjectFmt set_enterpriseDomain(EnterpriseExt enterpriseExt) {
        try {
            enterpriseManage.editEnterprise(enterpriseExt);
        } catch (Exception be) {
            return LayuiResultUtil.fail(be.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * ??????????????????
     */
    @RequestMapping("get_enterpriseDomain")
    public String get_enterpriseDomain(BaseParamEntity entity) {
        EnterpriseExt enterpriseExt = new EnterpriseExt();
        enterpriseExt.setId(entity.getCkIds().get(0));
        Enterprise enterprise = enterpriseManage.queryEnterpriseById(entity.getCkIds().get(0));
        request.setAttribute("enterprise", enterprise);

        return "/enterprise/set_domain";
    }


    /**
     *
     * @Title: getTransferPhoneNos
     * @Description: ???????????????????????????
     * @author yjb
     * @param startDate
     * @param endDate
     * @return
     * @throws
     * @date 2020-08-19
     */
    @ResponseBody
    @RequestMapping("business_getTransferPhoneNos")
    public LayUiJsonObjectFmt getTransferPhoneNos(String startDate,
                                                  String endDate) {
        try {
            businessManage.getTransferPhoneNos(startDate, endDate, getLoginUser());
        } catch (ServiceException se) {
            return LayuiResultUtil.error(se);
        } catch (Exception e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    //??????????????????
    @RequestMapping("business_alarmLogList")
    @ResponseBody
    public String alarmLogList(AlarmLog alarmLog) {
        List<AlarmLog> alarmLogList = this.businessManage.queryAlarmLogList(alarmLog);
        return new SmsUIObjectMapper().asSuccessString(alarmLogList, alarmLog.getPagination());
    }

    //????????????=???????????????
    @RequestMapping("business_alarmDetail")
    public String alarmDetail(String bingValveType,String bindValue
            ,String beginDate,String endDate,String alarmType) {
        request.setAttribute("bingValveType",bingValveType);//??????????????????
        request.setAttribute("bindValue",bindValue);//????????????
        request.setAttribute("beginDate",beginDate);
        request.setAttribute("endDate",endDate);
        request.setAttribute("alarmType",alarmType);//????????????
        return "/systemsetup/alarm_log_detail";
    }
}
