package org.tbwork.roo.server.lock;

import org.tbwork.roo.common.model.ITaskResult;
 
public class Lock {

	private Long clientId;
	
	private String taskname;
	
	private volatile boolean received;
	
	private ITaskResult taskResult;
	
	public Lock(Long clientId, String taskname){
		this.clientId = clientId;
		this.taskname = taskname;
	}
	
	public void promizeReceive(){
		 received = false;
	}
	
	public void confirmReceive(){
		received = true;
	} 
	
	public boolean received(){
		return received;
	}
	
	public void setResult(ITaskResult taskResult){
		this.taskResult = taskResult;
	}
	
	public ITaskResult getResult(){
		return taskResult;
	}
}
