package fr.noalegeek.noaplugin.commands.moderation;

import fr.noalegeek.noaplugin.NoaPlugin;
import fr.noalegeek.noaplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReportCommand implements CommandExecutor {
    private String reason;
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
        if (args.length != 1) {
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                    "/" + command.getName() + " <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                    "<pseudo>" + ChatColor.GRAY + " correspondra au joueur.");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
            return false;
        }
        if(target == player){
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Vous ne pouvez pas report vous-même !");
        }
        return true;
    }

    public static void sendReport(Player whoReported, Player beenReported, String reason, Command command) {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(NoaPlugin.getPermission() + command.getName() + ".see")).forEach(player -> player.sendMessage(String.format("%s[%sReport%s] %s%s%s a reporté %s%s%s pour la raison \"%s%s%s\".", ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GRAY, ChatColor.DARK_AQUA, whoReported.getDisplayName(), ChatColor.AQUA, ChatColor.DARK_AQUA, beenReported.getDisplayName(), ChatColor.AQUA, ChatColor.RED, reason, ChatColor.AQUA)));
    }
}
