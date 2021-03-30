package xyz.visonforcoding.carambola.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import xyz.visonforcoding.carambola.entity.TaskList;
import xyz.visonforcoding.carambola.entity.User;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public interface TaskListRepository extends CrudRepository<TaskList, Long> {

    @Query(value = "select tl from TaskList tl left join Task t on t.list.id = tl.id where t.user.id = ?1")
    public Iterable<TaskList> findAllByTasksUser(Long userid);

    public Iterable<TaskList> findAllByUser(User user);

    public Optional<TaskList> findByIdAndUser(Long listId, User user);

}
