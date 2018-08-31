package org.tbwork.roo.server.core;

import org.tbwork.roo.common.handler.ExceptionHandler;
import org.tbwork.roo.server.core.handler.ServerMainMessageHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RooServerChannelInitializer extends ChannelInitializer<SocketChannel>{
  
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(
					new ExceptionHandler(),
	        		new ObjectEncoder(),
	       		    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
	        		new ServerMainMessageHandler()
				); 
	}
	
}
