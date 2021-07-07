package fr.noalegeek68.noaplugin.utils;

import fr.noalegeek68.noaplugin.NoaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class InventoryBuilder implements Listener {

    private int rows = 3;
    private String name;
    private final List<ItemStack> itemStacks;
    private Consumer<InventoryClickEvent> clickEventConsumer = InventoryEvent::getInventory;
    private boolean cancelEvent = false;

    public InventoryBuilder(@NotNull String name) {
        this.name = name;
        this.itemStacks = new ArrayList<>();
    }

    public InventoryBuilder(@NotNull String name, int rows) {
        this.name = name;
        this.itemStacks = new ArrayList<>();
        this.setRows(rows);
    }

    public InventoryBuilder onClick(Consumer<InventoryClickEvent> eventConsumer) {
        this.clickEventConsumer = eventConsumer;
        return this;
    }

    public InventoryBuilder setCancelled() {
        this.cancelEvent = !cancelEvent;
        return this;
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if(event.getInventory().getSize() != rows) return;
        if(!event.getView().getTitle().equalsIgnoreCase(name)) return;
        event.setCancelled(cancelEvent);
        this.clickEventConsumer.accept(event);
    }

    public InventoryBuilder setRows(int rows) {
        if(rows > 6 || rows < 1) rows = 3;
        this.rows = rows;
        return this;
    }

    public InventoryBuilder addItems(ItemStack... itemStacks) {
        this.itemStacks.addAll(Arrays.asList(itemStacks));
        return this;
    }

    public InventoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Inventory build() {
        Bukkit.getServer().getPluginManager().registerEvents(this, NoaPlugin.getInstance());
        Inventory inv = Bukkit.createInventory(null, size * 9, name);
        itemStacks.forEach(inv::addItem);
        return inv;
    }

}
