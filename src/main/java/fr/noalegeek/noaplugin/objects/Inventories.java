package fr.noalegeek.noaplugin.objects;

import org.bukkit.inventory.Inventory;

public enum Inventories {

    KITS(null);

    public final Inventory inventory;

    Inventories(Inventory inventory) {
        this.inventory = inventory;
    }

}
