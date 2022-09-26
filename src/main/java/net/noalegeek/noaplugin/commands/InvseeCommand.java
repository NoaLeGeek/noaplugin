package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.SunderiaUtils;
import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import fr.sunderia.sunderiautils.utils.DepInventoryBuilder;
import fr.sunderia.sunderiautils.utils.ItemBuilder;
import fr.sunderia.sunderiautils.utils.ItemStackUtils;
import net.noalegeek.noaplugin.NoaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@CommandInfo(name = "invsee", usage = "/invsee\n/invsee <playerInventory>\n/invsee <playerInventory> <player>", permission = "noaplugin.invsee", description = "Open your inventory or a player's inventory or a player's inventory for an other player")
public class InvseeCommand extends PluginCommand {
    /**
     * This constructor is used to register the command and check if the command has the correct annotation.
     *
     * @param plugin An instance of the plugin.
     */
    public InvseeCommand(JavaPlugin plugin) {
        super(plugin);
    }

    public static ItemStack[] guiContents = new ItemStack[40];
    public static ItemStack[] targetContents = new ItemStack[40];

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {
        /*
        /inv -> open(player)
                /inv <inv> -> open(player, args[0])
                /inv <inv> <opener> -> open(args[1], args[0])
                */
        if(args.length > 2){
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Syntax of /" + getName() + ":\n" +
                    ChatColor.RESET + getUsage().split("\n")[0] + "\n" +
                    ChatColor.GRAY + "Open your inventory\n" +
                    ChatColor.RESET + getUsage().split("\n")[1] + "\n" +
                    ChatColor.DARK_GRAY + "<playerInventory>" + ChatColor.GRAY + " The specified player's inventory\n" +
                    ChatColor.RESET + getUsage().split("\n")[2] + "\n" +
                    ChatColor.DARK_GRAY + "<playerInventory>" + ChatColor.GRAY + " The specified player's inventory\n" +
                    ChatColor.DARK_GRAY + "<player>" + ChatColor.GRAY + " The player that will open the specified player's inventory");
            return;
        }
        if(args.length > 0){
            if (Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " isn't online or don't exist.");
                return;
            }
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " isn't online or don't exist.");
                    return;
                }
            }
        }
        Player target = args[0] == null ? player : Bukkit.getPlayer(args[0]);
        (args[1] == null ? player : Bukkit.getPlayer(args[1])).openInventory(new DepInventoryBuilder((player == target ? "Your" : target.getDisplayName() + (target.getDisplayName().endsWith("s") ? "'" : "'s")) + " inventory", new DepInventoryBuilder.Shape("""
                BBBBPBBBB
                AAAAAAAAA
                AAAAAAAAA
                AAAAAAAAA
                BAABABAAB
                AAAAAAAAA
                """, Map.of('B', new ItemBuilder(Material.BLACK_STAINED_GLASS).setDisplayName(" ").addPersistentDataContainer(SunderiaUtils.key("cancelled"), PersistentDataType.BYTE, (byte) 1).build(),
                'A', new ItemStack(Material.AIR),
                'P', new ItemBuilder(Material.PLAYER_HEAD).setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_GRAY + (player == target ? "You" : target.getDisplayName())).setHead(target).build())))
                .onClick(event -> {
                    if(ItemStackUtils.hasPersistentDataContainer(event.getCurrentItem(), SunderiaUtils.key("cancelled"), PersistentDataType.BYTE))
                        return;
                })
                .onUpdate(event -> {
                    //

                }, 0, 0)
                .build());
    }
}

