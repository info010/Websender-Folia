package org.info_0.websenderfolia.events;

import java.net.Socket;

public interface EventManager {
	
	EventOutput callOnCommandEvent(Socket socket, String command);
	EventOutput callOnMessageEvent(Socket socket, String message);
	
}
