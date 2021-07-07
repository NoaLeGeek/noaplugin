package fr.noalegeek68.noaplugin.enums;

import fr.noalegeek68.noaplugin.utils.InventoryBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public enum GUI {

    KITS(new InventoryBuilder(ChatColor.GREEN + "Kits", 3)),
    REPORT(new InventoryBuilder("Report").build()),
    INVSEE(new InventoryBuilder("Inventaire", 3));

    public final Inventory inventory;

    GUI(Inventory inventory){
        this.inventory = inventory;
    }
}
