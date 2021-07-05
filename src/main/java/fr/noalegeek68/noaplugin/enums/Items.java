package fr.noalegeek68.noaplugin.enums;

import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

    KITS(new ItemBuilder(Material.CHEST).setDisplayName(ChatColor.GREEN + "Kits").build());

    public final ItemStack itemStack;

    Items(ItemStack is) {
        this.itemStack = is;
    }
}
