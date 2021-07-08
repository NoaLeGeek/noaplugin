package fr.noalegeek68.noaplugin.objects;

import fr.noalegeek68.noaplugin.utils.InventoryBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public enum GUI {

    KITS(new InventoryBuilder(ChatColor.GREEN + "Kits", 3)),
    REPORT(new InventoryBuilder("Report").setCancelled()/*.setItemsRow(1,new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1),new ItemStack(Material.BLUE_WOOL,1))*/),
    INVSEE(new InventoryBuilder("Inventaire", 3));

    public final InventoryBuilder inventoryBuilder;

    GUI(InventoryBuilder inventoryBuilder){
        this.inventoryBuilder = inventoryBuilder;
    }
}
