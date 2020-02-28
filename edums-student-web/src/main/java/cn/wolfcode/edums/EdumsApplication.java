package cn.wolfcode.edums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Leon
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = "cn.wolfcode.edums")
public class EdumsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EdumsApplication.class, args);
    }
}
