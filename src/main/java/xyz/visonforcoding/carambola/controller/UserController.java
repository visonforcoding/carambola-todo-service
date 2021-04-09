package xyz.visonforcoding.carambola.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.visonforcoding.carambola.AppGlobal;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.entity.UserInfo;
import xyz.visonforcoding.carambola.repository.UserRepository;
import xyz.visonforcoding.carambola.service.AppService;
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

    @Autowired
    private AppService appService;

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
                appService.setSession(ckUser);
                UUID tokenUuid = UUID.randomUUID();
                ckUser.setToken(tokenUuid.toString());
                userRepository.save(ckUser);
                Map data = new HashMap();
                data.put("username", ckUser.getUsername());
                data.put("token", ckUser.getToken());
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

    @PostMapping(path = "/register")
    public Response register(@Valid @RequestBody User user) {
        user.setPassword(Security.encrypt(user.getPassword(), salt));
        userRepository.save(user);
        return new Response(ResponseRet.Success, "注册成功");
    }

    @GetMapping()
    public Response users() {
        Iterable<User> users = userRepository.findAll();
        return new Response(ResponseRet.Success, "注册成功", users);
    }

}
