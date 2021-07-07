package fr.noalegeek68.noaplugin.commands;

import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            CommandUtils.senderError(sender);
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission(NoaPlugin.getPermission() + command.getName() + ".use")) {
            CommandUtils.permissionError(player);
            return false;
        }
        switch (args.length) {
            case 0:
                player.openInventory(player.getInventory());
                return true;
            case 1:
                Player playerInventory = Bukkit.getPlayer(args[0]);
                if(playerInventory == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                player.openInventory(playerInventory.getInventory());
                return true;
            case 2:
                playerInventory = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                if(playerInventory == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                if(target == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[1] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                playerInventory.openInventory(target.getInventory());
                return true;
            default:
                player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                        "/" + command.getName() + " <inventaire>" + ChatColor.DARK_GRAY + "\n" +
                        "<inventaire>" + ChatColor.GRAY + " correspondra à l'inventaire du joueur. \n" +
                        ChatColor.RESET + "/" + command.getName() + " <inventaire> <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                        "<pseudo>" + ChatColor.GRAY + " correspondra à celui qui va voir l'inventaire.");
                return false;
        }
    }
}

