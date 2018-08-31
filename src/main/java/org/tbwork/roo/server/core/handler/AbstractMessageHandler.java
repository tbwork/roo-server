package org.tbwork.roo.server.core.handler;

import org.tbwork.roo.common.enums.MessageType; 
import org.tbwork.roo.common.message.Message;
import org.tbwork.roo.common.message.c_2_s.TaskResultMessage;
 
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class AbstractMessageHandler extends SimpleChannelInboundHandler<Message>{

	
	public abstract void tackleWithTaskResultMessage(TaskResultMessage trm);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		 
	     if(MessageType.C2S_TASK_RESULT.equals(msg.getType()))	{
	    	 tackleWithTaskResultMessage((TaskResultMessage) msg);
	     }
	     
	     ctx.fireChannelRead(msg);
	}
	
}
