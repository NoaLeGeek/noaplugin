package fr.noalegeek68.noaplugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemStackUtils {

    /*private static final Material[] armors = new Material[]{Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
            Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
            Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS,
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS};*/

    public static boolean isAnArmor(ItemStack is) {
        return is.getType().name().endsWith("_HELMET") || is.getType().name().endsWith("_CHESTPLATE") || is.getType().name().endsWith("_LEGGINGS") ||
                is.getType().name().endsWith("_BOOTS");
    }

    public static boolean isSimilar(ItemStack first, ItemStack second){
        ItemMeta im1 = first.getItemMeta();
        ItemMeta im2 = second.getItemMeta();
        if(!(im1 instanceof Damageable && im2 instanceof Damageable)) return false;
        ((Damageable) im1).setDamage(((Damageable) im2).getDamage());
        first.setItemMeta(im1);
        second.setItemMeta(im2);
        return second.isSimilar(first);
    }

    public static boolean isAirOrNull(ItemStack item){
        return item == null || item.getType().isAir();
    }

    public static Material randomBanner(){
        final List<Material> banners = new ArrayList<>();
        for(Material banner : Material.values()){
            if(banner.name().endsWith("_BANNER") && !banner.name().endsWith("_WALL_BANNER")){
                banners.add(banner);
            }
        }
        return banners.get(new Random().nextInt(banners.size()));
    }

    public static Material randomSkull(){
        final List<Material> skulls = new ArrayList<>();
        for(Material skull : Material.values()){
            if(skull.name().endsWith("_HEAD") && !skull.name().endsWith("_WALL_HEAD")){
                skulls.add(skull);
            }
            if(skull.name().endsWith("_SKULL") && !skull.name().endsWith("_WALL_SKULL")){
                skulls.add(skull);
            }
        }
        return skulls.get(new Random().nextInt(skulls.size()));
    }
}