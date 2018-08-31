package org.tbwork.roo.server.model;

import org.tbwork.roo.common.model.ITaskParameter;
import org.tbwork.roo.common.model.ITaskResult;
import org.tbwork.roo.common.model.InvokeContext;

import lombok.Data;

@Data
public class ServerInvokeContext extends InvokeContext{
	
	private Long clientId;
	
	private String taskName; 
	
	private ITaskParameter taskParameter;
	
	private ITaskResult taskResult;

}
