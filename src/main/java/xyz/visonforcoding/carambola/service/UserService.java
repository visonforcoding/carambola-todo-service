package xyz.visonforcoding.carambola.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.repository.UserRepository;
import xyz.visonforcoding.carambola.utils.Security;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.Log;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@Service
public class UserService {

    @Value("${app.security.salt}")
    private String salt;

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user, String password) {
        String pass = Security.encrypt(password, salt);
        Log.debug("密码", pass);
        user.setPassword(pass);
        userRepository.save(user);
        return user;
    }

}
