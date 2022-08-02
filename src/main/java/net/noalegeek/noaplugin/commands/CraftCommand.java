package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import net.noalegeek.noaplugin.NoaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "craft", usage = "/craft\n/craft <player>", permission = "noaplugin.craft", description = "Open the GUI of a crafting table for yourself or for a player")
public class CraftCommand extends PluginCommand {
    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     *
     * @param plugin An instance of the plugin.
     */
    public CraftCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(@NotNull Player player, String[] args) {
        switch (args.length) {
            case 0 -> player.openWorkbench(player.getLocation(), true);
            case 1 -> {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
                    return;
                }
                target.openWorkbench(target.getLocation(), true);
            }
            default -> player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Syntax of /" + getName() + ":\n" +
                    ChatColor.RESET + getUsage().split("\n")[0] + "\n" +
                    ChatColor.GRAY + "Open the GUI of a crafting table\n" +
                    ChatColor.RESET + getUsage().split("\n")[1] + "\n" +
                    ChatColor.DARK_GRAY + "<player>" + ChatColor.GRAY + " The player that will open the GUI of a crafting table");
        }
    }
}
