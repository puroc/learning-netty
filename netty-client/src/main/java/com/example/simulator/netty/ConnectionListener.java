/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package com.example.simulator.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

public class ConnectionListener implements ChannelFutureListener {
	private TcpNettyClient client;

	public ConnectionListener(TcpNettyClient client) {
		this.client = client;
	}

	public void operationComplete(ChannelFuture channelFuture) throws Exception {
      if(!channelFuture.isSuccess()) {
         System.out.println("Reconnect");
         EventLoop loop = channelFuture.channel().eventLoop();
         loop.schedule(new Runnable() {  
             public void run() {  
                 client.connect();  
               }} , 1L, TimeUnit.SECONDS);
      }

   }
}