package xyz.visonforcoding.carambola.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import xyz.visonforcoding.carambola.entity.Task;
import xyz.visonforcoding.carambola.entity.User;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

    public Iterable<Task> findAllByUser(User user);

    public Optional<Task> findByIdAndUser(Long id, User user);

}
