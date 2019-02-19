/**
 * <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao.
 **/
package com.example.simulator.netty;

import com.example.simulator.Client;
import com.example.simulator.Config;
import com.example.simulator.Counter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TcpNettyClient  implements Client {

    private ChannelHandlerContext ctx;

    @Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    public TcpNettyClient() {
    }

    public boolean connect() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap e = new Bootstrap();
            e.group(worker);
            e.channel(NioSocketChannel.class);
            final MsgClientHandler handler = new MsgClientHandler(this);
            handler.setTcpClient(this);
            e.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("decoder-SD1", new LineBasedFrameDecoder(1024));
                    pipeline.addLast("encoder-SD1", new Encoder());

//                    pipeline.addLast("decoder-SD1", new StringDecoder());
//                    pipeline.addLast("encoder-SD1", new StringEncoder());
                    pipeline.addLast(handler);
                }
            });
            e.remoteAddress(this.host, Integer.valueOf(this.port).intValue());
            e.option(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));
            e.option(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
            ChannelFuture f = e.connect(this.host, Integer.valueOf(this.port).intValue());
            f.addListener(new ConnectionListener(this)).sync();

        } catch (Exception arg5) {
            arg5.printStackTrace();
        }

        return true;
    }


    @Override
    public void send(String threadName) {
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        sendDebugMsg();
//                        sendMsg4ever(Thread.currentThread().getName());
                    }
                }
        );
        thread.setName(threadName);
        thread.start();
    }

    private void sendDebugMsg() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("please input");
                String str = scan.next();
                if ("1".equalsIgnoreCase(str)) {
                    sendMsg();
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsg4ever(String threadName) {
        System.out.println(threadName + " begin");
        while (true) {
            ChannelFuture channelFuture = this.ctx.writeAndFlush(Config.getInstance().getMsg());
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        Counter.getInstance().add();
                    }
                }
            });

        }
    }

    private void sendMsg() {
        this.ctx.writeAndFlush(Config.getInstance().getMsg());
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}