package xyz.visonforcoding.wonfu.spring.boot.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.visonforcoding.carambola.service.AppService;
import xyz.visonforcoding.wonfu.spring.boot.starter.Response;
import xyz.visonforcoding.wonfu.spring.boot.starter.ResponseRet;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private AppService appService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Log.info("SecurityInterceptor...", handler);
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LoginRequired classRequired = method.getDeclaringClass().getAnnotation(LoginRequired.class);
        // 判断接口是否需要登录
        LoginRequired methodRequired = method.getAnnotation(LoginRequired.class);
        if (classRequired == null && methodRequired == null) {
            return true;
        }
        appService.initSession(); //token 方式验证
        if (request.getSession().getAttribute(App.SESSION_USER) != null) {
            return true;
        }
        if (request.getHeader("Accept") != null && request.getHeader("Accept").matches("(.*)application/json(.*)")) {
            PrintWriter writer = null;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonObject = objectMapper.writeValueAsString(new Response(ResponseRet.unLogin, "未登录"));
            writer.print(jsonObject);
            return false;
        }
        //未登录的业务逻辑
        response.sendRedirect("/login");
        return false;
    }

}
