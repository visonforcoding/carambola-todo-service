package xyz.visonforcoding.carambola.service;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.entity.UserInfo;
import xyz.visonforcoding.carambola.repository.UserRepository;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.App;

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
    UserRepository userRepository;

    public User getCurrentUser() {
        if (env.equals("dev")) {
            Optional<User> user = userRepository.findById(1L);
            if (user.isPresent()) {
                return user.get();
            }
        }
        Object sessionUser = request.getSession().getAttribute(App.SESSION_USER);
        UserInfo<User> userInfo = (UserInfo<User>) sessionUser;
        return userInfo.getT();
    }

}
