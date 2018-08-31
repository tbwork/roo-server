package org.tbwork.roo.server;

import org.tbwork.roo.common.model.ITaskParameter;
import org.tbwork.roo.common.model.ITaskResult;

public interface IFatherServer {

	/**
	 * Start the server.
	 */
	public void start();
	
	/**
	 * Execute the task.
	 */
	public ITaskResult executeTask(Long clientId, String taskName, ITaskParameter param);
	
	/**
	 * Publish new task classes. 
	 */
	public void publishTask(byte [] classData);
	
}
