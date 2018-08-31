package org.tbwork.roo.server;

import org.tbwork.anole.loader.annotion.AnoleConfigLocation;
import org.tbwork.anole.loader.context.Anole;
import org.tbwork.anole.loader.context.AnoleApp;
import org.tbwork.anole.loader.util.AnoleLogger.LogLevel;
import org.tbwork.roo.server.impl.FatherServer;

/**
 * Hello world!
 */
@AnoleConfigLocation
public class AppStarter 
{
    public static void main( String[] args )
    { 
    	AnoleApp.start(LogLevel.DEBUG);
    	IFatherServer fs = new FatherServer(22222);
    	fs.start();
    	
    	System.out.println(Anole.getProperty("a"));
    }
}
