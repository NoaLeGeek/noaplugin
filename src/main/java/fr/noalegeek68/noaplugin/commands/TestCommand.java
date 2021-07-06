package fr.noalegeek68.noaplugin.commands;

import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
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
        player.sendMessage(ChatColor.GREEN + "Dites bienvenue à " + player.getDisplayName() + " sur le serveur " + player.getServer().getName() + ".");
        return true;
    }
}
