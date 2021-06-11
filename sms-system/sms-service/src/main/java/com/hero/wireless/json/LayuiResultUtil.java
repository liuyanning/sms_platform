package com.hero.wireless.json;

import org.apache.commons.lang3.StringUtils;

import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.web.exception.BaseException;

public class LayuiResultUtil {

	public static LayUiJsonObjectFmt success(Object data) {
		return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, LayUiObjectMapper.CODE_SUCCESS_MSG, data);
	}

	public static LayUiJsonObjectFmt success(String msg) {
		if (StringUtils.isBlank(msg)) {
			msg = LayUiObjectMapper.CODE_SUCCESS_MSG;
		}
		return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, msg, msg);
	}

	public static LayUiJsonObjectFmt success() {
		return success(null);
	}

	public static LayUiJsonObjectFmt fail(String msg) {
		return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_FAILD, msg);
	}

	public static LayUiJsonObjectFmt sessionOut() {
		return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SESSION_OUT, LayUiObjectMapper.CODE_SESSION_OUT_MSG);
	}

	public static LayUiJsonObjectFmt error(ServiceException se) {
		return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_ERROR,
				StringUtils.defaultString(se.getMessage(), se.getErrorCode()));
	}

	public static LayUiJsonObjectFmt error(Exception e) {
		return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_ERROR, "未知错误，请联系客服");
	}

	public static LayUiJsonObjectFmt error(BaseException e) {
		return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_ERROR,
				StringUtils.defaultString(e.getMessage(), e.getExceptionKey().toString()));
	}
}
