package provider;

import framework.URL;
import protocol.dubbo.NettyServer;
import protocol.http.HttpServer;
import provider.api.IHelloService;
import provider.impl.HelloServiceImpl;
import register.RemoteMapRegister;

public class ProviderMain {

    public static void main(String[] args) {

        // 1、本地注册
        LocalRegister.register(IHelloService.class.getName(), HelloServiceImpl.class);

        // 2、远程注册
        URL url = new URL("localhost",7000);
        RemoteMapRegister.register(IHelloService.class.getName(),url);


        // 3、启动tomcat
        NettyServer httpServer = new NettyServer();
        httpServer.start("localhost",7000);

    }

}
