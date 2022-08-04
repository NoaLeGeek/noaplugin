package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@CommandInfo(name = "alert", usage = "/alert <message>", permission = "noaplugin.alert", requiresPlayer = false, description = "Send an important alert to all the online players and send a title that say to look in the chat")
public class AlertCommand extends PluginCommand {
    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     *
     * @param plugin An instance of the plugin.
     */
    public AlertCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Syntax of /" + getName() + ":\n" +
                    ChatColor.RESET + getUsage() + "\n" +
                    ChatColor.DARK_GRAY + "<message>" + ChatColor.GRAY + " The message that will be sent to all the players in the server");
            return;
        }
        sender.getServer()
                .getOnlinePlayers()
                .forEach(onlinePlayer -> onlinePlayer.sendTitle(ChatColor.BOLD + "" + ChatColor.DARK_RED + "ALERT", ChatColor.RED + "Look in the chat", 0, 75, 0));
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "ALERT" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
    }
}
