package fr.noalegeek.noaplugin.commands;

import fr.noalegeek.noaplugin.NoaPlugin;
import fr.noalegeek.noaplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AlertCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            CommandUtils.senderError(sender);
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission(NoaPlugin.getPermission() + command.getName() + ".use")) {
            CommandUtils.permissionError(player);
            return false;
        }
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NoaPlugin] " + ChatColor.RESET + "" + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                    "/" + command.getName() + " <message>");
            return false;
        } else {
            StringBuilder alert = new StringBuilder();
            for (String part : args) alert.append(part.replace("&", "§")).append(" ");
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Alerte] " + ChatColor.RESET + "" + ChatColor.RED + alert);
            return true;
        }
    }
}
