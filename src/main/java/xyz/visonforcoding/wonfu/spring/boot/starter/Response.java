package xyz.visonforcoding.wonfu.spring.boot.starter;

import xyz.visonforcoding.wonfu.spring.boot.starter.config.App;
import java.util.ArrayList;
import java.util.List;

public class Response {

    private int code;
    private String msg;
    private Object data;
    private String _uniq_req_no = App._uniq_req_no.get();

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
        List list = new ArrayList();
        this.data = list;
    }

    public void setRet(int ret) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public String getUniq_req_no() {
        return _uniq_req_no;
    }

    public void setUniq_req_no(String _uniq_req_no) {
        this._uniq_req_no = _uniq_req_no;
    }

}
