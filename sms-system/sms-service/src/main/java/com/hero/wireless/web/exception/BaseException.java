/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hero.wireless.web.exception;

import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.web.config.ExceptionKey;

/**
 * TODO 取消配置文件
 * 该类需要修改
 * @author Volcano
 * @version 1.1  20190916
 */
public class BaseException extends ServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6689247052590421863L;
	private ExceptionKey exceptionKey = ExceptionKey.UNSUPPORTED_OPERATION_FUN;

	public BaseException(String errorCode, String message) {
		super(errorCode, message);
	}

	public BaseException(String message, Exception e) {
		super(message);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(ExceptionKey key, Object... message) {
		super(key.toString());
		this.exceptionKey = key;
	}

	public ExceptionKey getExceptionKey() {
		return exceptionKey;
	}

}
