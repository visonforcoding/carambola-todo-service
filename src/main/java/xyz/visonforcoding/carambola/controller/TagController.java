package xyz.visonforcoding.carambola.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.visonforcoding.carambola.entity.Tag;
import xyz.visonforcoding.carambola.repository.TagRepository;
import xyz.visonforcoding.carambola.service.AppService;
import xyz.visonforcoding.wonfu.spring.boot.starter.Response;
import xyz.visonforcoding.wonfu.spring.boot.starter.ResponseRet;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.LoginRequired;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@RestController
@RequestMapping("/tag")
@LoginRequired
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AppService appService;

    @GetMapping()
    public Response list() {
        Iterable<Tag> tags = tagRepository.findAllByUser(appService.getCurrentUser());
        return new Response(0, "获取成功", tags);
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable String id) {
        Optional<Tag> tag = tagRepository.findById(1L);
        return new Response(0, "获取成功", tag.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
        return null;
    }

    @PostMapping
    public Response post(@RequestBody Tag tag) {
        tag.setUser(appService.getCurrentUser());
        tagRepository.save(tag);
        return new Response(0, "插入成功");
    }

    @DeleteMapping()
    public Response delete(@RequestParam Long tagId) {
        Optional<Tag> theTag = tagRepository.findByIdAndUser(tagId, appService.getCurrentUser());
        if (theTag.isPresent()) {
            tagRepository.delete(theTag.get());
            return new Response(0, "删除成功");
        }
        return new Response(ResponseRet.deleteFail, "删除失败");
    }

}
