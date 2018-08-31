package org.tbwork.roo.server.model;

import java.util.List;

import io.netty.channel.socket.SocketChannel;
import lombok.Data;

@Data
public class ClientInfo {

	private SocketChannel sc;
	
	private String remoteIp;
	
	private List<TaskInfo> tasks;
	
	private int promisedPingCount;
	
}
