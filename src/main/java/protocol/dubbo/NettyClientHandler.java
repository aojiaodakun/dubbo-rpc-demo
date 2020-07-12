package protocol.dubbo;

import framework.Invocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import provider.api.IHelloService;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {

    private Invocation invocation;

    private String result = "";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("client :" + ctx);
        Invocation invocation = new Invocation(IHelloService.class.getName(),"sayHello",new Class[]{String.class},new Object[]{"靓仔"});
        ctx.writeAndFlush(invocation);

//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server,我是客户端", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        result = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("服务端回复的消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
//        System.out.println("服务端的地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    @Override
    public String call() throws Exception {
        return result;
    }


    public Invocation getInvocation() {
        return invocation;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }



}
