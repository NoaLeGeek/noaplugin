package fr.noalegeek68.noaplugin.commands.moderation;

import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModCommand implements CommandExecutor {
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
        switch (args.length){
            case 0:
                if(NoaPlugin.arrayModerators.contains(player.getUniqueId())){
                    NoaPlugin.arrayModerators.remove(player.getUniqueId());
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "Vous n'êtes désormais plus en mode modération.");
                } else {
                    NoaPlugin.arrayModerators.add(player.getUniqueId());
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "Vous êtes désormais en mode modération.");
                }
                return true;
            case 1:
                if(!player.hasPermission(NoaPlugin.getPermission() + command.getName() + ".others")){
                    CommandUtils.permissionError(player);
                    return false;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
                    return false;
                }
                if(target != player) {
                    if (NoaPlugin.arrayModerators.contains(target.getUniqueId())) {
                        NoaPlugin.arrayModerators.remove(target.getUniqueId());
                        target.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "Vous n'êtes désormais plus en mode modération à cause de " + player.getDisplayName() + ".");
                    } else {
                        NoaPlugin.arrayModerators.add(target.getUniqueId());
                        target.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "Vous êtes désormais en mode modération à cause de " + player.getDisplayName() + ".");
                    }
                } else {
                    if(NoaPlugin.arrayModerators.contains(player.getUniqueId())){
                        NoaPlugin.arrayModerators.remove(player.getUniqueId());
                        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "Vous n'êtes désormais plus en mode modération.");
                    } else {
                        NoaPlugin.arrayModerators.add(player.getUniqueId());
                        player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "Vous êtes désormais en mode modération.");
                    }
                }
                return true;
            default:
                player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                        "/" + command.getName() + " <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                        "<pseudo>" + ChatColor.GRAY + " correspondra au joueur.");
                return false;
        }
    }
}
