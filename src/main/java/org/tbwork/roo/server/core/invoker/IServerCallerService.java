package org.tbwork.roo.server.core.invoker;

import org.tbwork.roo.common.model.ITaskParameter;
import org.tbwork.roo.common.model.InvokeContext;
import org.tbwork.roo.server.model.ServerInvokeContext;

public interface IServerCallerService {

	 public InvokeContext getInvokeContext(String guid);
	  
	/**
	 * @param clientId the client id of target client
	 * @param taskName the taskname
	 * @param parameter the parameters would be transferred to remote task.
	 * @return
	 */
	public ServerInvokeContext registerInvokeContext(Long clientId, String taskName, ITaskParameter parameter);
	
	public void removeInvokeContext(String guid);
	
}
