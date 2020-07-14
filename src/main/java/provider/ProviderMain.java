package provider;

import framework.Protocol;
import framework.ProtocolFactory;
import framework.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import protocol.dubbo.DubboProtocol;
import protocol.dubbo.NettyServer;
import protocol.http.HttpServer;
import provider.api.IHelloService;
import provider.impl.HelloServiceImpl;
import register.RemoteMapRegister;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ProviderMain {

    public static void main(String[] args) throws Exception {

        // 1、本地注册
        LocalRegister.register(IHelloService.class.getName(), HelloServiceImpl.class);

        // 2、远程注册
        URL url = new URL("localhost",8080);
        RemoteMapRegister.register(IHelloService.class.getName(),url);


        // 3、启动tomcat

        // 方式1：简单工厂
//        Protocol protocol = ProtocolFactory.getProtocol();
//        protocol.start(url);

        /**
         * 方式2：java-spi
         * protocol.dubbo.DubboProtocol
         */
//        Protocol protocol = null;
//        ServiceLoader<Protocol> serviceLoader = ServiceLoader.load(Protocol.class);
//        Iterator<Protocol> it = serviceLoader.iterator();
//        while (it.hasNext()){
//            protocol = it.next();
//        }
//        protocol.start(url);


        /**
         * 方式3：dubbo-spi
         * dubbo=protocol.dubbo.DubboProtocol
         * http=protocol.http.HttpProtocol
          */
        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = extensionLoader.getExtension("dubbo");
        protocol.start(url);


        // 阻塞
        System.in.read();

    }

}
