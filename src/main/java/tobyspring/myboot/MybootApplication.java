package tobyspring.myboot;

import org.springframework.boot.SpringApplication;
import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class MybootApplication {

    public static void main(String[] args) {
//        MySpringApplication.run(MybootApplication.class, args);
        SpringApplication.run(MybootApplication.class, args);
    }
}