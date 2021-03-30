package xyz.visonforcoding.carambola.task;

import xyz.visonforcoding.wonfu.spring.boot.starter.config.App;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.Log;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.repository.UserRepository;
import xyz.visonforcoding.carambola.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@Component
public class UserTask implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... strings) throws Exception {
        Log.debug("task启动...");

        System.out.print(App.sqlCount.get());
        User root = userRepository.findByUsername("root");
        if (root == null) {
            Log.debug("创建管理员账号");
            root = new User();
            root.setUsername("root");
            root.setEmail("root@123.com");
            userService.createUser(root, "asdqwe123");
        }
    }

}
