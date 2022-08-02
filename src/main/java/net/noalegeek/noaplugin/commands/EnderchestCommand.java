package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import net.noalegeek.noaplugin.NoaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "enderchest", usage = "/enderchest\n/enderchest <playerEnderchest>\n/enderchest <playerEnderchest> <player>", permission = "noaplugin.enderchest", description = "Open your enderchest or a player's enderchest or a player's enderchest for an other player")
public class EnderchestCommand extends PluginCommand {
    /**
     * This constructor is used to register the command and check if the command has the correct annotation
     *
     * @param plugin An instance of the plugin.
     */
    public EnderchestCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {
        switch (args.length) {
            case 0 -> player.openInventory(player.getEnderChest());
            case 1, 2 -> {
                if (Bukkit.getPlayer(args[0]) == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " isn't online or don't exist.");
                    return;
                }
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[1] + " isn't online or don't exist.");
                        return;
                    }
                    Bukkit.getPlayer(args[1]).openInventory(Bukkit.getPlayer(args[0]).getEnderChest());
                    return;
                }
                player.openInventory(Bukkit.getPlayer(args[0]).getEnderChest());
            }
            default -> player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Syntax of /" + getName() + ":\n" +
                    ChatColor.RESET + getUsage().split("\n")[0] + "\n" +
                    ChatColor.GRAY + "Open your enderchest\n" +
                    ChatColor.RESET + getUsage().split("\n")[1] + "\n" +
                    ChatColor.DARK_GRAY + "<playerEnderchest>" + ChatColor.GRAY + " The specified player's enderchest\n" +
                    ChatColor.RESET + getUsage().split("\n")[2] + "\n" +
                    ChatColor.DARK_GRAY + "<playerEnderchest>" + ChatColor.GRAY + " The specified player's enderchest\n" +
                    ChatColor.DARK_GRAY + "<player>" + ChatColor.GRAY + " The player that will open the specified player's enderchest");
        }
    }
}
