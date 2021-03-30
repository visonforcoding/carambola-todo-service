package xyz.visonforcoding.carambola.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.visonforcoding.carambola.entity.Task;
import xyz.visonforcoding.carambola.entity.TaskList;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.repository.TaskListRepository;
import xyz.visonforcoding.carambola.repository.TaskRepository;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@Service
public class TaskListService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    public Iterable<TaskList> getAllTaskListWithTaskByUser(User user) {
        Iterable<Task> tasks = taskRepository.findAllByUser(user);
        return null;
    }

}
