package org.tbwork.roo.server.cm;

import java.util.List;

import org.tbwork.roo.common.message.Message;
import org.tbwork.roo.server.model.ClientInfo;

public interface IClientManager {

	public void sendMessageTo(Long clientId, Message message);
	
	public void sweepBadConnection();
	
	public List<ClientInfo> getClients();
	
	
}
