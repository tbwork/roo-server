package org.tbwork.roo.server.core.invoker.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tbwork.roo.common.model.ITaskParameter;
import org.tbwork.roo.common.model.InvokeContext; 
import org.tbwork.roo.common.util.IDGenerator;
import org.tbwork.roo.server.core.invoker.IServerCallerService;
import org.tbwork.roo.server.model.ServerInvokeContext;

public class ServerCallerService implements IServerCallerService{

	public static final Map<String, InvokeContext> invokeMap = new ConcurrentHashMap<String, InvokeContext>();
	
	private static class SingletonServerCallerService { 
		private static final ServerCallerService callerService = new ServerCallerService(); 
	}
	
	public static ServerCallerService getCallerService(){
		return SingletonServerCallerService.callerService;
	}
	
	public InvokeContext getInvokeContext(String guid) {
		return invokeMap.get(guid);
	}

	public ServerInvokeContext registerInvokeContext(Long clientId, String taskName, ITaskParameter parameter) { 
		ServerInvokeContext ic = new ServerInvokeContext();
		String invokeId = IDGenerator.getGuid();
		ic.setInvokeId(invokeId);
		ic.setClientId(clientId);
		ic.setTaskName(taskName);
		ic.setTaskParameter(parameter);
		invokeMap.put(invokeId, ic);
		return ic;
	}

	public void removeInvokeContext(String guid) {
		invokeMap.remove(guid);
	}

	
	
}
