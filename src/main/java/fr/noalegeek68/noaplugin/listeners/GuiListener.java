package fr.noalegeek68.noaplugin.listeners;

import fr.noalegeek68.noaplugin.enums.GUI;
import fr.noalegeek68.noaplugin.enums.ItemsGUI;
import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import fr.noalegeek68.noaplugin.utils.ItemStackUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GuiListener implements Listener {
   
    private final Map<Player, Boolean> knightMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        knightMap.put(player, false);
        player.getInventory().clear();
        player.getInventory().setItem(4, ItemsGUI.KITS.itemStack);
        player.updateInventory();
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if(itemStack != null) {
            if (itemStack.isSimilar(ItemsGUI.KITS.itemStack)) {
                event.setCancelled(true);
                GUI.KITS.inventory.setItem(13, new ItemBuilder(Material.IRON_SWORD)
                        .setDisplayName(ChatColor.DARK_GREEN + "Kit Chevalier")
                        .build());
                for(int slot = 0; slot < 27; slot++){
                    if(GUI.KITS.inventory.getItem(slot) == null || GUI.KITS.inventory.getItem(slot).getType().isAir()){
                        GUI.KITS.inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                                .setDisplayName(ChatColor.DARK_GRAY + "Rien")
                                .build());
                    }
                }
                player.openInventory(GUI.KITS.inventory);
            }
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (ItemStackUtils.isAirOrNull(itemStack)) return;
        if(inventory == player.getInventory() && player.getGameMode().equals(GameMode.SURVIVAL)) {
            if(itemStack.isSimilar(ItemsGUI.KITS.itemStack)){
                event.setCancelled(true);
                return;
            }
        }
        if(inventory == GUI.KITS.inventory) {
            event.setCancelled(true);
            if(itemStack.isSimilar(ItemsGUI.KITS.itemStack)) {
                if (!knightMap.get(player)) {
                    player.getInventory().clear();
                    player.getInventory().setItem(EquipmentSlot.HEAD, new ItemStack(Material.IRON_HELMET));
                    player.getInventory().setItem(EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE));
                    player.getInventory().setItem(EquipmentSlot.LEGS, new ItemStack(Material.IRON_LEGGINGS));
                    player.getInventory().setItem(EquipmentSlot.FEET, new ItemStack(Material.IRON_BOOTS));
                    player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
                    player.getInventory().setItem(1, new ItemStack(Material.BOW));
                    player.getInventory().setItem(2, new ItemStack(Material.ARROW, 64));
                    player.getInventory().setItem(8, ItemsGUI.KITS.itemStack);
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
