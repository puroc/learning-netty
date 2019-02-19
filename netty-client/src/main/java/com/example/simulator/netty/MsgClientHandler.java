/**
 * <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao.
 **/
package com.example.simulator.netty;

import com.example.simulator.Client;
import com.example.simulator.Config;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgClientHandler extends AbstractHandler {

    private Logger log = LoggerFactory.getLogger(MsgClientHandler.class);

    private Client client;

    public MsgClientHandler(Client client) {
        super();
        this.client = client;
    }

    protected void messageReceived(ChannelHandlerContext ctx, Object message) {
//        ByteBuf buffer = (ByteBuf) message;
//        byte[] msg = new byte[buffer.capacity()];
//
//        for (int i = 0; i < buffer.capacity(); ++i) {
//            msg[i] = buffer.getByte(i);
//        }
        System.out.println("receive msg");
    }

    protected void sessionException(ChannelHandlerContext ctx) {
        this.log.error("sessionException,remoteAddress:" + ctx.channel().remoteAddress());
    }

    protected void sessionOpen(ChannelHandlerContext ctx) {
        try {
            this.log.info("sessionOpen,remoteAddress:" + ctx.channel().remoteAddress());
            ((TcpNettyClient)this.client).setCtx(ctx);
            for (int i = 0; i < Config.getInstance().getThreadNum(); i++) {
               this.client.send("Thread-" + i);
            }
        } catch (Throwable arg2) {
            arg2.printStackTrace();
        }

    }




}