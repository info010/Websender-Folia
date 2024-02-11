package org.info_0.websenderfolia.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.info_0.websenderfolia.shared.SocketConfig;
import org.info_0.websenderfolia.shared.UtilSocket;


public class BukkitCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args){
        if(!(sender instanceof Player)){
            BukkitMain.getPlugin().configLoad();
            sender.sendMessage(String.valueOf(SocketConfig.prefix) + SocketConfig.pluginReloaded);
            return true;
        }
        if(sender.hasPermission(SocketConfig.commandPermission) || sender.isOp()){
            BukkitMain.getPlugin().configLoad();
            UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.pluginReloaded);
            return true;
        }
        
        UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.nothavePerm);
        return false;
    }
    
}
