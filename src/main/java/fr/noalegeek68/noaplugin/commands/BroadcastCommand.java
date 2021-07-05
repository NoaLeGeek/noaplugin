package fr.noalegeek68.noaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NoaPlugin] " + ChatColor.RESET + "" + ChatColor.RED + "ERREUR ! Il manque des arguments : " + ChatColor.RESET + "/" + command.getName() + " <message>" + ChatColor.RED + ".");
            } else {
                StringBuilder broadcast = new StringBuilder();
                for(String part : args) broadcast.append(part.replace("&","§")).append(" ");
                Bukkit.broadcastMessage(ChatColor.BOLD + "[" + player.getDisplayName() + "] " + ChatColor.RESET + broadcast);
                return true;
            }
        }
        return false;
    }
}
