package fr.noalegeek.noaplugin.commands;

import fr.noalegeek.noaplugin.NoaPlugin;
import fr.noalegeek.noaplugin.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
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
        player.teleport(new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX(), player.getWorld().getSpawnLocation().getY(), player.getWorld().getSpawnLocation().getZ(), player.getWorld().getSpawnLocation().getYaw(), player.getWorld().getSpawnLocation().getPitch()));
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[NoaPlugin] " + ChatColor.RESET + "" + ChatColor.GREEN + "Vous venez d'être téléporté au spawn du monde.");
        /*public InventoryBuilder addItemsRow(int row, ItemStack... itemStacks){
        for(int i = row; i < row * 9 + 1; i++) {
            this.itemStacks.add(i - 1, itemStacks[i - 1]);
        }
        return this;
    }*/
        return true;
    }
}
