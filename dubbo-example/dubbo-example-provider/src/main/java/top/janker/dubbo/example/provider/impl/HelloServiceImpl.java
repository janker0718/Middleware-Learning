package top.janker.dubbo.example.provider.impl;

import org.apache.dubbo.config.annotation.DubboService;
import top.janker.dubbo.example.api.HelloService;

@DubboService(version = "1.0.0")
public class HelloServiceImpl  implements HelloService {

    @Override
    public String saveHello(String name) {
        return "hello,"+name;
    }
}
