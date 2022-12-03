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
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

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

    //Create a ItemStack array with 54 elements because invsee's inventory is a double chest inventory
    public static ItemStack[] guiContents = new ItemStack[54];
    //Create a ItemStack array with 40 elements because Inventory#getContents()
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
                    //Update guiContents if targetContents is updated
                    if(targetContents != Arrays.stream(target.getInventory().getContents())
                            .map(itemStack -> itemStack == null ? new ItemStack(Material.AIR) : itemStack)
                            .toArray(ItemStack[]::new)){

                    }
                    //Update targetContents if guiContents is updated
                    else if(guiContents != Arrays.stream(event.getInventory().getContents())
                            .map(itemStack -> itemStack == null ? new ItemStack(Material.AIR) : itemStack)
                            .toArray(ItemStack[]::new)){

                    }
                    //Register the new contents of guiContents and targetContents
                    targetContents = Arrays.stream(target.getInventory().getContents()).map(itemStack -> itemStack == null ? new ItemStack(Material.AIR) : itemStack).toArray(ItemStack[]::new);
                    IntStream.range(0, 54).forEach(slot -> {
                        if(ItemStackUtils.hasPersistentDataContainer(event.getInventory().getItem(slot), SunderiaUtils.key("cancelled"), PersistentDataType.BYTE)) return;
                        guiContents[switch (slot){
                            case 36 -> 39;
                            case 37 -> 38;
                            case 38 -> 37;
                            case 39 -> 36;
                            case 40 -> 35;
                            default -> slot > 45 ? slot - 45 : slot;
                        }] = event.getInventory().getItem(slot) == null ? new ItemStack(Material.AIR) : event.getInventory().getItem(slot);
                    });
                }, 0, 0)
                .build());
    }
}

