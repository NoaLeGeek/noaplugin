package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "broadcast", usage = "/broadcast <message>", permission = "noaplugin.broadcast", description = "Send a message to all online players with the author's username")
public class BroadcastCommand extends PluginCommand {
    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     *
     * @param plugin An instance of the plugin.
     */
    public BroadcastCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Syntax of /" + getName() + ":\n" +
                    ChatColor.RESET + getUsage() + "\n" +
                    ChatColor.DARK_GRAY + "<message>" + ChatColor.GRAY + " The message that will be sent to all the players in the server");
            return;
        }
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + player.getDisplayName() + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
    }
}

