package fr.noalegeek.noaplugin.objects;

import fr.noalegeek.noaplugin.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

    // Items who open GUI
    KITS(new ItemBuilder(Material.CHEST)
            .setDisplayName(ChatColor.GREEN + "Kits")
            .build()),

    // Items in GUI
    KNIGHTKIT(new ItemBuilder(Material.IRON_SWORD)
            .setDisplayName(ChatColor.DARK_GREEN + "Kit Chevalier")
            .build());

    public final ItemStack itemStack;

    Items(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
