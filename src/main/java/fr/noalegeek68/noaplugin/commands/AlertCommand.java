package fr.noalegeek68.noaplugin.commands;

import org.bukkit.Bukkit;
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
                player.sendMessage("§c§l[NoaPlugin] §r§cERREUR ! Il manque des arguments : §r/" + command.getName() + " <message>§c.");
            } else {
                StringBuilder alert = new StringBuilder();
                for(String part : args) alert.append(part).append(" ");
                Bukkit.broadcastMessage("§4§l[Alerte] §r§c" + alert);
                return true;
            }
        }
        return false;
    }
}
