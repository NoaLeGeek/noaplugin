package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import net.noalegeek.noaplugin.NoaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "report", usage = "/report <player>", permission = "noaplugin.report", description = "Report a player")
public class ReportCommand extends PluginCommand {
    private String reason;

    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     *
     * @param plugin An instance of the plugin.
     */
    public ReportCommand(JavaPlugin plugin) {
        super(plugin);
    }

    public static void sendReport(Player whoReported, Player beenReported, String reason, Command command) {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(NoaPlugin.getPermission() + command.getName() + ".see")).forEach(player -> player.sendMessage(String.format("%s[%sReport%s] %s%s%s a reporté %s%s%s pour la raison \"%s%s%s\".", ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GRAY, ChatColor.DARK_AQUA, whoReported.getDisplayName(), ChatColor.AQUA, ChatColor.DARK_AQUA, beenReported.getDisplayName(), ChatColor.AQUA, ChatColor.RED, reason, ChatColor.AQUA)));
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {
        if (args.length != 1) {
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Syntax of /" + getName() + ":\n" +
                    ChatColor.RESET + getUsage() + "\n" +
                    ChatColor.DARK_GRAY + "<player>" + ChatColor.GRAY + " The player that will be reported");
            return;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " isn't online or don't exist.");
            return;
        }
        if (Bukkit.getPlayer(args[0]) == player) {
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "You can't report yourself.");
        }
    }
}
