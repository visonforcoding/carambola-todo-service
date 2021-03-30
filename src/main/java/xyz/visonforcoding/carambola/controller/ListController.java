package xyz.visonforcoding.carambola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.visonforcoding.carambola.entity.Task;
import xyz.visonforcoding.carambola.entity.TaskList;
import xyz.visonforcoding.carambola.entity.User;
import xyz.visonforcoding.carambola.repository.TaskListRepository;
import xyz.visonforcoding.carambola.service.AppService;
import xyz.visonforcoding.wonfu.spring.boot.starter.Response;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@RestController
@RequestMapping("/list")
public class ListController {
    
    @Autowired
    private TaskListRepository taskListRepository;
    
    @Autowired
    private AppService appService;
    
    @PostMapping(path = "/add")
    public Response add(@RequestBody TaskList list) {
        User user = appService.getCurrentUser();
        list.setUser(user);
        taskListRepository.save(list);
        return new Response(0, "插入成功", list);
    }
    
    @GetMapping(path = "/my")
    public Response list() {
        User user = appService.getCurrentUser();
        Iterable<TaskList> taskLists = taskListRepository.findAllByUser(user);
        return new Response(0, "获取成功", taskLists);
    }
    
}
