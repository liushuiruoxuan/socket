package com.xt.guigu.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup 和 WorkerGroup
        /**
         * 1.创建两个线程组 bossGroup 和 workerGroup
         * 2.bossGroup 只是处理链接请求，真正和客户端业务处理的，会交给workerGroup完成
         * 3.两个都是无限循环
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器端启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        //使用链式编程来进行设置
        bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                .channel(NioServerSocketChannel.class) //使用NioSocketChannel 作为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到线程数
                .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(null);
                    }
                }); //给我们的workerGroup 的EventLoop对应的管道设置处理器

        System.out.println("。。。。。。服务器 is ready。。。");

        //绑定一个端口并且同步
        ChannelFuture cf = bootstrap.bind(6668).sync();

        //对关闭通道进行监听
        cf.channel().closeFuture().sync();
    }
}
