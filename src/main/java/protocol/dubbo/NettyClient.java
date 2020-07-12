package protocol.dubbo;

import framework.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient<T> {

    public NettyClientHandler client = null;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public void start(String hostName,Integer port){
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder",
                                new ObjectDecoder(
                                        ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                        pipeline.addLast("encoder",new ObjectEncoder());
                        pipeline.addLast("handle",new NettyClientHandler());
                    }
                });
         try {
             ChannelFuture channelFuture = b.connect(hostName,port).sync();
             // 对关闭通道进行监听
             channelFuture.channel().closeFuture().sync();
         }catch (Exception e){
             e.printStackTrace();
         }
    }

    public String send(String hostName, Integer port, Invocation invocation){
        if(client == null){
            start(hostName,port);
        }
        client.setInvocation(invocation);
        try {
            return executorService.submit(client).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }



}
