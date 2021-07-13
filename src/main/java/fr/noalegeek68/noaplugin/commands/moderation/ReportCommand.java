package fr.noalegeek68.noaplugin.commands.moderation;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.utils.CommandUtils;
import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class ReportCommand implements CommandExecutor {
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
        if(args.length != 1){
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Il manque des arguments : " + ChatColor.RESET + "\n" +
                    "/" + command.getName() + " <pseudo>" + ChatColor.DARK_GRAY + "\n" +
                    "<pseudo>" + ChatColor.GRAY + " correspondra au joueur.");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + args[0] + " n'est pas connecté ou n'existe pas.");
            return false;
        }
        SmartInventory reportInventory = SmartInventory.builder()
                .size(3, 9)
                .id("reportgui")
                .title("Sélectionnez une raison")
                .type(InventoryType.CHEST)
                .provider(new InventoryProvider() {
                    private int knockbackLevel = 1;
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        contents.set(0, 0, ClickableItem.of(new ItemBuilder(Material.STICK)
                                        .setDisplayName(ChatColor.DARK_GRAY + "AntiKnockback")
                                        .setLore(ChatColor.GRAY + "Le joueur ne reçoit pas d'effets de recul.")
                                        .addEnchant(Enchantment.KNOCKBACK, knockbackLevel)
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(1));
                                }));
                    }
                    @Override
                    public void update(Player player, InventoryContents contents) {
                        int state = contents.property("state", 0);
                        contents.setProperty("state", state + 1);
                        if(state % 20 != 0) return;
                        contents.set(0, 0, ClickableItem.of(new ItemBuilder(Material.STICK)
                                        .setDisplayName(ChatColor.DARK_GRAY + "AntiKnockback")
                                        .setLore(ChatColor.GRAY + "Le joueur ne reçoit pas d'effets de recul.")
                                        .addEnchant(Enchantment.KNOCKBACK, knockbackLevel)
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(1));
                                }));
                        if(knockbackLevel == 10){
                            knockbackLevel = 1;
                            return;
                        }
                        knockbackLevel++;
                    }
                }).manager(NoaPlugin.getManager()).build();
        reportInventory.open(player);
        return true;
    }
    private static void sendReport(Player whoReported, Player beenReported, String reason){

    }
}
