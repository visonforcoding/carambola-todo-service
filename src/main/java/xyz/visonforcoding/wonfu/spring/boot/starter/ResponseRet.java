package xyz.visonforcoding.wonfu.spring.boot.starter;

/**
 *
 * @author caowenpeng
 */
public class ResponseRet {

    public static Integer Success = 0;

    /**
     * 参数错误
     */
    public static Integer parametrErrror = 510;
    /**
     * 未登录
     */
    public static Integer unLogin = 401;

    /**
     * 登录失败
     */
    public static Integer loginFail = 511;

    /**
     * 数据库执行异常
     */
    public static Integer dbExecuteFail = 1001;

    /**
     * 无数据
     */
    public static Integer noEntity = 2001;

    public static Integer deleteFail = 2002;
}
