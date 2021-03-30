package xyz.visonforcoding.wonfu.spring.boot.starter.config;

/**
 *
 * 项目配置常量
 */
public class App {

    public static final String SESSION_USER = "user"; // 用户对象
    public static ThreadLocal<String> _uniq_req_no = new ThreadLocal<String>();
    public static ThreadLocal<Integer> sqlCount = ThreadLocal.withInitial(() -> 0);
    public static ThreadLocal<Long> sqlDuration = ThreadLocal.withInitial(() -> 0L);
}
