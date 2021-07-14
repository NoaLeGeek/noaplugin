package fr.noalegeek68.noaplugin.objects;

import com.google.common.cache.AbstractCache;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.commands.moderation.ReportCommand;
import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum GUI {

    KITS(SmartInventory.builder()
            .size(3, 9)
            .title(ChatColor.DARK_GREEN + "Kits")
            .provider(new InventoryProvider() {
                @Override
                public void init(Player player, InventoryContents contents) {
                    contents.fill(ClickableItem.empty((new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                            .setDisplayName(ChatColor.DARK_GRAY + "Rien")
                            .build())));
                    contents.set(1, 4, ClickableItem.of(ItemsGUI.KNIGHTKIT.itemStack,
                            e -> {
                                e.setCancelled(true);
                                if (!arrayKnight.contains(player)) {
                                    player.getInventory().clear();
                                    player.getInventory().setItem(EquipmentSlot.HEAD, new ItemStack(Material.IRON_HELMET));
                                    player.getInventory().setItem(EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE));
                                    player.getInventory().setItem(EquipmentSlot.LEGS, new ItemStack(Material.IRON_LEGGINGS));
                                    player.getInventory().setItem(EquipmentSlot.FEET, new ItemStack(Material.IRON_BOOTS));
                                    player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
                                    player.getInventory().setItem(1, new ItemStack(Material.BOW));
                                    player.getInventory().setItem(2, new ItemStack(Material.ARROW, 64));
                                    player.getInventory().setItem(8, ItemsGUI.KITS.itemStack);
                                    arrayKnight.add(player);
                                } else {
                                    player.getInventory().clear();
                                    player.getInventory().setItem(4, ItemsGUI.KITS.itemStack);
                                    arrayKnight.remove(player);
                                }
                                player.closeInventory();
                            }));
                }

                @Override
                public void update(Player player, InventoryContents contents) {
                }
            })
            .manager(NoaPlugin.getManager())
            .build());

    public final SmartInventory smartInventory;
    public static final ArrayList<Player> arrayKnight = new ArrayList<>();

    GUI(SmartInventory smartInventory) {
        this.smartInventory = smartInventory;
    }

}
