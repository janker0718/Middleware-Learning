package top.janker.dubbo.example.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import top.janker.dubbo.example.api.HelloService;

public class HelloServiceTest extends DubboExampleConsumerApplicationTests {

    @DubboReference(version = "1.0.0")
    private HelloService helloService;

    @Test
    public void saveHello(){
        String result = helloService.saveHello("刘志永");
        System.out.println(result);
    }

}
