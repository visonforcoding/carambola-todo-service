package xyz.visonforcoding.carambola.entity;

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
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Table;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@Entity
@Table(appliesTo = "task", comment = "任务")
public class Task extends BaseEntity {

    @NotNull(message = "任务名称不可为空")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "任务描述不可为空")
    @Column(nullable = false)
    private String detail;

    @Column(name = "`rank`", columnDefinition = "INT(10) default 1  NOT NULL COMMENT '排序'")
    private Integer rank = 1;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT), nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT), nullable = false)
    @JsonIgnoreProperties(value = {"tasks", "user"})
    private TaskList list;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_task", joinColumns = {
        @JoinColumn(name = "task_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "tag_id", referencedColumnName = "id")
    }, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnoreProperties(value = {"user"})
    private List<Tag> tags;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public TaskList getList() {
        return list;
    }

    public void setList(TaskList list) {
        this.list = list;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "xyz.visonforcoding.carambola.entity.Task[ id=" + id + " ]";
    }

}
