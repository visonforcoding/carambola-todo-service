package xyz.visonforcoding.carambola.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.visonforcoding.carambola.entity.User;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
