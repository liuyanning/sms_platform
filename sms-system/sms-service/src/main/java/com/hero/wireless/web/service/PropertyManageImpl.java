package com.hero.wireless.web.service;

import com.hero.wireless.enums.PropertiesType;
import com.hero.wireless.web.dao.business.IPropertiesDAO;
import com.hero.wireless.web.entity.business.Properties;
import com.hero.wireless.web.entity.business.PropertiesExample;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * @version V2.2.5
 * @description: 属性表管理
 * @author: 刘彦宁
 * @date: 2020年05月12日09:22
 **/
@Service("propertyManage")
public class PropertyManageImpl implements IPropertyManage {

    @Resource(name = "IPropertiesDAO")
    private IPropertiesDAO<Properties> propertiesDAO;

    @Override
    public List<Properties> queryPropertiesList(Properties condition) {
        PropertiesExample example = new PropertiesExample();
        PropertiesExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(condition.getType_Code())) {
            cri.andType_CodeEqualTo(condition.getType_Code());
        }
        if (!StringUtils.isEmpty(condition.getType_Code_Num())) {
            cri.andType_Code_NumEqualTo(condition.getType_Code_Num());
        }
        if (!StringUtils.isEmpty(condition.getProperty_Name())) {
            cri.andProperty_NameEqualTo(condition.getProperty_Name());
        }
        if (!StringUtils.isEmpty(condition.getExtended_Field())) {
            cri.andExtended_FieldEqualTo(condition.getExtended_Field());
        }
        return propertiesDAO.selectByExample(example);
    }

    @Override
    public void deleteProperties(Properties properties) {
        PropertiesExample example = new PropertiesExample();
        PropertiesExample.Criteria cri = example.createCriteria();
        cri.andType_CodeEqualTo(properties.getType_Code());//必填参数
        cri.andType_Code_NumEqualTo(properties.getType_Code_Num());//必填参数
        if(StringUtils.isNotEmpty(properties.getProperty_Name())){
            cri.andProperty_NameEqualTo(properties.getProperty_Name());
        }
        if(StringUtils.isNotEmpty(properties.getProperty_Value())){
            cri.andProperty_ValueEqualTo(properties.getProperty_Value());
        }
        propertiesDAO.deleteByExample(example);
    }

    @Override
    public void addEnterpriseUserProperties(EnterpriseUserExt user) {
        PropertiesExample example = new PropertiesExample();
        PropertiesExample.Criteria cri = example.createCriteria();
        cri.andType_CodeEqualTo(PropertiesType.ENTERPRISE_USER.toString());//必填参数
        cri.andType_Code_NumEqualTo(String.valueOf(user.getId()));//必填参数
        cri.andExtended_FieldIsNull();// 扩展字段是Allowed_Send_Time的属性不删除，因为属性值设置不在同一个位置
        propertiesDAO.deleteByExample(example);

        Properties properties = new Properties();
        properties.setType_Code(PropertiesType.ENTERPRISE_USER.toString());
        properties.setType_Code_Num(String.valueOf(user.getId()));
        properties.setCreate_Date(new Date());
        List<Properties> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(user.getBlacklist_Switch())){
            properties.setProperty_Name(PropertiesType.Property_Name.BLACKLIST_SWITCH.toString());
            properties.setDescription("黑名单开关");
            properties.setProperty_Value(user.getBlacklist_Switch());
            list.add(properties);
        }
        if(StringUtils.isNotEmpty(user.getSignatureLocation())){
            Properties cloneBean = (Properties)properties.clone();
            cloneBean.setProperty_Name(PropertiesType.Property_Name.SIGNATURE_LOCATION.toString());
            cloneBean.setDescription("签名位置");
            cloneBean.setProperty_Value(user.getSignatureLocation());
            list.add(cloneBean);
        }
        if(StringUtils.isNotEmpty(user.getSgipSpIp())){
            Properties cloneBean = (Properties)properties.clone();
            cloneBean.setProperty_Name(PropertiesType.Property_Name.SGIP_SP_IP.toString());
            cloneBean.setDescription("SP_IP");
            cloneBean.setProperty_Value(user.getSgipSpIp());
            list.add(cloneBean);
        }
        if(StringUtils.isNotEmpty(user.getSgipSpPort())){
            Properties cloneBean = (Properties)properties.clone();
            cloneBean.setProperty_Name(PropertiesType.Property_Name.SGIP_SP_PORT.toString());
            cloneBean.setDescription("SP端口");
            cloneBean.setProperty_Value(user.getSgipSpPort());
            list.add(cloneBean);
        }
        if(StringUtils.isNotEmpty(user.getCountryCodeValue())){
            Properties cloneBean = (Properties)properties.clone();
            cloneBean.setProperty_Name(PropertiesType.Property_Name.COUNTRY_CODE_VALUE.toString());
            cloneBean.setDescription("国家区号过滤");
            cloneBean.setProperty_Value(user.getCountryCodeValue());
            list.add(cloneBean);
        }
        if(StringUtils.isNotEmpty(user.getWindowSize())){
            Properties cloneBean = (Properties)properties.clone();
            cloneBean.setProperty_Name(PropertiesType.Property_Name.WINDOW_SIZE.toString());
            cloneBean.setDescription("滑动窗口大小");
            cloneBean.setProperty_Value(user.getWindowSize());
            list.add(cloneBean);
        }
        if(list.size()>0){
            propertiesDAO.insertList(list);
        }
    }

    @Override
    public void update(Properties properties) {
        propertiesDAO.updateByPrimaryKeySelective(properties);
    }

    @Override
    public void save(Properties properties) {
        propertiesDAO.insert(properties);
    }

}
