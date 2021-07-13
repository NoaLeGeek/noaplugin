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

import java.util.HashMap;
import java.util.Map;

public enum GUI {

    KITS(SmartInventory.builder()
            .size(3, 9)
            .title(ChatColor.DARK_GREEN + "Kits")
            .provider(new InventoryProvider() {
                @Override
                public void init(Player player, InventoryContents contents) {
                    contents.set(1, 4, ClickableItem.of(ItemsGUI.KNIGHTKIT.itemStack,
                            e -> {
                                e.setCancelled(true);
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
                                    player.getInventory().setItem(4, ItemsGUI.KITS.itemStack);
                                    knightMap.put(player, false);
                                }
                                player.closeInventory();
                            }));
                    for(int row = 0; row < contents.inventory().getRows() + 1 ; row++){
                        for(int columns = 0; columns < contents.inventory().getColumns() + 1; columns++) {
                            if (!contents.get(row, columns).isPresent()) {
                                contents.set(row, columns, ClickableItem.empty((new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                                        .setDisplayName(ChatColor.DARK_GRAY + "Rien")
                                        .build())));
                            }
                        }
                    }
                }

                @Override
                public void update(Player player, InventoryContents contents) {
                }
            })
            .manager(NoaPlugin.getManager())
            .build());

    public final SmartInventory smartInventory;
    public static final Map<Player, Boolean> knightMap = new HashMap<>();

    GUI(SmartInventory smartInventory) {
        this.smartInventory = smartInventory;
    }

}
