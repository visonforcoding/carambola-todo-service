package xyz.visonforcoding.carambola;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.WebConfig;

@SpringBootApplication(exclude = {})
@EnableJpaAuditing
@ComponentScan
@Import(WebConfig.class)
public class Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(Application.class, args);
    }

}
