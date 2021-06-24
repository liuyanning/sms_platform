package com.hero.wireless.web.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmStatus;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.enums.Priority;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.ExceptionKey;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.ReportExample;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.service.base.BaseBusinessManage;
import com.hero.wireless.web.util.*;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("businessManage")
public class BusinessManageImpl extends BaseBusinessManage implements IBusinessManage {

    @Override
    public List<BlackListExt> queryBlackList(BlackListExt condition) {
        BlackListExample example = assemblyBlackListConditon(condition);
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" b.id desc ");
        return this.blackListExtDAO.selectExtByExamplePage(example);
    }

    @Override
    public BlackList addBlackList(BlackList data) {
        //查询数据库判断是否已经存在
        BlackListExample example = new BlackListExample();
        BlackListExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(data.getEnterprise_No())) {
            criteria.andEnterprise_NoEqualTo(data.getEnterprise_No());
        }
        if (null != data.getEnterprise_User_Id()) {
            criteria.andEnterprise_User_IdEqualTo(data.getEnterprise_User_Id());
        }
        if (StringUtils.isNotBlank(data.getPhone_No())) {
            criteria.andPhone_NoEqualTo(data.getPhone_No());
        }
        if (StringUtils.isNotBlank(data.getPool_Code())) {
            criteria.andPool_CodeEqualTo(data.getPool_Code());
        }
        List<BlackList> list = blackListDAO.selectByExample(example);
        if (list.size() > 0) {
            throw new ServiceException(data.getPhone_No() + ",已经存在!");
        }
        boolean addResult = BlackListUtil.addBlackList(data.getPhone_No(), data.getPool_Code(),
                data.getEnterprise_No(), data.getEnterprise_User_Id());
        data.setCreate_Date(new Date());
        this.blackListDAO.insert(data);
        return data;
    }

    @Override
    public List<CodeSort> queryCodeSortList(CodeSort condition) {
        CodeSortExample example = assemblyCodeSortConditon(condition);
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" id desc ");
        return this.codeSortDAO.selectByExamplePage(example);
    }

    @Override
    public CodeSort addCodeSort(CodeSort data) {
        CodeSortExample example = new CodeSortExample();
        CodeSortExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(data.getCode())) {
            cri.andCodeEqualTo(data.getCode());
        }
        List<CodeSort> list = this.codeSortDAO.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            throw new BaseException(ExceptionKey.CODE_SORT_EXSITS, data.getCode());
        }
        data.setCreate_Date(new Date());
        this.codeSortDAO.insert(data);
        return data;
    }

    @Override
    public Integer editCodeSort(CodeSort data) {
        Integer result = this.codeSortDAO.updateByPrimaryKeySelective(data);
        SuperLogger.debug("编辑代码分类影响行数:" + result);
        return result;
    }

    @Override
    public Integer deleteCodeSort(List<Integer> idList) {
        CodeSortExample example = new CodeSortExample();
        CodeSortExample.Criteria cri = example.createCriteria();
        cri.andIdIn(idList);
        Integer result = this.codeSortDAO.deleteByExample(example);
        SuperLogger.debug("删除代码分类影响行数:" + result);
        return result;
    }

    @Override
    public List<CodeExt> queryCodeList(CodeExt condition) {
        CodeExample example = new CodeExample();
        CodeExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(condition.getName())) {
            cri.andNameLike("%" + condition.getName() + "%");
        }
        if (!StringUtils.isEmpty(condition.getSort_Code())) {
            cri.andSort_CodeEqualTo(condition.getSort_Code());
        }
        if (!StringUtils.isEmpty(condition.getUp_Code())) {
            cri.andUp_CodeEqualTo(condition.getUp_Code());
        }
        if (!StringUtils.isEmpty(condition.getCode())) {
            cri.andCodeEqualTo(condition.getCode());
        }
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" c.id desc ");
        return this.codeExtDAO.selectExtByExamplePage(example);
    }

    @Override
    public Code addCode(Code data) {
        CodeExample example = new CodeExample();
        CodeExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(data.getCode())) {
            cri.andCodeEqualTo(data.getCode());
        }
        if (!StringUtils.isEmpty(data.getUp_Code())) {
            cri.andUp_CodeEqualTo(data.getUp_Code());
        }
        if (!StringUtils.isEmpty(data.getSort_Code())) {
            cri.andSort_CodeEqualTo(data.getSort_Code());
        }
        List<Code> list = this.codeDAO.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            throw new BaseException(ExceptionKey.CODE_EXSITS, data.getSort_Code(), data.getCode());
        }
        data.setCreate_Date(new Date());
        this.codeDAO.insert(data);
        return data;
    }

    @Override
    public Integer editCode(CodeExt data) {
        Integer result = this.codeDAO.updateByPrimaryKeySelective(data);
        return result;
    }

    @Override
    public Integer deleteCode(List<Integer> idList) {
        CodeExample example = new CodeExample();
        CodeExample.Criteria cri = example.createCriteria();
        cri.andIdIn(idList);
        Integer result = this.codeDAO.deleteByExample(example);
        return result;
    }

    @Override
    public int deleteBlackByIdList(List<Integer> idList) {
        BlackListExample example = new BlackListExample();
        BlackListExample.Criteria cri = example.createCriteria();
        cri.andIdIn(idList);
        // redis删除缓存
        List<BlackList> blacks = this.blackListDAO.selectByExample(example);
        blacks.forEach(item -> {
            BlackListUtil.removeBlackList(item.getPhone_No(), item.getPool_Code(),
                    item.getEnterprise_No(), item.getEnterprise_User_Id());
        });
        return this.blackListDAO.deleteByExample(example);
    }

    @Override
    public List<SensitiveWord> querySensitiveWordList(SensitiveWordExt condition) {
        SensitiveWordExample example = new SensitiveWordExample();
        SensitiveWordExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(condition.getWord())) {
            cri.andWordLike("%" + condition.getWord() + "%");
        }
        if (StringUtils.isNotBlank(condition.getTrade_Type_Code())) {
            cri.andTrade_Type_CodeEqualTo(condition.getTrade_Type_Code());
        }
        if (StringUtils.isNotBlank(condition.getPool_Code())) {
            cri.andPool_CodeEqualTo(condition.getPool_Code());
        }
        if (StringUtils.isNotBlank(condition.getCreate_User())) {
            cri.andCreate_UserEqualTo(condition.getCreate_User());
        }
        if (condition.getMinCreateDate() != null) {
            cri.andCreate_DateGreaterThanOrEqualTo(condition.getMinCreateDate());
        }
        if (condition.getMaxCreateDate() != null) {
            cri.andCreate_DateLessThanOrEqualTo(condition.getMaxCreateDate());
        }
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" id desc ");
        return this.sensitiveWordDAO.selectByExamplePage(example);
    }

    @Override
    public SensitiveWord addSensitiveWord(SensitiveWord data) {
        data.setCreate_Date(new Date());
        this.sensitiveWordDAO.insert(data);
        return data;
    }

    @Override
    public List<MobileArea> queryMobileAreaList(MobileArea condition) {
        MobileAreaExample example = new MobileAreaExample();
        MobileAreaExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(condition.getMobile_Area())) {
            cri.andMobile_AreaEqualTo(condition.getMobile_Area());
        }
        if (!StringUtils.isEmpty(condition.getMobile_Number())) {
            cri.andMobile_NumberEqualTo(condition.getMobile_Number());
        }
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        example.setPagination(condition.getPagination());
        example.setOrderByClause("id desc");
        return this.mobileAreaDAO.selectByExamplePage(example);
    }


    @Override
    public Integer deleteSensitiveWord(List<Integer> idList) {
        SensitiveWordExample example = new SensitiveWordExample();
        SensitiveWordExample.Criteria cri = example.createCriteria();
        cri.andIdIn(idList);
        Integer result = this.sensitiveWordDAO.deleteByExample(example);
        return result;
    }

    @Override
    public MobileArea addMobileArea(MobileArea data) {
        if (StringUtils.isEmpty(data.getMobile_Number())) {
            throw new ServiceException(ResultStatus.MOBILE_NUMBER__NOT_NULL);
        }
        if (StringUtils.isEmpty(data.getArea_Code())) {
            throw new ServiceException(ResultStatus.AREA_CODE__NOT_NULL);
        }
        if (StringUtils.isEmpty(data.getMobile_Area())) {
            throw new ServiceException(ResultStatus.MOBILE_AREA__NOT_NULL);
        }
        MobileAreaExample example = new MobileAreaExample();
        MobileAreaExample.Criteria cri = example.createCriteria();
        cri.andMobile_NumberEqualTo(data.getMobile_Number());
        List<MobileArea> Phone_NoAreaList = this.mobileAreaDAO.selectByExample(example);
        if (Phone_NoAreaList != null && Phone_NoAreaList.size() > 0) {
            throw new ServiceException(ResultStatus.MOBILE_AREA_EXSITS);
        }
        this.mobileAreaDAO.insert(data);
        return data;
    }

    @Override
    public void initMobileAreaCache() {
        new Thread() {
            public void run() {
                Pagination pagination = new Pagination(1, 10000);
                while (true) {
                    MobileAreaExample condition = new MobileAreaExample();
                    condition.setPagination(pagination);
                    List<MobileArea> list = mobileAreaDAO.selectByExample(condition);
                    if (ObjectUtils.isEmpty(list)) {
                        break;
                    }
                    pagination = new Pagination(pagination.getPageIndex() + 1, 10000);
                }
            }
        }.start();
    }

    @Override
    public Integer editMobileArea(MobileArea data) {
        Integer result = this.mobileAreaDAO.updateByPrimaryKeySelective(data);
        MobileArea redisKeyData = new MobileArea();
        redisKeyData.setMobile_Number(data.getMobile_Number());
        return result;
    }

    @Override
    public List<SmsRoute> querySmsRouteList(SmsRoute condition) {
        SmsRouteExample example = new SmsRouteExample();
        SmsRouteExample.Criteria cri = example.createCriteria();
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        if (!StringUtils.isEmpty(condition.getPrefix_Number())) {
            cri.andPrefix_NumberEqualTo(condition.getPrefix_Number());
        }
        if (!StringUtils.isEmpty(condition.getRoute_Name_Code())) {
            cri.andRoute_Name_CodeEqualTo(condition.getRoute_Name_Code());
        }
        if (!StringUtils.isEmpty(condition.getCountry_Code())) {
            cri.andCountry_CodeEqualTo(condition.getCountry_Code());
        }
        if (!StringUtils.isEmpty(condition.getMCC())) {
            cri.andMCCEqualTo(condition.getMCC());
        }
        if (!StringUtils.isEmpty(condition.getCountry_Number())) {
            cri.andCountry_NumberEqualTo(condition.getCountry_Number());
        }
        if (!StringUtils.isEmpty(condition.getRemark())) {
            cri.andRemarkLike("%" + condition.getRemark() + "%");
        }
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" id desc ");
        return this.smsRouteDAO.selectByExamplePage(example);
    }

    @Override
    public SmsRoute addSmsRoute(SmsRoute data) {
        String redisKey = DatabaseCache.getMobileRouteKey(data.getPrefix_Number(), data.getCountry_Code());
        SmsRouteExample example = new SmsRouteExample();
        example.createCriteria().andPrefix_NumberEqualTo(data.getPrefix_Number())
                .andCountry_CodeEqualTo(data.getCountry_Code());
        List<SmsRoute> listSms = smsRouteDAO.selectByExample(example);
        if (listSms.size() > 0) {
            this.smsRouteDAO.updateByExampleSelective(data, example);
        } else {
            data.setCreate_Date(new Date());
            this.smsRouteDAO.insert(data);
        }
        return data;
    }

    @Override
    public void editSmsRoute(SmsRoute smsRoute) {
        this.smsRouteDAO.updateByPrimaryKeySelective(smsRoute);
    }

    @Override
    public void deleteSmsRoute(List<Integer> ids) {
        SmsRouteExample example = new SmsRouteExample();
        SmsRouteExample.Criteria cri = example.createCriteria();
        cri.andIdIn(ids);
        List<SmsRoute> smsRoutes = this.smsRouteDAO.selectByExample(example);
        this.smsRouteDAO.deleteByExample(example);
    }

    @Override
    public void initSmsRouteCache() {
        new Thread(() -> DatabaseCache.refreshSmsRouteLocalCache()).start();
    }

    @Override
    public List<SmsRouteExt> querySmsRouteListLocalCache(int pageIndex, int pageSize) {
        Pagination pagination = new Pagination(pageIndex, pageSize);
        SmsRouteExample example = new SmsRouteExample();
        example.setPagination(pagination);
        return smsRouteExtDAO.query4LocalCache(example);
    }

    @Override
    public void initWhiteListCache() {
        //改成本地缓存后白名单不需要刷数据到redis了
        new Thread() {
            public void run() {
                Pagination pagination = new Pagination(1, 10000);
                while (true) {
                    WhiteListExample example = new WhiteListExample();
                    example.setPagination(pagination);
                    List<WhiteList> list = whiteListDAO.selectByExample(example);
                    if (ObjectUtils.isEmpty(list)) {
                        break;
                    }
//					list.forEach(entity -> {
//						WhiteListUtil.addWhiteList(entity.getPhone_No(), entity.getPool_Code(), entity.getEnterprise_No());
//					});
                    pagination = new Pagination(pagination.getPageIndex() + 1, 10000);
                }

            }
        }.start();

    }

    @Override
    public void initBlackListCache() {
        new Thread() {
            public void run() {
                Pagination pagination = new Pagination(1, 10000);
                while (true) {
                    BlackListExample condition = new BlackListExample();
                    condition.setPagination(pagination);
                    List<BlackList> list = blackListDAO.selectByExample(condition);
                    if (ObjectUtils.isEmpty(list)) {
                        break;
                    }
                    list.forEach(entity -> {
                        BlackListUtil.addBlackList(entity.getPhone_No(), entity.getPool_Code(),
                                entity.getEnterprise_No(), entity.getEnterprise_User_Id());
                    });
                    pagination = new Pagination(pagination.getPageIndex() + 1, 10000);
                }
            }
        }.start();
    }

    @Override
    public List<WhiteList> queryWhiteList(WhiteList condition, Map<String, String> parMap) {
        WhiteListExample example = assemblyWhiteListConditon(condition);
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" id desc ");
        return whiteListDAO.selectByExamplePage(example);
    }

    @Override
    public List<WhiteList> queryWhiteList(WhiteListExample condition) {
        return whiteListDAO.selectByExample(condition);
    }


    @Override
    public WhiteList addWhiteList(WhiteList data) {
        WhiteListExample example = assemblyAddWhiteListConditon(data);
        List<WhiteList> list = this.whiteListDAO.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            throw new RuntimeException(data.getPhone_No() + ",已经存在!");
        }
        //页面添加运营商临时注掉
