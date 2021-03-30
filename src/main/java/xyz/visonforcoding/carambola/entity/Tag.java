package xyz.visonforcoding.carambola.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@Entity
public class Tag extends BaseEntity {

    @NotNull(message = "标签名称不可为空")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT), nullable = false)
    private User user;
    private String color;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
////    @JoinTable(name = "tag_task", joinColumns = {
////        @JoinColumn(name = "tag_id", referencedColumnName = "id")
////    }, inverseJoinColumns = {
////        @JoinColumn(name = "task_id", referencedColumnName = "id")
////    }, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
////            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
////    @JsonIgnoreProperties(value = {"tags"})
////    @JsonBackReference
//    @JsonIgnoreProperties(value = {"tags"})
//    private List<Task> tasks;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

//    public List<Task> getTask() {
//        return tasks;
//    }
//
//    public void setTask(List<Task> task) {
//        this.tasks = task;
//    }
    @Override
    public String toString() {

        return "xyz.visonforcoding.carambola.entity.Tag[ id=" + id + " ]";
    }

}
