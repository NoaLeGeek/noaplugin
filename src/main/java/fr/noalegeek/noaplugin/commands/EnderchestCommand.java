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

public class EnderchestCommand implements CommandExecutor {
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
                player.openInventory(player.getEnderChest());
                return true;
            case 1:
                Player enderchest = Bukkit.getPlayer(args[0]);
                if (enderchest == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                player.openInventory(enderchest.getEnderChest());
                return true;
            case 2:
                enderchest = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                if (enderchest == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                if (target == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[1] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                enderchest.openInventory(target.getEnderChest());
                return true;
            default:
                player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                        "/" + command.getName() + " <enderchest>" + ChatColor.DARK_GRAY + "\n" +
                        "<enderchest>" + ChatColor.GRAY + " correspondra à l'enderchest du joueur." + ChatColor.RED + "\n" +
                        ChatColor.RESET + "/" + command.getName() + " <enderchest> <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                        "<pseudo>" + ChatColor.GRAY + " correspondra à celui qui va voir l'enderchest.");
                return false;
        }
    }
}
