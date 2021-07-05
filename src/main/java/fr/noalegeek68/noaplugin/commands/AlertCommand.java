package fr.noalegeek68.noaplugin.commands;

import fr.noalegeek68.noaplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NoaPlugin] " + ChatColor.RESET + "" + ChatColor.RED + "ERREUR ! Il manque des arguments : " + ChatColor.RESET + "/" + command.getName() + " <message>" + ChatColor.RED + ".");
                return false;
            } else {
                StringBuilder alert = new StringBuilder();
                for(String part : args) alert.append(part.replace("&","§")).append(" ");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Alerte] " + ChatColor.RESET + "" + ChatColor.RED  + alert);
                return true;
            }
        }
        CommandUtils.senderError(sender);
        return false;
    }
}
