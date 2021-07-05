package fr.noalegeek68.noaplugin.commands;

import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            switch (args.length) {
                case 0:
                    player.openInventory(player.getEnderChest());
                    break;
                case 1:
                    if(!player.hasPermission(NoaPlugin.pluginPrefixPerm + command.getName())) {
                        CommandUtils.permissionError(player);
                        return false;
                    }
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target == null) {
                        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté.");
                        return false;
                    }
                    target.openWorkbench(target.getLocation(), true);
                    break;
                default:
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "/craft <pseudo>" + ChatColor.RED + ".");
                    break;
            }
            return true;
        }
        CommandUtils.senderError(sender);
        return false;
    }
}
