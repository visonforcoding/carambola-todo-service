package xyz.visonforcoding.carambola.entity;

import java.io.Serializable;

/**
 * 登录用户对象
 *
 * @author
 */
public class UserInfo<T> implements Serializable {

    private String username;

    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
