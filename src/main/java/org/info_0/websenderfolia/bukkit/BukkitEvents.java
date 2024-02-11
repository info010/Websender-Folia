package org.info_0.websenderfolia.bukkit;

import java.net.Socket;

import org.bukkit.Bukkit;
import org.info_0.websenderfolia.events.BukkitSocketOnCommandEvent;
import org.info_0.websenderfolia.events.BukkitSocketOnMessageEvent;
import org.info_0.websenderfolia.events.EventManager;
import org.info_0.websenderfolia.events.EventOutput;

public class BukkitEvents implements EventManager {

	@Override
	public EventOutput callOnCommandEvent(Socket socket, String command){
		BukkitSocketOnCommandEvent event = new BukkitSocketOnCommandEvent(socket, command);
		Bukkit.getPluginManager().callEvent(event);
		
		return new EventOutput(event.getCommand(), event.isCancelled());
	}
	
	@Override
	public EventOutput callOnMessageEvent(Socket socket, String message){
		BukkitSocketOnMessageEvent event = new BukkitSocketOnMessageEvent(socket, message);
		Bukkit.getPluginManager().callEvent(event);
		
		return new EventOutput(event.getMessage(), event.isCancelled());
	}
	
}
