package fr.noalegeek68.noaplugin.objects;

import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemsInventories {

    KITS(new ItemBuilder(Material.CHEST).setDisplayName(ChatColor.GREEN + "Kits").build());

    public final ItemStack itemStack;

    ItemsInventories(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
