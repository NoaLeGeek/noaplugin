package fr.noalegeek68.noaplugin.enums;

import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemsGUI {

    KITS(new ItemBuilder(Material.CHEST).setDisplayName(ChatColor.GREEN + "Kits").build());

    public final ItemStack itemStack;

    ItemsGUI(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
