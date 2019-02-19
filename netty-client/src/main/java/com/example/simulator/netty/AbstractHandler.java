/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package com.example.simulator.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class AbstractHandler extends SimpleChannelInboundHandler<Object> {
	private Logger log = LoggerFactory.getLogger(AbstractHandler.class);
	private TcpNettyClient client;

	public void setTcpClient(TcpNettyClient client) {
		this.client = client;
	}

	protected void channelRead0(ChannelHandlerContext ctx, Object message) throws Exception {
		this.messageReceived(ctx, message);
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.sessionOpen(ctx);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      this.log.info("reconnect,remoteAddress:" + ctx.channel().remoteAddress());
      EventLoop eventLoop = ctx.channel().eventLoop();
      eventLoop.schedule(new Runnable() {  
          public void run() {  
            client.connect();  
          }}  , 1L, TimeUnit.SECONDS);
      super.channelInactive(ctx);
   }

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
		this.sessionException(ctx);
	}



	protected abstract void messageReceived(ChannelHandlerContext arg0, Object arg1);

	protected abstract void sessionException(ChannelHandlerContext arg0);

	protected abstract void sessionOpen(ChannelHandlerContext arg0);
}