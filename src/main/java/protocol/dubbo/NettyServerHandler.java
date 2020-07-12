package protocol.dubbo;

import framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import provider.LocalRegister;

import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation)msg;
        Class serviceImpl = LocalRegister.get(invocation.getInterfaceName());
        Method method = serviceImpl.getMethod(invocation.getMethodName(),invocation.getParamTypes());
        Object result = method.invoke(serviceImpl.newInstance(),invocation.getParams());

        System.out.println("netty----------------" + result.toString());
        ctx.writeAndFlush("netty:" + result);
    }
}
