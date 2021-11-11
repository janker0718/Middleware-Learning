package top.janker.dubbo.example.providder;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.janker.dubbo.example.api.HelloService;

public class HelloServiceTest extends DubboExampleProvidderApplicationTests {

    @DubboReference(version = "1.0.0",url = "dubbo://127.0.0.1:20880")
    private HelloService helloService;

    @Test
    public void saveHello(){
        String result = helloService.saveHello("刘志永");
        System.out.println(result);
    }

}