//		SmsRoute smsRoute = DatabaseCache.getSmsRouteByNumber(data.getPhone_No(), SMSUtil.getCountryCOdeByPhoneNo(data.getPhone_No()));
//		data.setRoute_Name_Code(smsRoute == null?"":smsRoute.getRoute_Name_Code());
        data.setCreate_Date(new Date());
        this.whiteListDAO.insert(data);
        return data;
    }

    @Override
    public int deleteWhiteList(List<Integer> ids) {
        WhiteListExample example = new WhiteListExample();
        WhiteListExample.Criteria cri = example.createCriteria();
        cri.andIdIn(ids);
        // redis删除缓存
        List<WhiteList> whites = this.whiteListDAO.selectByExample(example);
//		whites.forEach(item -> {
//			WhiteListUtil.removeWhiteList(item.getPhone_No(), item.getPool_Code(), item.getEnterprise_No());
//		});
        return this.whiteListDAO.deleteByExample(example);
    }

    @Override
    public void importBlackList(BlackList entity, MultipartFile file) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_IMPORT);//校验
        new Thread() {
            public void run() {
                BufferedReader reader = null;
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(file.getInputStream());
                    reader = new BufferedReader(isr);
                    String phoneLine = "";
                    List<BlackList> insertList = new ArrayList<BlackList>();// 插入列表
                    while ((phoneLine = reader.readLine()) != null) {
                        String[] phoneLineArray = phoneLine.split(Constant.MUTL_MOBILE_SPLIT);
                        if (phoneLineArray.length > 0) {
                            for (String phoneNo : phoneLineArray) {
                                if (StringUtils.isEmpty(phoneNo)) {
                                    continue;
                                }
                                phoneNo = phoneNo.trim();
                                if (phoneNo.startsWith(BeanUtil.UFEFF)) {
                                    phoneNo = phoneNo.replaceAll(BeanUtil.UFEFF, "");
                                }
                                if (!NumberUtils.isDigits(phoneNo)) {
                                    continue;
                                }
                                if (phoneNo.startsWith("86") && phoneNo.length() != 13) {
                                    continue;
                                }
                                BlackList blackBean = (BlackList) entity.clone();
                                blackBean.setId(null);
                                SmsRoute route = DatabaseCache.getSmsRouteByNumber(phoneNo, SMSUtil.getCountryCOdeByPhoneNo(phoneNo));
                                blackBean.setRoute_Name_Code(route == null ? "" : route.getRoute_Name_Code());
                                blackBean.setPhone_No(phoneNo);
                                blackBean.setCreate_Date(new Date());
                                // 查询redis是否值已存在,存在则不再保存
                                boolean insertResult = BlackListUtil.addBlackList(phoneNo, blackBean.getPool_Code(),
                                        blackBean.getEnterprise_No(), blackBean.getEnterprise_User_Id());
                                blackBean.setEnterprise_User_Id(blackBean.getEnterprise_User_Id());
                                if (!insertResult) {
                                    continue;
                                }
                                insertList.add(blackBean);
                                if (insertList.size() % Constant.INSERT_MAX_LENGTH == 0) {
                                    blackListDAO.insertList(insertList);
//									addBlackListRedis(insertList);
                                    insertList = new ArrayList<>();
                                }
                            }
                        }
                    }
                    if (insertList.size() > 0) {
                        blackListDAO.insertList(insertList);
                    }
                    newThreadAfter(redisKey);
                } catch (Exception e) {
                    SuperLogger.error(e.getMessage(), e);
                } finally {
                    try {
                        isr.close();
                        reader.close();
                    } catch (IOException e) {
                        SuperLogger.error(e.getMessage(), e);
                    }
                }
            }
        }.start();
    }

    @Override
    public void importWhiteList(WhiteList entity, MultipartFile file) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_IMPORT);//校验
        new Thread() {
            public void run() {
                BufferedReader reader = null;
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(file.getInputStream());
                    reader = new BufferedReader(isr);
                    String phoneLine = "";
                    List<WhiteList> insertList = new ArrayList<WhiteList>();// 插入列表
                    while ((phoneLine = reader.readLine()) != null) {
                        String[] phoneLineArray = phoneLine.split(Constant.MUTL_MOBILE_SPLIT);
                        if (phoneLineArray.length > 0) {
                            for (String phoneNo : phoneLineArray) {
                                phoneNo = phoneNo.trim();
                                if (phoneNo.startsWith(BeanUtil.UFEFF)) {
                                    phoneNo = phoneNo.replaceAll(BeanUtil.UFEFF, "");
                                }
                                if (!NumberUtils.isDigits(phoneNo)) {
                                    continue;
                                }
                                if (phoneNo.startsWith("86") && phoneNo.length() != 13) {
                                    continue;
                                }

//								boolean hasValue = WhiteListUtil.isWhite(phoneNo, entity.getPool_Code(), entity.getEnterprise_No());
//								if(hasValue){
//									continue;
//								}
//								// 增加redis
//								WhiteListUtil.addWhiteList(phoneNo, entity.getPool_Code(), entity.getEnterprise_No());

                                WhiteList whiteList = (WhiteList) entity.clone();
                                whiteList.setId(null);
                                whiteList.setPhone_No(phoneNo);
                                whiteList.setCreate_Date(new Date());
                                SmsRoute route = DatabaseCache.getSmsRouteByNumber(phoneNo, SMSUtil.getCountryCOdeByPhoneNo(phoneNo));
                                whiteList.setRoute_Name_Code(route == null ? "" : route.getRoute_Name_Code());
                                insertList.add(whiteList);
                            } // for end
                        }
                    } // while end
                    // 插入数据
                    if (insertList.size() > 0) {
                        ListUtils.partition(insertList, Constant.INSERT_MAX_LENGTH).forEach(sub -> {
                            whiteListDAO.insertList(sub);
                        });
                    }
                    newThreadAfter(redisKey);
                } catch (Exception e) {
                    SuperLogger.error(e.getMessage(), e);
                } finally {
                    try {
                        isr.close();
                        reader.close();
                    } catch (IOException e) {
                        SuperLogger.error(e.getMessage(), e);
                    }
                }
            }
        }.start();

    }

    @Override
    public void importSensitiveWord(SensitiveWord entity, MultipartFile file) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_IMPORT);//校验
        new Thread() {
            public void run() {
                BufferedReader reader = null;
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(file.getInputStream());
                    reader = new BufferedReader(isr);
                    String textLine = "";
                    List<SensitiveWord> insertList = new ArrayList<>();// 插入列表
                    while ((textLine = reader.readLine()) != null) {
                        String[] lineArray = textLine.split(Constant.MUTL_MOBILE_SPLIT);
                        if (lineArray.length > 0) {
                            for (String word : lineArray) {
                                if (word.startsWith(BeanUtil.UFEFF)) {
                                    word = word.replaceAll(BeanUtil.UFEFF, "");
                                }
                                if (StringUtils.isEmpty(word)) {
                                    continue;
                                }
                                SensitiveWord sensitiveWord = (SensitiveWord) entity.clone();
                                sensitiveWord.setId(null);
                                sensitiveWord.setWord(word);
                                insertList.add(sensitiveWord);
                            } // for end
                        }
                    } // while end
                    // 插入数据
                    if (insertList.size() > 0) {
                        ListUtils.partition(insertList, Constant.INSERT_MAX_LENGTH).forEach(sub -> {
                            sensitiveWordDAO.insertList(sub);
                        });
                    }
                    // 更新敏感字缓存
                    newThreadAfter(redisKey);
                } catch (Exception e) {
                    SuperLogger.error(e.getMessage(), e);
                } finally {
                    try {
                        isr.close();
                        reader.close();
                    } catch (IOException e) {
                        SuperLogger.error(e.getMessage(), e);
                    }
                }
            }
        }.start();

    }

    @Override
    public List<InterceptStrategy> queryInterceptStrategyList(InterceptStrategy condition) {
        InterceptStrategyExample example = new InterceptStrategyExample();
        InterceptStrategyExample.Criteria cri = example.createCriteria();
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        if (!StringUtils.isEmpty(condition.getName())) {
            cri.andNameLike("%" + condition.getName() + "%");
        }
        if (!StringUtils.isEmpty(condition.getBlack_Pool_Code())) {
            cri.andBlack_Pool_CodeEqualTo(condition.getBlack_Pool_Code());
        }
        if (!StringUtils.isEmpty(condition.getWhite_Pool_Code())) {
            cri.andWhite_Pool_CodeEqualTo(condition.getWhite_Pool_Code());
        }
        if (!StringUtils.isEmpty(condition.getSensitive_Word_Pool_Code())) {
            cri.andSensitive_Word_Pool_CodeEqualTo(condition.getSensitive_Word_Pool_Code());
        }
        if (!StringUtils.isEmpty(condition.getFaild_Type_Code())) {
            cri.andFaild_Type_CodeEqualTo(condition.getFaild_Type_Code());
        }
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" id desc ");
        return this.interceptStrategyDAO.selectByExamplePage(example);
    }

    @Override
    public int addInterceptStrategy(InterceptStrategy interceptStrategy) {
        int result = this.interceptStrategyDAO.insert(interceptStrategy);
        return result;
    }

    @Override
    public int deleteInterceptStrategy(List<Integer> idList) {
        InterceptStrategyExample example = new InterceptStrategyExample();
        InterceptStrategyExample.Criteria cri = example.createCriteria();
        cri.andIdIn(idList);
        int result = this.interceptStrategyDAO.deleteByExample(example);
        return result;
    }

    @Override
    public int editInterceptStrategy(InterceptStrategy interceptStrategy) {
        int result = this.interceptStrategyDAO.updateByPrimaryKeySelective(interceptStrategy);
        return result;
    }

    @Override
    public List<Alarm> queryAlarmList(AlarmExt condition) {
        AlarmExample example = new AlarmExample();
        AlarmExample.Criteria cri = example.createCriteria();
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        if (StringUtils.isNotEmpty(condition.getStatus())) {
            cri.andStatusEqualTo(condition.getStatus());
        }
        if (StringUtils.isNotEmpty(condition.getType_Code())) {
            cri.andType_CodeEqualTo(condition.getType_Code());
        }
        if (StringUtils.isNotEmpty(condition.getBind_Value())) {
            cri.andBind_ValueEqualTo(condition.getBind_Value());
        }
        if (StringUtils.isNotEmpty(condition.getPhone_Nos())) {
            cri.andPhone_NosLike("%" + condition.getPhone_Nos() + "%");
        }
        if (StringUtils.isNotEmpty(condition.getEmails())) {
            cri.andEmailsLike("%" + condition.getEmails() + "%");
        }
        if (condition.getAlarm_Total() != null) {
            cri.andAlarm_TotalEqualTo(condition.getAlarm_Total());
        }
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" id desc ");
        return alarmDAO.selectByExamplePage(example);
    }

    @Override
    public int editAlarm(AlarmExt alarmExt) {
        verifyAlarm(alarmExt);//验证
        //判断预警是否已经添加过了
        AlarmExample alarmExample = new AlarmExample();
        AlarmExample.Criteria cri = alarmExample.createCriteria();
        cri.andType_CodeEqualTo(alarmExt.getType_Code());
        if(StringUtils.isNotEmpty(alarmExt.getBind_Value())){
            cri.andBind_ValueEqualTo(alarmExt.getBind_Value());
        }
        List<Alarm> alarmList = alarmDAO.selectByExample(alarmExample);
        alarmList.forEach(entity ->{
            if(entity.getId().intValue() != alarmExt.getId().intValue()){
                throw new ServiceException("请检查，预警之前已添加过！");
            }
        });
        alarmExt.setAlarm_Total(0);//已告警次数
        if (StringUtils.isNotEmpty(alarmExt.getMonitorStartTime())
                && StringUtils.isNotEmpty(alarmExt.getMonitorEndTime())) {
            alarmExt.setMonitor_Time(alarmExt.getMonitorStartTime() + "," + alarmExt.getMonitorEndTime());
        }
        return this.alarmDAO.updateByPrimaryKeySelective(alarmExt);
    }

    @Override
    public int addAlarm(AlarmExt alarmExt) {
        verifyAlarm(alarmExt);//验证
        //判断预警是否已经添加过了
        AlarmExample alarmExample = new AlarmExample();
        AlarmExample.Criteria cri = alarmExample.createCriteria();
        cri.andType_CodeEqualTo(alarmExt.getType_Code());
        if(StringUtils.isNotEmpty(alarmExt.getBind_Value())){
            cri.andBind_ValueEqualTo(alarmExt.getBind_Value());
        }
        List<Alarm> alarmList = alarmDAO.selectByExample(alarmExample);
        if(alarmList.size() > 0){
            throw new ServiceException("请检查，预警之前已添加过！");
        }
        alarmExt.setAlarm_Total(0);//初始次数
        alarmExt.setCreate_Date(new Date());
        alarmExt.setStatus(AlarmStatus.START.toString());
        if (StringUtils.isNotEmpty(alarmExt.getMonitorStartTime())
                && StringUtils.isNotEmpty(alarmExt.getMonitorEndTime())) {
            alarmExt.setMonitor_Time(alarmExt.getMonitorStartTime() + "," + alarmExt.getMonitorEndTime());
        }
        int result = this.alarmDAO.insert(alarmExt);
        //企业用户预警通知MQ在分拣，这里不用通知MQ
        if(AlarmType.ACCOUNT_BALANCE_ALARM.equals( alarmExt.getType_Code())){
            return result;
        }
        Alarm alarm = new Alarm();
        BeanUtils.copyProperties(alarmExt, alarm);
        QueueUtil.notifyAlarm(alarm, Priority.MIDDLE_LEVEL.value());
        return result;
    }

    @Override
    public int deleteAlarm(List<Integer> idList) {
        if (ObjectUtils.isEmpty(idList)) {
            return 0;
        }
        AlarmExample example = new AlarmExample();
        AlarmExample.Criteria cri = example.createCriteria();
        cri.andIdIn(idList);
        return this.alarmDAO.deleteByExample(example);
    }

    @Override
    public Integer lockAlarm(List<Integer> uIdList) {
        if (uIdList == null || uIdList.isEmpty()) {
            return 0;
        }
        AlarmExample example = new AlarmExample();
        AlarmExample.Criteria cri = example.createCriteria();
        cri.andIdIn(uIdList);
        Alarm record = new Alarm();
        record.setStatus(AlarmStatus.STOP.toString());
        int result = this.alarmDAO.updateByExampleSelective(record, example);
        return result;
    }

    @Override
    public Integer unLockAlarm(List<Integer> uIdList) {
        if (uIdList == null || uIdList.isEmpty()) {
            return 0;
        }
        AlarmExample example = new AlarmExample();
        AlarmExample.Criteria cri = example.createCriteria();
        cri.andIdIn(uIdList);
        List<Alarm> list = this.alarmDAO.selectByExample(example);
        Alarm record = new Alarm();
        record.setStatus(AlarmStatus.START.toString());
        record.setAlarm_Total(0);//初始次数
        int result = this.alarmDAO.updateByExampleSelective(record, example);
        list.stream().forEach(item -> {
            item.setAlarm_Total(0);//初始次数
            item.setStatus(AlarmStatus.START.toString());
            QueueUtil.notifyAlarm(item, Priority.MIDDLE_LEVEL.value());
        });
        return result;
    }

    @Override
    public void updateByPrimaryKeySelective(Alarm alarm) {
        this.alarmDAO.updateByPrimaryKeySelective(alarm);
    }

    @Override
    public AlarmExt queryAlarmById(Integer Id) {
        Alarm alarm = this.alarmDAO.selectByPrimaryKey(Id);
        AlarmExt alarmExt = new AlarmExt();
        BeanUtils.copyProperties(alarm, alarmExt);
        if (alarmExt.getType_Code().equalsIgnoreCase(AlarmType.USER_NO_SUBMIT_SMS_ALARM.toString())) {
            String[] monitorTime = alarmExt.getMonitor_Time().split(",");
            alarmExt.setMonitorStartTime(monitorTime[0]);
            alarmExt.setMonitorEndTime(monitorTime[1]);
        }
        initSelect(alarmExt);
        return alarmExt;
    }

    @Override
    public List<Properties> queryPropertiesList(Properties properties) {
        PropertiesExample example = new PropertiesExample();
        PropertiesExample.Criteria criteria = example.createCriteria();
        if (null != properties.getId()) {
            criteria.andIdEqualTo(properties.getId());
        }
        if (StringUtils.isNotEmpty(properties.getType_Code())) {
            criteria.andType_CodeEqualTo(properties.getType_Code());
        }
        if (StringUtils.isNotEmpty(properties.getType_Code_Num())) {
            criteria.andType_Code_NumEqualTo(properties.getType_Code_Num());
        }
        if (StringUtils.isNotEmpty(properties.getProperty_Name())) {
            criteria.andProperty_NameEqualTo(properties.getProperty_Name());
        }
        if (StringUtils.isNotEmpty(properties.getProperty_Value())) {
            criteria.andProperty_ValueEqualTo(properties.getProperty_Value());
        }
        if (StringUtils.isNotEmpty(properties.getExtended_Field())) {
            criteria.andExtended_FieldEqualTo(properties.getExtended_Field());
        }
        if (StringUtils.isNotEmpty(properties.getRemark())) {
            criteria.andRemarkEqualTo(properties.getRemark());
        }
        if (StringUtils.isNotEmpty(properties.getDescription())) {
            criteria.andDescriptionEqualTo(properties.getDescription());
        }
        return propertiesDAO.selectByExample(example);
    }


    @Override
    public List<SmsRouteExt> queryOperatorListByCountry(String countryNumber) {
        return smsRouteExtDAO.queryOperatorListByCountry(countryNumber);
    }

    @Override
    public void getTransferPhoneNos(String startDate, String endDate, AdminUser adminUser) {

        //只允许查询时间跨度两周的数据
        int days = DateTime.daysBetween(DateTime.getDate(startDate), DateTime.getDate(endDate));
        if (days > 15) {
            throw new ServiceException("不能查询时间跨度大于15天的数据");
        }

        if (!GlobalRepeat.getAndSet("getTransferPhoneNos")) {
            SuperLogger.error("已有执行的查询任务，请稍后再操作");
            throw new ServiceException("已有执行的查询任务，请稍后再操作");
        }
        new Thread() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                saveSystemLog(adminUser, "START", startTime);
                int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "select_mnp_page_size", 1000);
                Long minId = 0L;
                Pagination pagination = new Pagination(1, pageSize);
                String token = DatabaseCache.getStringValueBySortCodeAndCode("system_env", "query_mnp_mobile_token", "");
                String queryMNPMobileUrl = DatabaseCache.getStringValueBySortCodeAndCode("system_env", "query_mnp_mobile_url", "");
                String mnpFullClassImpl = DatabaseCache.getStringValueBySortCodeAndCode("system_env", "mnp_full_class_impl", "");
                while (true) {
                    ReportExample reportExample = new ReportExample();
                    ReportExample.Criteria cri = reportExample.createCriteria();
                    cri.andIdGreaterThan(minId);
                    cri.andSubmit_DateBetween(DateTime.getDate(startDate), DateTime.getDate(endDate));
                    reportExample.setPagination(pagination);
                    reportExample.setOrderByClause("Id");
                    List<Report> list = reportExtDAO.selectReportByLimit(reportExample);
                    if (ObjectUtils.isEmpty(list)) {
                        break;
                    }
                    // 获取查询到的手机号集合
                    List<String> mobiles = list.stream().map(Report::getPhone_No).collect(Collectors.toList());
                    //调用接口查询数据
                    try {
                        AbstractHttp abstractQuery = (AbstractHttp) Class.forName(mnpFullClassImpl).newInstance();
                        Map<String, Integer> resultBean = abstractQuery.queryTransferPhone(token, String.join(",", mobiles), queryMNPMobileUrl);
                        if (resultBean != null) {
                            for (Map.Entry<String, Integer> entry : resultBean.entrySet()) {
                                SmsRoute smsRoute = new SmsRoute();
                                smsRoute.setCreate_Date(new Date());
                                smsRoute.setRemark("携号转网");
                                smsRoute.setPrefix_Number(entry.getKey());
                                smsRoute.setRoute_Name_Code(entry.getValue() == 1 ? "china_mobile" : entry.getValue() == 2 ? "china_unicom" : entry.getValue() == 3 ? "china_telecom" : "");
                                smsRoute.setCountry_Code("86");
                                if (StringUtils.isNotEmpty(smsRoute.getRoute_Name_Code())) {
                                    try {
                                        addSmsRoute(smsRoute);
                                    } catch (Exception e) {
                                        SuperLogger.error("保存路由信息异常：" + e.getMessage());
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        SuperLogger.error("查询保存携号转网数据异常：" + e.getMessage(), e);
                    }
                    minId = list.get(list.size() - 1).getId();
                }
                long endTime = System.currentTimeMillis();
                try {
                    GlobalRepeat.remove("getTransferPhoneNos");
                    saveSystemLog(adminUser, "FINISH", (endTime - startTime));
                } catch (Exception e) {
                    SuperLogger.error("查询保存携号转网系统日志异常：" + e.getMessage(), e);
                }
            }

            private void saveSystemLog(AdminUser adminUser, String mark, long time) {
                SystemLog log = new SystemLog();
                log.setCreate_Date(new Date());
                log.setModule_Name("查询保存携号转网数据");
                log.setOperate_Desc("查询保存携号转网数据");
                log.setReal_Name(adminUser.getReal_Name());
                log.setUser_Name(adminUser.getUser_Name());
                log.setUser_Id(adminUser.getId());
                log.setSpecific_Desc("查询保存携号转网任务" + mark + ": " + DateTime.getString());
                if ("FINISH".equals(mark)) {
                    log.setSpecific_Desc("查询保存携号转网任务" + mark + ": " + DateTime.getString() + " 耗时：" + DateTime.getFormatDurationByMS(time));
                }
                systemLogDAO.insert(log);
            }
        }.start();
    }

    @Override
    public List<AlarmLog> queryAlarmLogList(AlarmLog data) {
        AlarmLogExample alarmLogExample = new AlarmLogExample();
        AlarmLogExample.Criteria cri = alarmLogExample.createCriteria();
        if (!StringUtils.isEmpty(data.getType_Code())) {
            cri.andType_CodeEqualTo(data.getType_Code());
        }
        if (!StringUtils.isEmpty(data.getBind_Value())) {
            cri.andBind_ValueEqualTo(data.getBind_Value());
        }
        if (!StringUtils.isEmpty(data.getProbe_Result())) {
            cri.andProbe_ResultEqualTo(data.getProbe_Result());
        }
        if (!StringUtils.isEmpty(data.getDescription())) {
            cri.andDescriptionLike("%" + data.getDescription() + "%");
        }
        alarmLogExample.setPagination(data.getPagination());
        alarmLogExample.setOrderByClause("Id DESC");
        return alarmLogDAO.selectByExamplePage(alarmLogExample);
    }

    @Override
    public List<BlackList> queryBlackListLocalCache(int pageIndex, int pageSize) {
        Pagination pagination = new Pagination(pageIndex, pageSize);
        BlackListExample example = new BlackListExample();
        example.setPagination(pagination);
        return blackListDAO.selectByExample(example);
    }

    @Override
    public List<MobileArea> queryMobileAreaList(MobileAreaExample mobileAreaExample) {
        return mobileAreaDAO.selectByExample(mobileAreaExample);
    }

}
