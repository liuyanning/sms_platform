/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hero.wireless.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Volcano
 */
public class BaseController {
	private static Log log = LogFactory.getLog(BaseController.class);
    public final static String ADMIN_PLATFORM = "admin:";
    public final static String ENTERPRISE_PLATFORM = "enterprise:";
    public final static String AGENT_PLATFORM = "agent:";
    public final static String MONITOR_PLATFORM = "monitor:";

	@Autowired
	public HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	protected void viewPrint(String paramString) {
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html; charset=UTF-8");
		try {
			this.response.getOutputStream()
					.write(paramString.getBytes("UTF-8"));
			return;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
