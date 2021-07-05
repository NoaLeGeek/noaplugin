package fr.noalegeek68.noaplugin.listener;

import fr.noalegeek68.noaplugin.enums.Items;
import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import fr.noalegeek68.noaplugin.utils.ItemStackUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Listeners implements Listener {
    private final Inventory guiKits = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Kits");
    private final Map<Player, Boolean> knightMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        knightMap.put(player, false);
        player.getInventory().clear();
        player.getInventory().setItem(4, new ItemBuilder(Material.CHEST)
                .setDisplayName(ChatColor.GREEN + "Kits")
                .build());
        player.updateInventory();
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();
        if(itemStack != null) {
            if (itemStack.isSimilar(Items.KITS.itemStack)) {
                event.setCancelled(true);
                guiKits.setItem(13, new ItemBuilder(Material.IRON_SWORD)
                        .setDisplayName(ChatColor.DARK_GREEN + "Kit Chevalier")
                        .build());
                for(int i = 0; i < 27; i++){
                    if(guiKits.getItem(i) == null ||guiKits.getItem(i).getType().isAir()){
                        guiKits.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                                .setDisplayName(ChatColor.DARK_GRAY + "Rien")
                                .build());
                    }
                }
                player.openInventory(guiKits);
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(player.getInventory().getHelmet() != null && player.getInventory().getHelmet().isSimilar(new ItemBuilder(Material.DIAMOND_HELMET)
                .build())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 1));
        } else {
            if(player.hasPotionEffect(PotionEffectType.SPEED)) player.removePotionEffect(PotionEffectType.SPEED);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (ItemStackUtils.isAirOrNull(itemStack)) return;
        if(inventory == player.getInventory() && player.getGameMode() == GameMode.SURVIVAL) {
            if(itemStack.isSimilar(Items.KITS.itemStack)){
                event.setCancelled(true);
                return;
            }
        }
        if(inventory == guiKits) {
            event.setCancelled(true);
            if(itemStack.isSimilar(new ItemBuilder(Material.IRON_SWORD)
                    .setDisplayName(ChatColor.DARK_GREEN + "Kit Chevalier")
                    .build())) {
                if (!knightMap.get(player)) {
                    player.getInventory().clear();
                    player.getInventory().setItem(EquipmentSlot.HEAD, new ItemStack(Material.IRON_HELMET));
                    player.getInventory().setItem(EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE));
                    player.getInventory().setItem(EquipmentSlot.LEGS, new ItemStack(Material.IRON_LEGGINGS));
                    player.getInventory().setItem(EquipmentSlot.FEET, new ItemStack(Material.IRON_BOOTS));
                    player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
                    player.getInventory().setItem(1, new ItemStack(Material.BOW));
                    player.getInventory().setItem(2, new ItemStack(Material.ARROW, 64));
                    player.getInventory().setItem(8, new ItemBuilder(Material.CHEST)
                            .setDisplayName(ChatColor.GREEN + "Kits")
                            .build());
                    knightMap.put(player, true);
                } else {
                    player.getInventory().clear();
                    player.getInventory().setItem(4, new ItemBuilder(Material.CHEST)
                            .setDisplayName(ChatColor.GREEN + "Kits")
                            .build());
                    knightMap.put(player, false);
                }
                player.closeInventory();
            }
        }
    }
}
