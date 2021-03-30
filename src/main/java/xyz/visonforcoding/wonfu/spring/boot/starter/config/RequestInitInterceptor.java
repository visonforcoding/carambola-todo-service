package xyz.visonforcoding.wonfu.spring.boot.starter.config;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author caowenpeng
 */
public class RequestInitInterceptor implements HandlerInterceptor {

    @Autowired
    private ReqExtProperty reqExtProperty;

    public RequestInitInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        App._uniq_req_no.set(UUID.randomUUID().toString());
//        App.sqlCount.set(0);
        reqExtProperty.sqlCount.set(0);
        App.sqlDuration.set(0L);
        Log.setMsgTraceNo(App._uniq_req_no.get());
        MDC.put("REQ_NO", App._uniq_req_no.get());
        Log.info("sqlCount", App.sqlCount.get());
        Log.info("request start... || ");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Log.info("完成postHandle", App.sqlCount.get());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Log.info("完成", App.sqlCount.get());
        Log.info(String.format("finish request... || sql执行次数:%s sql总耗时:%s ms", reqExtProperty.sqlCount.get(), App.sqlDuration.get()));
        App.sqlCount.remove();
        App.sqlDuration.remove();
        App._uniq_req_no.remove();
    }

}
