package fr.noalegeek68.noaplugin.objects;

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
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public enum SmartInventories {

    /*REPORT(SmartInventory.builder()
            .size(3, 9)
            .id("reportgui")
            .title("Sélectionnez une raison")
            .type(InventoryType.CHEST)
            .provider(new InventoryProvider() {
                private int knockbackLevel = 0;
                @Override
                public void init(Player player, InventoryContents contents) {
                    knockbackLevel = 0;
                }
                @Override
                public void update(Player player, InventoryContents contents) {
                    int state = contents.property("state", 0);
                    contents.setProperty("state", state + 1);
                    if(state % 20 != 0) return;
                    knockbackLevel++;
                    contents.set(1, 1, ClickableItem.of(new ItemBuilder(Material.STICK)
                                .setDisplayName(ChatColor.DARK_GRAY + "AntiKnockback")
                                .setLore(ChatColor.GRAY + "\nLe joueur ne reçoit pas d'effets de recul.")
                                .addEnchant(Enchantment.KNOCKBACK, knockbackLevel)
                                .build(),
                            e -> {

                            }));
                }
            }).manager(NoaPlugin.getManager()).build());

            This is an exemple for later.

            */
    EXAMPLE(SmartInventory.builder().build());

    public final SmartInventory smartInventory;

    SmartInventories(SmartInventory smartInventory){
        this.smartInventory = smartInventory;
    }

}
