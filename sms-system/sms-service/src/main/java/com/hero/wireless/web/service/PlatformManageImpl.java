package com.hero.wireless.web.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AccountStatus;
import com.hero.wireless.notify.JsonResponse;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.dao.business.IPlatformDAO;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.Properties;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.service.base.BaseEnterpriseManage;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.hero.wireless.web.config.MessagesManger.getSystemMessages;

@Service("platformManage")
public class PlatformManageImpl implements IPlatformManage {
    @Resource(name = "IPlatformDAO")
    private IPlatformDAO platformDAO;

    @Override
    public List<Platform> queryPlatformList(Platform platform) {
        PlatformExample example = new PlatformExample();
        PlatformExample.Criteria cri = example.createCriteria();
        example.setOrderByClause(" id desc");
        if(StringUtils.isNotBlank(platform.getPlatform_Name())){
            cri.andPlatform_NameEqualTo(platform.getPlatform_Name());
        }
        if(StringUtils.isNotBlank(platform.getPlatform_No())){
            cri.andPlatform_NoEqualTo(platform.getPlatform_No());
        }
        if(platform.getStatistics_Status()!=null){
            cri.andStatistics_StatusEqualTo(platform.getStatistics_Status());
        }
        return platformDAO.selectByExample(example);
    }

    @Override
    public int addPlatform(Platform platform) {
        PlatformExample example = new PlatformExample();
        PlatformExample.Criteria cri = example.createCriteria();
        cri.andPlatform_NameEqualTo(platform.getPlatform_Name());
        List<Platform> platformList= platformDAO.selectByExample(example);
        if(platformList.size() > 0){
            throw new ServiceException("平台名称重复");
        }
        platform.setPlatform_No(CodeUtil.buildNo());
        platform.setSign_Key(CodeUtil.buildNoByTime(1000));
        platform.setCreate_Date(new Date());
        return platformDAO.insert(platform);
    }

    @Override
    public int updateByPrimaryKeySelective(Platform platform) {
        PlatformExample example = new PlatformExample();
        PlatformExample.Criteria cri = example.createCriteria();
        cri.andPlatform_NameEqualTo(platform.getPlatform_Name());
        List<Platform> platformList= platformDAO.selectByExample(example);
        if(platformList.size() > 0 && !platform.getId().equals(platformList.get(0).getId())){
            throw new ServiceException("平台名称重复");
        }
        platform.setPlatform_No(null);
        platform.setSign_Key(null);
        return platformDAO.updateByPrimaryKeySelective(platform);
    }

    @Override
    public Platform queryPlatformById(Integer id) {
        return platformDAO.selectByPrimaryKey(id);
    }
}
