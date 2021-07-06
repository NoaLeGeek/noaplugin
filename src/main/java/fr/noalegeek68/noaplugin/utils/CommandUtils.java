package fr.noalegeek68.noaplugin.utils;

import fr.noalegeek68.noaplugin.NoaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandUtils {

    public static void senderError(@NotNull CommandSender sender) {
        sender.sendMessage(NoaPlugin.pluginPrefix + "Seuls les joueurs peuvent exécuter cette commande.");
    }

    public static void permissionError(@NotNull Player player) {
        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Vous n'avez pas la permission d'exécuter cette commande.");
    }
}
