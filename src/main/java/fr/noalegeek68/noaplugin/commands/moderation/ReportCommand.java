package fr.noalegeek68.noaplugin.commands.moderation;

import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.enums.GUI;
import fr.noalegeek68.noaplugin.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReportCommand implements CommandExecutor {
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
        if(args.length != 1){
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                    "/" + command.getName() + " <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                    "<pseudo>" + ChatColor.GRAY + " correspondra au joueur.");
            return false;
        }

        return true;
    }
}
