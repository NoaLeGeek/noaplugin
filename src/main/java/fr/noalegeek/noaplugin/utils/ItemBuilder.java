package fr.noalegeek.noaplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack stack;

    public ItemBuilder(Material mat) {
        stack = new ItemStack(mat);
    }

    public ItemBuilder(Material mat, int amount) {
        stack = new ItemStack(mat, amount);
    }

    public ItemBuilder(ItemStack itemstack) {
        stack = itemstack;
    }

    public ItemMeta getItemMeta() {
        return stack.getItemMeta();
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
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

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = stack.getItemMeta();
        im.setLore(lore);
        stack.setItemMeta(im);
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

    public ItemBuilder removeLoreLine(String line){
        ItemMeta im = stack.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if(!lore.contains(line)) return this;
        lore.remove(line);
        im.setLore(lore);
        stack.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(int index){
        ItemMeta im = stack.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if(index<0||index>lore.size()) return this;
        lore.remove(index);
        im.setLore(lore);
        stack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line){
        ItemMeta im = stack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(im.hasLore())lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        stack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos){
        ItemMeta im = stack.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        stack.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder addProtection(int level) {
        return addEnchant(Enchantment.PROTECTION_EXPLOSIONS, level).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, level).addEnchant(Enchantment.PROTECTION_FIRE, level)
                .addEnchant(Enchantment.PROTECTION_PROJECTILE, level);
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder addItemFlag(ItemFlag... flags) {
        for (ItemFlag flag : flags) {
            addItemFlag(flag);
        }
        return this;
    }

    public ItemBuilder addPage(String text) {
        BookMeta bm = (BookMeta) stack.getItemMeta();
        bm.addPage(text);
        return this;
    }

    public ItemBuilder setAuthor(String author) {
        BookMeta bm = (BookMeta) stack.getItemMeta();
        bm.setAuthor(author);
        return this;
    }

    public ItemBuilder setBookTitle(String title) {
        BookMeta bm = (BookMeta) stack.getItemMeta();
        bm.setTitle(title);
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(customModelData);
        setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return stack;
    }
}
