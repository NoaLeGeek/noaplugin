package fr.noalegeek68.noaplugin.listeners;

import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.commands.moderation.ReportCommand;
import fr.noalegeek68.noaplugin.objects.GUI;
import fr.noalegeek68.noaplugin.objects.ItemsGUI;
import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import fr.noalegeek68.noaplugin.utils.ItemStackUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Listeners implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if(player.getInventory().getHelmet() != null && player.getInventory().getHelmet().isSimilar(new ItemBuilder(Material.DIAMOND_HELMET)
                .build())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 1));
        } else {
            if(player.hasPotionEffect(PotionEffectType.SPEED)) player.removePotionEffect(PotionEffectType.SPEED);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(GUI.arrayKnight.contains(player)) GUI.arrayKnight.add(player);
        player.getInventory().clear();
        player.getInventory().setItem(4, ItemsGUI.KITS.itemStack);
        player.updateInventory();
        if(NoaPlugin.arrayModerators.contains(player.getUniqueId())){
            NoaPlugin.arrayModerators.remove(event.getPlayer().getUniqueId());
            player.teleport(new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX(), player.getWorld().getSpawnLocation().getY(), player.getWorld().getSpawnLocation().getZ(), player.getWorld().getSpawnLocation().getYaw(), player.getWorld().getSpawnLocation().getPitch()));
            // Unvanish the person
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if(itemStack != null) {
            if (itemStack.isSimilar(ItemsGUI.KITS.itemStack)) {
                event.setCancelled(true);
                GUI.KITS.smartInventory.open(player);
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
            }
        }
    }
}
