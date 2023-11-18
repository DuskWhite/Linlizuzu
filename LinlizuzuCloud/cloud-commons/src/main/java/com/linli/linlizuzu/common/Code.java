package com.linli.linlizuzu.common;

/**
 * @author Damumu
 */

public enum Code {
    /** 成功 200
     *  接口错误 500
     *  查询值为空 404
     *  */
    SUCCESS(200),FAILED(500),NOTFOUND(404);

    private int code;

    Code(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
