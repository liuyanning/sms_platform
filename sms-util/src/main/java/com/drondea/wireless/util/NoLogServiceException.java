package com.drondea.wireless.util;

import com.drondea.wireless.config.ResultStatus;

/**
 * @version V3.0.0
 * @description: 不打印
 * @author: 刘彦宁
 * @date: 2020年11月24日19:20
 **/
public class NoLogServiceException extends  ServiceException {
    public NoLogServiceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public NoLogServiceException(String message) {
        super(message);
    }

    public NoLogServiceException(ResultStatus resultStatus) {
        super(resultStatus);
    }

    public NoLogServiceException(ResultStatus resultStatus, String content) {
        super(resultStatus, content);
    }
}
