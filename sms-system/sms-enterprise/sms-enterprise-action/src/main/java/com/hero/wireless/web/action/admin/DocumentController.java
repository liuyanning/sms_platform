package com.hero.wireless.web.action.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.config.DatabaseCache;


@Controller
@RequestMapping("/admin")
public class DocumentController extends BaseEnterpriseController {

    @RequestMapping("document_Index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/document/document_index");
        String documentUrl = DatabaseCache.getStringValueBySystemEnvAndCode("documentUrl","https://www.showdoc.cc/drondea?page_id=2981934830938370");
        mv.addObject("documentUrl",documentUrl);
        return mv;
    }

}
