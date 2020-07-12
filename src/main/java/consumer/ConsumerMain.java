package consumer;

import framework.Invocation;
import framework.ProxyFactory;
import protocol.http.HttpClient;
import provider.api.IHelloService;

public class ConsumerMain {

    public static void main(String[] args) {

//        method1();

        method2();

    }
    // 1、常规写法
    public static void method1(){
        HttpClient httpClient = new HttpClient();
        Invocation invocation = new Invocation(IHelloService.class.getName(),"sayHello",new Class[]{String.class},new Object[]{"靓仔"});
        String result = httpClient.send("localhost",8080,invocation);
        System.out.println(result);
    }

    // 2、jdk动态代理
    public static void method2(){
        IHelloService helloService = ProxyFactory.getProxy(IHelloService.class);
        System.out.println(helloService.sayHello("靓仔"));
    }

}
