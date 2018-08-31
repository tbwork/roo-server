package org.tbwork.roo.server.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbwork.roo.common.message.c_2_s.TaskResultMessage;
import org.tbwork.roo.common.model.InvokeContext;
import org.tbwork.roo.server.core.invoker.IServerCallerService;
import org.tbwork.roo.server.core.invoker.impl.ServerCallerService;

import com.alibaba.fastjson.JSON;

public class ServerMainMessageHandler extends AbstractMessageHandler{

	private static final IServerCallerService scs = ServerCallerService.getCallerService();
	private static final Logger logger = LoggerFactory.getLogger(ServerMainMessageHandler.class);
	
	
	@Override
	public void tackleWithTaskResultMessage(TaskResultMessage trm) {
		
		String invokeId = trm.getInvokeId();
		InvokeContext invokeContext = scs.getInvokeContext(invokeId);
		if(invokeContext != null){
			try{ 
				if(!invokeContext.isTackled()){
					invokeContext.lock();  
					if(!invokeContext.isTackled()){
						
						//to do logics here
						
						
						invokeContext.setTackled(true);
					} 
				} 
			}
			catch(Exception e){
				logger.warn("Tackle with task result message failed due to {}", e.getMessage(), e);
			}
			finally{ 
				invokeContext.unlock();
				scs.removeInvokeContext(invokeId);
			}
		}
		//ignore useless response
		logger.warn("There is no invoker context for the current response received: {}", JSON.toJSONString(trm));
		 
	}

}
