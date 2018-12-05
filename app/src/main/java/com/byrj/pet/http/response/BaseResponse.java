package com.byrj.pet.http.response;

/**
 * 接口返回格式
 *
 * @param <T>
 * @author xll
 */
public class BaseResponse<T> {

    /**
     * true 代表业务正常
     */
    private boolean success;

    /**
     * 错误信息 success = false的时候才有值
     */
    private String msg;

    /**
     * 返回码
     */
    private int code;

    /**
     * 具体返回内容
     */
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
