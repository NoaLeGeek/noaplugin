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

public class CraftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
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
                player.openWorkbench(player.getLocation(), true);
                return true;
            case 1:
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                target.openWorkbench(target.getLocation(), true);
                return true;
            default:
                player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                        "/craft <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                        "<pseudo>" + ChatColor.GRAY + " correspondra à celui qui va voir l'interface de l'établi.");
                return false;
        }
    }
}
