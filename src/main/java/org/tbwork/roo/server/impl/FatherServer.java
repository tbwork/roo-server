package org.tbwork.roo.server.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbwork.anole.loader.context.Anole;
import org.tbwork.roo.common.exceptions.RemoteTimeoutException;
import org.tbwork.roo.common.message.s_2_c.CallTaskMessage;
import org.tbwork.roo.common.model.ITaskParameter;
import org.tbwork.roo.common.model.ITaskResult;
import org.tbwork.roo.server.IFatherServer;
import org.tbwork.roo.server.core.RooServerChannelInitializer;
import org.tbwork.roo.server.core.invoker.IServerCallerService;
import org.tbwork.roo.server.core.invoker.impl.ServerCallerService;
import org.tbwork.roo.server.model.ServerInvokeContext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class FatherServer implements IFatherServer{

	private final int port;
	  
	private Channel channel = null;
	
	private static final Logger logger = LoggerFactory.getLogger(FatherServer.class);
	
	volatile boolean started;
	
	private static final IServerCallerService scs = ServerCallerService.getCallerService();
	
	public FatherServer(int port){
		this.port = port;
		Integer coreSize = Anole.getIntProperty("roo.server.execute.thread.pool.size.core");
		Integer maxSize = Anole.getIntProperty("roo.server.execute.thread.pool.size.max");
		Integer keepaliveTime = Anole.getIntProperty("roo.server.execute.thread.pool.keepalive.time");
		Integer queueSize = Anole.getIntProperty("roo.server.execute.thread.pool.queue.size");
		this.pool = new ThreadPoolExecutor(coreSize, maxSize, keepaliveTime, TimeUnit.MILLISECONDS,  new ArrayBlockingQueue(queueSize, true));
	}
 
	private ExecutorService pool;
	
	public void start() {
		try{
			ServerBootstrap sb = new ServerBootstrap();
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			Integer workerCount = Anole.getIntProperty("roo.server.worker.count");
			EventLoopGroup worderGroup = new NioEventLoopGroup(workerCount);
			sb.group(bossGroup, worderGroup)
			.childHandler(new RooServerChannelInitializer()); 
			
			ChannelFuture f = sb.bind(port).sync();
			if(f.isSuccess()){
				channel = f.channel();
				logger.info("[:)] Roo server started succesfully at local address (port = {}) !", port);
	        	started = true;
			}
			
		}catch(InterruptedException e){ 
	     	logger.error("[:(] Roo server failed to start at port {}!", port);
				e.printStackTrace();
	    }
		
	}


	public ITaskResult executeTask(final Long clientId, final String taskName, final ITaskParameter param) {
		 final Channel sc = channel; 
		 Future<ITaskResult>  future = pool.submit(new Callable<ITaskResult>(){ 
			public ITaskResult call() throws Exception { 
				 CallTaskMessage ctm = new CallTaskMessage();
				 ctm.setTaskName(taskName);
				 ctm.setTaskParameter(param); 
				 
				 ServerInvokeContext invokeContext = scs.registerInvokeContext(clientId, taskName, param);
				 ITaskResult result = null; 
				 try{
					 invokeContext.lock(); 
					 sc.writeAndFlush(ctm);
					 invokeContext.wait();
					 result = invokeContext.getTaskResult();
				 }
				 catch(Exception e){
					 logger.warn("execute task failed, details: {}", e.getMessage(), e);
				 }
				 finally{
					 invokeContext.unlock();
				 }  
				 return result;
			} 
		 });
		 try{
			 return future.get(5, TimeUnit.SECONDS); 
		 }
		 catch(TimeoutException e){
			 throw new RemoteTimeoutException();
		 }
		 catch(Exception e){
			 throw new RuntimeException(e.getMessage());
		 }
	}


	public void publishTask(byte[] classData) {
		
	} 
	
	
}
