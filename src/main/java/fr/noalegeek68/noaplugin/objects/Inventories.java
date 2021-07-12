package fr.noalegeek68.noaplugin.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public enum Inventories {

    KITS(Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Kits"));

    public final Inventory inventory;

    Inventories(Inventory inventory){
        this.inventory = inventory;
    }
}
