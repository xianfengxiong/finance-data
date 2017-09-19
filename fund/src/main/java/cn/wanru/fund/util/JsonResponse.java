package cn.wanru.fund.util;

/**
 * @author xxf
 * @date 17-9-12
 */
public class JsonResponse<T> {

    private String code;
    private String msg;
    private T data;

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public JsonResponse<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonResponse<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public JsonResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
}
