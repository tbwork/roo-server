package org.tbwork.roo.server.cm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tbwork.roo.common.message.Message;
import org.tbwork.roo.server.cm.IClientManager;
import org.tbwork.roo.server.model.ClientInfo;

public class ClientManager implements IClientManager {

	private static Map<Long, ClientInfo> clients;
			
	static{
		clients = new HashMap<Long, ClientInfo>();
	} 
	
	public void sendMessageTo(Long clientId, Message message) {
		ClientInfo ci = clients.get(clientId);
		if(ci!=null){
			ci.getSc().writeAndFlush(message);
		}
	}

	public void sweepBadConnection() {
		// TODO Auto-generated method stub
		
	}

	public List<ClientInfo> getClients() {
		// TODO Auto-generated method stub
		return null;
	}

}
