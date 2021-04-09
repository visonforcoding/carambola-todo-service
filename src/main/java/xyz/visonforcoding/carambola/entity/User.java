package xyz.visonforcoding.carambola.entity;

import javax.persistence.Column;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "user", comment = "用户")
@DynamicUpdate()
public class User extends BaseEntity {

    @NotBlank(message = "账号不可为空")
    private String username;

    @NotBlank(message = "密码不可为空")
    private String password;

    @NotBlank(message = "邮箱不可为空")
    private String email;
    @Column(columnDefinition = "tinyint(4) NOT NULL COMMENT '0:禁用 1:正常'")
    @ColumnDefault(value = "0")
    private Integer status = 0;

    @ColumnDefault(value = "''")
    @Column(nullable = false)
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
