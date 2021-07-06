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
        if(sender instanceof Player) {
            Player player = (Player) sender;
            switch (args.length) {
                case 0:
                    player.openInventory(player.getInventory());
                    return true;
                case 1:
                    if(!player.hasPermission(NoaPlugin.pluginPrefixPerm + command.getName())) {
                        CommandUtils.permissionError(player);
                        return false;
                    }
                    Player inventory = Bukkit.getPlayer(args[0]);
                    if(inventory == null) {
                        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté.");
                        return false;
                    }
                    player.openInventory(inventory.getInventory());
                    return true;
                case 2:
                    if(!player.hasPermission(NoaPlugin.pluginPrefixPerm + command.getName())) {
                        CommandUtils.permissionError(player);
                        return false;
                    }
                    inventory = Bukkit.getPlayer(args[0]);
                    Player target = Bukkit.getPlayer(args[1]);
                    if(inventory == null) {
                        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté.");
                        return false;
                    }
                    if(target == null) {
                        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[1] + " n'est pas connecté.");
                        return false;
                    }
                    inventory.openInventory(target.getInventory());
                    return true;
                default:
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                            "/" + command.getName() + " <inventaire>" + ChatColor.DARK_GRAY + "\n" +
                            "<inventaire>" + ChatColor.GRAY + " correspondra à l'inventaire du joueur." + ChatColor.RED + "\n" +
                            ChatColor.RESET + "/" + command.getName() + " <inventaire> <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                            "<pseudo>" + ChatColor.GRAY + " correspondra à celui qui va voir l'inventaire.");
                    return false;
            }
        }
        CommandUtils.senderError(sender);
        return false;
    }
}
