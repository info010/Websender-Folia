package org.info_0.websenderfolia.shared;

import org.bukkit.Bukkit;
import org.info_0.websenderfolia.bukkit.BukkitEvents;
import org.info_0.websenderfolia.bukkit.BukkitMain;
import org.info_0.websenderfolia.events.EventManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

@SuppressWarnings("deprecation")
public class UtilSocket {
	
	private static EventManager manager;
	public static EventManager getManager(){
		if(manager == null){
			if(SocketConfig.isBukkit()){
				manager = new BukkitEvents();
			}
		}
		
		return manager;
	}
	
    public static String hash(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(input.getBytes());
            
            String result = new BigInteger(1, md.digest()).toString(16);
            if(result.length() % 2 != 0){
                result = "0" + result;
            }
            
            return result;
        }catch(Exception ex){
            return "";
        }
    }
    
    public static String readString(DataInputStream in, boolean base64) throws IOException {
        int stringSize = in.readInt();
        StringBuilder buffer = new StringBuilder();
        
        for(int i = 0; i < stringSize; i++){
            buffer.append(in.readChar());
        }
        
        return base64 ? DecodeBASE64(buffer.toString()) : buffer.toString();
    }
	
	public static String DecodeBASE64(String text) throws UnsupportedEncodingException {		
		byte[] bytes = Base64.getDecoder().decode(text);
		return new String(bytes, "UTF-8");
	}
    
    public static void writeString(DataOutputStream out, String string) throws IOException {
        out.writeInt(string.length());
        out.writeChars(string);
    }
    
    public static void sendCommand(String command, DataOutputStream out) throws IOException {
        boolean success = false;        
        try{
            if(SocketConfig.isBukkit()){
                success = BukkitMain.sendCommand(command);
            }
        }catch(Exception ex){
            createLog(String.valueOf(SocketConfig.prefix) + "ERROR: " + ex.getMessage());
            success = false;
        }
        
        out.writeInt(success ? 1 : 0);
        out.flush();
    }
    
	public static void createLog(String data){
        if(SocketConfig.consoleInfo.equals("true")){
            if(SocketConfig.isBukkit()){
            	Bukkit.getConsoleSender().sendMessage(String.valueOf(SocketConfig.prefix) + data);
            }
        }
    }
    
    public static void sendPlayerMsg(String playerName, String data){
        if(SocketConfig.isBukkit()){
            Bukkit.getPlayer(playerName).sendMessage(String.valueOf(SocketConfig.prefix) + data);
        }
    }
    
}
