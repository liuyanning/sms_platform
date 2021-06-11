package com.hero.wireless.web.util;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.config.DatabaseCache;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class UploadUtil {

    /**
     * 文件上传方法
     * @param file
     * @param dirName 文件夹目录名
     * @return Map<String, String> : status=true 为上传成功 其他都为失败
     */
    public static Map<String, String> uploadFile(MultipartFile file, String dirName){
        Map<String, String> result = new HashMap<>();
        result.put("status","false");
        try {
            if(StringUtils.isEmpty(dirName))dirName = "default";
            //软连接名
            String linkName = DatabaseCache.getStringValueBySystemEnvAndCode("uploadLink","uploadLink");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String filePath = request.getContextPath() + File.separator + linkName + File.separator
                    + dirName + File.separator;//文件项目目录
            filePath = filePath.replaceAll("/",File.separator);
            String realPath = request.getSession().getServletContext().getRealPath(filePath);
            String fileName = file.getOriginalFilename();//获取文件名加后缀
            String fileType = fileName.substring(fileName.lastIndexOf("."));//后缀
            fileName = DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_S_2)+ fileType;//新文件名
            File fileDirs = new File(realPath);//真实本地目录
            if(!fileDirs.exists()){
                fileDirs.mkdirs();
            }
            SuperLogger.debug("realPath:"+realPath);
            File newFile = new File(realPath + File.separator +fileName);
            file.transferTo(newFile);//写文件
            result.put("url", filePath + fileName);
            result.put("realPath", realPath + File.separator + fileName);
            result.put("size", newFile.length()+"");
            result.put("format", fileType.substring(1).toUpperCase());
            result.put("status","true");
            SuperLogger.debug("result:"+result);
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
        }
        return result;
    }

}
