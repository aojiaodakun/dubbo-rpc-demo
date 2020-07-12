package framework;

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
                NettyClient httpClient = new NettyClient();
                Invocation invocation = new Invocation(interfaceClass.getName(),method.getName(),method.getParameterTypes(),args);


                // 随机策略
                URL url = RemoteMapRegister.random(interfaceClass.getName());
                String result = httpClient.send(url.getHostname(),url.getPort(),invocation);
                return result;
            }
        });
    }

}
