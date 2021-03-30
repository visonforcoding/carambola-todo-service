package xyz.visonforcoding.carambola.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.visonforcoding.carambola.AppGlobal;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.entity.UserInfo;
import xyz.visonforcoding.carambola.repository.UserRepository;
import xyz.visonforcoding.carambola.utils.Security;
import xyz.visonforcoding.wonfu.spring.boot.starter.Response;
import xyz.visonforcoding.wonfu.spring.boot.starter.ResponseRet;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.App;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${app.security.salt}")
    private String salt;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/hand-login", method = RequestMethod.POST)
    public Response loginAction(HttpServletRequest request, HttpSession session) {
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        if (username == null || pwd == null) {
            return new Response(ResponseRet.parametrErrror, "参数错误");
        }
        User ckUser = userRepository.findByUsername(username);
        if (ckUser != null) {
            String userPwd = ckUser.getPassword();
            if (Security.verifyPassword(userPwd, pwd, this.salt)) {
                UserInfo userInfo = new UserInfo<User>();
                userInfo.setUsername(ckUser.getUsername());
                userInfo.setT(ckUser);
                session.setAttribute(App.SESSION_USER, userInfo);
                ThreadLocal<UserInfo> threadLocalUser = new ThreadLocal<UserInfo>();
                threadLocalUser.set(userInfo);
                AppGlobal.userinfo = threadLocalUser;
                Map data = new HashMap();
                data.put("username", ckUser.getUsername());
                data.put("jumpUrl", "/");
                return new Response(0, "登录成功", data);
            }
        } else {
            return new Response(602, "该用户未注册");
        }
        return new Response(ResponseRet.loginFail, "未登录");
    }

    @GetMapping(path = "/login-out")
    public Response loginOut(HttpSession session) {
        Object userInfo = session.getAttribute(App.SESSION_USER);
        if (userInfo != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            UserInfo user = objectMapper.convertValue(userInfo, UserInfo.class);
            session.removeAttribute(App.SESSION_USER);
        }
        return new Response(ResponseRet.Success, "");
    }

}
