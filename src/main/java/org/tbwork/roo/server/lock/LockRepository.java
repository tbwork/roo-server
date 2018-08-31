package org.tbwork.roo.server.lock;

import java.util.Map; 
import java.util.concurrent.ConcurrentHashMap;

public class LockRepository {

	private static Map<String,Lock> lockMap = new ConcurrentHashMap<String, Lock>();
	public static Lock getLock(Long clientId, String taskname){
		String key = String.format("%d_%s", clientId, taskname);
		if(!lockMap.containsKey(key)){
			synchronized(LockRepository.class){
				if(!lockMap.containsKey(key)){
					Lock lock = new Lock(clientId, taskname);
					lockMap.put(key, lock);
				}
			}
		}
		return lockMap.get(key);
	}
	
	public static void removeLock(Long clientId, String taskname){
		String key = String.format("%d_%s", clientId, taskname);
		lockMap.remove(key);
	}
	
}
