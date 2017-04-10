package org.maiya.free.exception;

/**
 * Created by wanglei on 16/11/11.
 */
public class BizBaseException extends Exception {
    private final String code;

    public BizBaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
