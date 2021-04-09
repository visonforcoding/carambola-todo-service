package xyz.visonforcoding.carambola.controller;

import java.util.Iterator;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.visonforcoding.carambola.entity.TaskList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.visonforcoding.carambola.entity.Tag;
import xyz.visonforcoding.carambola.entity.Task;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.repository.TagRepository;
import xyz.visonforcoding.carambola.repository.TaskListRepository;
import xyz.visonforcoding.carambola.repository.TaskRepository;
import xyz.visonforcoding.carambola.service.AppService;
import xyz.visonforcoding.wonfu.spring.boot.starter.Response;
import xyz.visonforcoding.wonfu.spring.boot.starter.ResponseRet;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.Log;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.LoginRequired;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@RestController
@RequestMapping("/task")
@LoginRequired
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AppService appService;

    @GetMapping()
    public Response task(@RequestParam Long taskId) {
        User user = appService.getCurrentUser();
        Optional<Task> task = taskRepository.findByIdAndUser(taskId, user);
        if (task.isPresent()) {
            return new Response(0, "获取成功", task.get());
        }
        return new Response(ResponseRet.noEntity, "获取失败");
    }

    @GetMapping(path = "/list")
    public Response list() {
        User user = appService.getCurrentUser();
        Iterable<Task> tasks = taskRepository.findAllByUser(user);
        return new Response(0, "获取成功", tasks);
    }

    @PostMapping(path = "/add")
    public Response post(@Valid @RequestBody Task task) {
        Long listId = task.getList().getId();
        Log.info("request list id..", task.getList());
        Optional<TaskList> list = taskListRepository.findById(listId);
        if (list.isPresent()) {
            task.setList(list.get());
            task.setUser(appService.getCurrentUser());
            taskRepository.save(task);

        }
        return new Response(0, "插入成功", task);
    }

    @GetMapping(path = "/delete")
    public Response delete(@RequestParam Long taskId) {
        User user = appService.getCurrentUser();
        Optional<Task> task = taskRepository.findByIdAndUser(taskId, user);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
        }
        return new Response(0, "删除成功");
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public Response updateList(@RequestParam Long listId, @RequestParam Long taskId) {
        Optional<TaskList> taskList = taskListRepository.findByIdAndUser(listId, appService.getCurrentUser());
        Optional<Task> task = taskRepository.findByIdAndUser(taskId, appService.getCurrentUser());
        if (taskList.isPresent() && task.isPresent()) {
            task.get().setList(taskList.get());
            taskRepository.save(task.get());
            return new Response(0, "更新成功");

        }
        return new Response(0, "更新失败");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Response updateTask(@Valid @RequestBody Task task) {
        Optional<Task> theTask = taskRepository.findByIdAndUser(task.getId(), appService.getCurrentUser());
        if (theTask.isPresent()) {
            task.getTags().forEach((Tag tag) -> {
                Optional<Tag> theTag = tagRepository.findById(tag.getId());
                if (theTag.isEmpty()) {
                    task.getTags().remove(tag);
                }
            });
        }
        taskRepository.save(task);
        return new Response(0, "更新成功");
    }

    @RequestMapping(path = "/tag", method = RequestMethod.POST)
    public Response addTag(@RequestParam Long tagId, @RequestParam Long taskId) {
        Optional<Tag> tag = tagRepository.findById(tagId);
        Optional<Task> task = taskRepository.findByIdAndUser(taskId, appService.getCurrentUser());
        task.get().getTags().add(tag.get());
        taskRepository.save(task.get());

        return new Response(0, "获取成功");
    }

}
