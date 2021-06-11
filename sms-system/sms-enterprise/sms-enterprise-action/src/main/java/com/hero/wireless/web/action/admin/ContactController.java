package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.ContactGroup;
import com.hero.wireless.web.entity.business.ext.ContactExt;
import com.hero.wireless.web.service.ISendManage;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 联系人action
 *
 * @author 张丽阳
 * @createTime 2013年11月1日 下午4:26:20
 */
@Controller
@RequestMapping("/admin/")
public class ContactController extends BaseEnterpriseController {

    @Resource(name = "sendManage")
    private ISendManage sendManage;

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * @return
     */
    @RequestMapping("contact_addContactGroup")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "contact_addContactGroup")
    public LayUiJsonObjectFmt addContactGroup(ContactGroup contactGroup) {
        try {
            contactGroup.setEnterprise_No(getLoginEnterprise().getNo());
            contactGroup.setCreate_Enterprise_User_Id(getLoginEnterpriseUser().getId());
            contactGroup.setCreate_Enterprise_User_Name(getLoginRealName());
            this.sendManage.addContactGroup(contactGroup);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("contact_exsitsContactGroup")
    @ResponseBody
    public Boolean exsitsContactGroup(ContactGroup contactGroup) {
        contactGroup.setEnterprise_No(getLoginEnterprise().getNo());
        contactGroup.setCreate_Enterprise_User_Id(getLoginEnterpriseUser().getId());
        contactGroup.setCreate_Enterprise_User_Name(getLoginRealName());
        return this.sendManage.exsitsContactGroup(contactGroup);
    }

    //
    @RequestMapping("contact_addContact")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "contact_addContact")
    public LayUiJsonObjectFmt addContact(ContactExt contactExt) {
        try {
            checkContactExt(contactExt);
            contactExt.setEnterprise_No(getLoginEnterprise().getNo());
            contactExt.setCreate_Enterprise_User_Id(getLoginEnterpriseUser().getId());
            contactExt.setCreate_Enterprise_User_Name(getLoginRealName());
            this.sendManage.addContact(contactExt);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }


    @RequestMapping("contact_manager")
    @ResponseBody
    public List<ContactGroup> manager(ContactGroup contactGroup) {
        if (contactGroup == null) {
            contactGroup = new ContactGroup();
        }
        contactGroup.setCreate_Enterprise_User_Id(getLoginEnterpriseUser().getId());
        List<ContactGroup> contactGroupList = sendManage.queryContactGroupList(contactGroup);

        return contactGroupList;

    }

    @RequestMapping("contact_listContact")
    @ResponseBody
    public String listContact(ContactExt contactExt) {
        contactExt.setCreate_Enterprise_User_Id(getLoginEnterpriseUser().getId());
        List<ContactExt> contactExtList = sendManage.queryContactList(contactExt);
        return new SmsUIObjectMapper().asSuccessString(contactExtList, contactExt.getPagination());
    }

    @RequestMapping("contact_exportContactList")
    @ResponseBody
    public LayUiJsonObjectFmt exportContactList(ContactExt contactExt) {
        try {
            contactExt.setEnterprise_No(this.getLoginEnterprise().getNo());
            sendManage.exportContactList(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue()
                    , contactExt, getEnterpriseDefaultExportFile());
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    @RequestMapping("contact_importContact")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "contact_importContact")
    public LayUiJsonObjectFmt importContact(ContactExt contactExt, @RequestParam(value = "moblieFile") MultipartFile moblieFile) {
        contactExt.setEnterprise_No(getLoginEnterprise().getNo());
        contactExt.setCreate_Enterprise_User_Id(getLoginEnterpriseUser().getId());
        contactExt.setCreate_Enterprise_User_Name(getLoginRealName());
        String fileTyle = moblieFile.getOriginalFilename().substring(moblieFile.getOriginalFilename().lastIndexOf("."));
        Map<String ,Object> result = new HashMap<>();
        try {
            checkContactExt(contactExt);
            if (fileTyle.equalsIgnoreCase(".txt")) {
                result = this.sendManage.importContact(moblieFile, contactExt);
            } else {
                result = this.sendManage.importExcelContact(moblieFile, contactExt);
            }
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success(result);
    }


    @RequestMapping("/contact_preEditContact")
    public String preCharge(BaseParamEntity entity) {
        if (entity.getCkIds() != null && entity.getCkIds().size() > 0) {
            ContactExt condition = new ContactExt();
            condition.setId(entity.getCkIds().get(0));
            condition.setEnterprise_No(getLoginEnterprise().getNo());
            List<ContactExt> list = this.sendManage.queryContactList(condition);
            request.setAttribute("contactExt", list.size() > 0 ? list.get(0) : null);
        }
        return "/contact/contact_edit";
    }

    @RequestMapping("/contact_editContact")
    @ResponseBody
    public LayUiJsonObjectFmt editContact(ContactExt contactExt) {
        try {
            checkContactExt(contactExt);
            contactExt.setEnterprise_No(getLoginEnterprise().getNo());
            contactExt.setCreate_Enterprise_User_Id(getUserId());
            this.sendManage.editContact(contactExt);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }


    @RequestMapping("/contact_editContactGroup")
    @ResponseBody
    public LayUiJsonObjectFmt editContactGroup(ContactGroup contactGroup) {
        try {
            contactGroup.setEnterprise_No(getLoginEnterprise().getNo());
            contactGroup.setCreate_Enterprise_User_Id(getUserId());
            this.sendManage.editContactGroup(contactGroup);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("/contact_batchDeleteContact")
    @ResponseBody
    public LayUiJsonObjectFmt batchDeleteContact(BaseParamEntity entity) {
        List<Integer> ckContactId = entity.getCkIds();
        this.sendManage.deleteContactByIds(ckContactId, getAuthorityEnterpriseUserBean());
        return LayuiResultUtil.success();
    }

    @RequestMapping("/contact_batchDeleteContactGroup")
    @ResponseBody
    public LayUiJsonObjectFmt batchDeleteContactGroup(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.sendManage.deleteContactGroupByIds(ckIds, getAuthorityEnterpriseUserBean());
        return LayuiResultUtil.success();
    }

    /**
     * 通讯录模板下载
     *
     * @return
     */
    @RequestMapping("/contact_downloadTemplate")
    @ResponseBody
    public void downloadTemplate() {
        InputStream fis = null;
        OutputStream os = null;
        try {
            String path = DatabaseCache.getStringValueBySortCodeAndCode("system_env","contact_template_path",null);
            String realPath = request.getSession().getServletContext().getRealPath(path);
            File file = new File(realPath);
            String fileName = file.getName();
            fis = new BufferedInputStream(new FileInputStream(realPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();// 清空response
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.addHeader("Content-Length", "" + file.length());
            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);
            os.flush();
            os.close();
        } catch (Exception ex) {
            SuperLogger.error(ex.getMessage(), ex);
            try {
                if (fis != null) fis.close();
                if (os != null) os.close();
            } catch (IOException ioe) {
            }
        }
    }
    private void checkContactExt(ContactExt contactExt) {
        if (ObjectUtils.isEmpty(contactExt) || contactExt.getGroup_Id()==null) {
            throw new ServiceException("contactExt or group_Id is null");
        }
    }
}
