package xyz.visonforcoding.carambola.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import xyz.visonforcoding.carambola.entity.User;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByToken(String token);
}
