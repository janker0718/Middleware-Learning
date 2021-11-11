package top.janker.dubbo.example.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages = {"top.janker.dubbo.example.provider.impl"})
public class DubboExampleProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboExampleProviderApplication.class, args);
    }

}
