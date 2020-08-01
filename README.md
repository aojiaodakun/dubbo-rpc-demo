#### 模拟DUBBO-RPC框架
##### 一、dubbo服务导出逻辑：
    1、接口实现类作为ServiceBean，onApplicationEvent(ContextRefreshedEvent event)
    beanFactory装配结束后，执行export方法。
    大概逻辑如下：
    1.1、RegistryProtocol.export
            doLocalExport
                DubboProtocol.export
                    openServer
            #将接口invoker注册到静态map        
            ProviderConsumerRegTable.registerProvider(originInvoker, registryUrl, registedProviderUrl);
            #注册中心注册url 
            register(registryUrl, registedProviderUrl);       
##### 二、本地注册：LocalRegister.Map<String,Class>
    1、模拟Dubbo的ProviderConsumerRegTable.registerProvider
##### 三、远程注册：RemoteMapRegister.Map<String,List<URL>> 
    1、模拟注册中心注册url
##### 四、服务调用：本demo提供了Http协议、dubbo协议（netty）
##### 五、学习一下javaSpi与dubboSpi源码内容
