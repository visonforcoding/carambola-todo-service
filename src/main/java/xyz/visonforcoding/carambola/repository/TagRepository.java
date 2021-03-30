package xyz.visonforcoding.carambola.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import xyz.visonforcoding.carambola.entity.Tag;
import xyz.visonforcoding.carambola.entity.User;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public interface TagRepository extends CrudRepository<Tag, Long> {

    public Iterable<Tag> findAllByUser(User user);

    public Optional<Tag> findByIdAndUser(Long id, User user);

}
