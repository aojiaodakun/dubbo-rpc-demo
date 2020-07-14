package framework;

import protocol.dubbo.DubboProtocol;
import protocol.dubbo.NettyClient;
import protocol.http.HttpClient;
import register.RemoteMapRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static <T> T getProxy(Class interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 方式1：修改源代码指定实现类
                Protocol protocol = new DubboProtocol();
                // 方式2：简单工厂+VM option：-DprotocolName=dubbo
                protocol = ProtocolFactory.getProtocol();
                // 方式3：javaspi


                Invocation invocation = new Invocation(interfaceClass.getName(),method.getName(),method.getParameterTypes(),args);

                // 随机策略
                URL url = RemoteMapRegister.random(interfaceClass.getName());
                String result = protocol.send(url,invocation);
                return result;
            }
        });
    }

}
