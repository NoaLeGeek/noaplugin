package fr.noalegeek68.noaplugin.utils;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;

public class ItemBuilder {

    private ItemStack stack;

    public ItemBuilder(Material mat) {
        stack = new ItemStack(mat);
    }

    public ItemBuilder(Material mat, int amount) {
        stack = new ItemStack(mat, amount);
    }

    public ItemMeta getItemMeta() {
        return stack.getItemMeta();
    }

    public ItemBuilder setColor(Color color) {
        if(!(stack.getItemMeta() instanceof LeatherArmorMeta)) return this;
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(color);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setGlow(boolean glow) {
        if(glow) {
            if(ItemStackUtils.isAnArmor(stack)) {
                addEnchant(Enchantment.KNOCKBACK, 1);
            } else {
                addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1);
            }
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            ItemMeta meta = getItemMeta();
            for(Enchantment enchantment : meta.getEnchants().keySet()) {
                meta.removeEnchant(enchantment);
            }
        }
        return this;
    }

    public ItemBuilder setGlow() {
        return setGlow(stack.getEnchantments().isEmpty());
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = stack.getItemMeta();
        if(!(meta instanceof Damageable)) return this;
        meta.setUnbreakable(unbreakable);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta meta) {
        stack.setItemMeta(meta);
        return this;
    }

    /**
     * @deprecated Use {@link #setHead(OfflinePlayer)}
     * @param owner The owner of the head
     * @return the item stack
     */
    @Deprecated
    public ItemBuilder setHead(String owner) {
        return setHead(Bukkit.getOfflinePlayer(owner));
    }

    public ItemBuilder setHead(OfflinePlayer player) {
        if(!(stack.getItemMeta() instanceof SkullMeta)) return this;
        SkullMeta meta =(SkullMeta) stack.getItemMeta();
        if(!meta.hasOwner()) return this;
        meta.setOwningPlayer(player);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(displayName);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setItemStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(lore);
        ItemMeta meta = getItemMeta();
        meta.setLore(loreList);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return stack;
    }
}
