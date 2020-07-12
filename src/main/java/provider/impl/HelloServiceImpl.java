package provider.impl;

import provider.api.IHelloService;

public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String username) {
        return "hello " + username;
    }
}
