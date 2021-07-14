package fr.noalegeek68.noaplugin.commands.moderation;

import com.google.gson.JsonArray;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.listeners.Listeners;
import fr.noalegeek68.noaplugin.utils.CommandUtils;
import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import fr.noalegeek68.noaplugin.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                .size(6, 9)
                .id("reportgui")
                .title("Sélectionnez une raison")
                .type(InventoryType.CHEST)
                .provider(new InventoryProvider() {
                    private int sharpnessLevel = 1;
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        contents.fill(ClickableItem.empty(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                                .setDisplayName(ChatColor.DARK_GRAY + "Rien")
                                .build()));
                        contents.set(1, 2, ClickableItem.of(new ItemBuilder(Material.OAK_SIGN)
                                        .setDisplayName(ChatColor.DARK_GRAY + "Abus de langage/Arnaque")
                                        .addLoreLine(ChatColor.GRAY + "Le joueur a fait un abus de langage dans le tchat")
                                        .addLoreLine(ChatColor.GRAY + "ou incite à rejoindre une arnaque.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                }));                        contents.set(1, 3, ClickableItem.of(new ItemBuilder(Material.NETHERITE_AXE)
                                        .setDisplayName(ChatColor.DARK_GRAY + "Cheat")
                                        .addEnchant(Enchantment.DAMAGE_ALL, sharpnessLevel)
                                        .addLoreLine(ChatColor.GRAY + "Le joueur a utilisé n'importe quel type de cheat.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                }));                        contents.set(1, 4, ClickableItem.of(new ItemBuilder(Material.NAME_TAG)
                                        .setDisplayName(ChatColor.DARK_GRAY + "Mauvais pseudo")
                                        .addLoreLine(ChatColor.GRAY + "Le joueur possède un mauvais pseudo.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                }));                        contents.set(1, 5, ClickableItem.of(new ItemBuilder(ItemStackUtils.randomBanner())
                                        .setDisplayName(ChatColor.DARK_GRAY + "Mauvais skin/cape")
                                        .addLoreLine(ChatColor.GRAY + "Le joueur possède un mauvais skin ou une mauvaise cape.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                }));                        contents.set(1, 6, ClickableItem.of(new ItemBuilder(ItemStackUtils.randomSkull())
                                        .setDisplayName(ChatColor.DARK_GRAY + "Equipe non-autorisée")
                                        .addLoreLine(ChatColor.GRAY + "Le joueur fait équipe avec une personne alors qu'il ne devrait pas.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                }));                        contents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                                        .setDisplayName(ChatColor.DARK_GRAY + "Autre raison")
                                        .addLoreLine(ChatColor.GRAY + "Le joueur a fait quelque chose de non-listé dans ces raisons.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                    Listeners.arrayReportOther.add((Player) e.getWhoClicked());
                                }));
                    }
                    @Override
                    public void update(Player player, InventoryContents contents) {
                        int state = contents.property("state", 0);
                        contents.setProperty("state", state + 1);
                        if(state % 20 != 0) return;
                        contents.set(1, 3, ClickableItem.of(new ItemBuilder(Material.NETHERITE_AXE)
                                        .setDisplayName(ChatColor.DARK_GRAY + "Cheat")
                                        .addEnchant(Enchantment.DAMAGE_ALL, sharpnessLevel)
                                        .setLore(ChatColor.GRAY + "Le joueur a fait un abus de langage dans le tchat ou incite à rejoindre une arnaque.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                    }));
                        contents.set(1, 5, ClickableItem.of(new ItemBuilder(ItemStackUtils.randomBanner())
                                        .setDisplayName(ChatColor.DARK_GRAY + "Mauvais skin/cape")
                                        .addLoreLine(ChatColor.GRAY + "Le joueur possède un mauvais skin ou une mauvaise cape.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                }));
                        contents.set(1, 6, ClickableItem.of(new ItemBuilder(ItemStackUtils.randomSkull())
                                        .setDisplayName(ChatColor.DARK_GRAY + "Equipe non-autorisée")
                                        .addLoreLine(ChatColor.GRAY + "Le joueur fait équipe avec une personne alors qu'il ne devrait pas.")
                                        .build(),
                                e -> {
                                    sendReport(player, target, e.getCurrentItem().getItemMeta().getDisplayName().substring(2), command);
                                    e.setCancelled(true);
                                }));
                        if(sharpnessLevel == 10) sharpnessLevel = 0;
                        sharpnessLevel++;
                    }
                }).manager(NoaPlugin.getManager()).build();
        reportInventory.open(player);
        return true;
    }
    private static void sendReport(Player whoReported, Player beenReported, String reason, Command command){
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(NoaPlugin.getPermission() + command.getName() + ".see")).forEach(player -> player.sendMessage(String.format("%s[%sReport%s] %s%s%s a reporté %s%s%s pour la raison \"%s%s%s\".", ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GRAY, ChatColor.DARK_AQUA, whoReported.getDisplayName(), ChatColor.AQUA, ChatColor.DARK_AQUA, beenReported.getDisplayName(), ChatColor.AQUA, ChatColor.RED, reason, ChatColor.AQUA)));
    }
}
