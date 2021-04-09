package xyz.visonforcoding.carambola.service;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.entity.UserInfo;
import xyz.visonforcoding.carambola.repository.UserRepository;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.App;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.Log;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@Service
public class AppService {

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        Object sessionUser = request.getSession().getAttribute(App.SESSION_USER);
        UserInfo<User> userInfo = (UserInfo<User>) sessionUser;
        return userInfo.getT();
    }

    public void initSession() {
        if (!setSessionByToken()) {
            clearSession();
        }
    }

    public Boolean setSessionByToken() {
        String token = request.getHeader("x-token");
        Log.info("setSessionByToken..." + token);
        if (token == null || token.isEmpty()) {
            return false;
        }
        Optional<User> user = userRepository.findByToken(token);
        if (user.isEmpty()) {
            return false;
        }
        setSession(user.get());
        return true;
    }

    public void clearSession() {
        request.getSession().invalidate();
    }

    public void setSession(User user) {
        UserInfo userInfo = new UserInfo<User>();
        userInfo.setUsername(user.getUsername());
        userInfo.setT(user);
        session.setAttribute(App.SESSION_USER, userInfo);
    }

}
