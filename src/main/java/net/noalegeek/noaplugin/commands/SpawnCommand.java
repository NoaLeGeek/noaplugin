package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import net.noalegeek.noaplugin.NoaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "spawn", usage = "/spawn\n/spawn <player>", permission = "noaplugin.spawn", description = "Teleport yourself to your spawn location")
public class SpawnCommand extends PluginCommand {
    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     *
     * @param plugin An instance of the plugin.
     */
    public SpawnCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(@NotNull Player player, String[] args) {
        if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " isn't online or don't exist.");
                return;
            }
            Bukkit.getPlayer(args[0]).teleport(new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX(), player.getWorld().getSpawnLocation().getY(), player.getWorld().getSpawnLocation().getZ(), player.getWorld().getSpawnLocation().getYaw(), player.getWorld().getSpawnLocation().getPitch()), PlayerTeleportEvent.TeleportCause.COMMAND);
            Bukkit.getPlayer(args[0]).sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "You've been teleported to the world spawn.");
        }
        player.teleport(new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX(), player.getWorld().getSpawnLocation().getY(), player.getWorld().getSpawnLocation().getZ(), player.getWorld().getSpawnLocation().getYaw(), player.getWorld().getSpawnLocation().getPitch()), PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "You've been teleported to the world spawn.");
    }
}
