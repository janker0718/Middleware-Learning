package top.janker.dubbo.example.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboExampleConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboExampleConsumerApplication.class, args);
    }

}
