package net.noalegeek.noaplugin;

import com.comphenix.protocol.ProtocolManager;
import fr.sunderia.sunderiautils.SunderiaUtils;
import fr.sunderia.sunderiautils.utils.ItemBuilder;
import net.noalegeek.noaplugin.listeners.Events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class NoaPlugin extends JavaPlugin {

    public static final String pluginPrefix = String.format("%s[%sNoaPlugin%s] ", ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GRAY);
    private static NoaPlugin instance;
    private static ProtocolManager protocolManager;

    public static NoaPlugin getInstance() {
        return instance;
    }

    public static String getPermission() {
        return "noaplugin.";
    }

    public static ProtocolManager getManager() {
        return protocolManager;
    }

    @Override
    public void onLoad() {
        instance = this;
        protocolManager = getManager();
    }

    @Override
    public void onEnable() {
        SunderiaUtils.of(this);
        this.saveDefaultConfig();
        try {
            SunderiaUtils.registerCommands(this.getClass().getPackageName() + ".commands");
            SunderiaUtils.registerListeners(this.getClass().getPackageName() + ".listeners");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getScheduler().runTaskTimer(this, Events::onSecondEvent, 20, 20);
        Bukkit.addRecipe(new ShapedRecipe(SunderiaUtils.key("test"), new ItemBuilder(Material.NETHERITE_BLOCK).setDisplayName("Heart of Hell").build()).shape("BAB", "AAA", "BAB").setIngredient('A', new RecipeChoice.ExactChoice(new ItemBuilder(Material.NETHERITE_INGOT).setAmount(2).build())).setIngredient('B', new RecipeChoice.ExactChoice(new ItemBuilder(Material.NETHERITE_INGOT).setAmount(5).build())));
        Bukkit.addRecipe(new ShapelessRecipe(SunderiaUtils.key("test2"), new ItemBuilder(Material.BEDROCK).setDisplayName("Mixed Ores").build()).addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.COPPER_INGOT).setAmount(9).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.AMETHYST_SHARD).setAmount(8).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.COAL).setAmount(7).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.IRON_INGOT).setAmount(6).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.GOLD_INGOT).setAmount(5).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.REDSTONE).setAmount(4).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.LAPIS_LAZULI).setAmount(3).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.DIAMOND).setAmount(2).build()))
                .addIngredient(new RecipeChoice.ExactChoice(new ItemBuilder(Material.EMERALD).setAmount(1).build())));
        Bukkit.addRecipe(new ShapedRecipe(SunderiaUtils.key("test3"), new ItemBuilder(Material.PETRIFIED_OAK_SLAB).build()).shape("AAA").setIngredient('A', new RecipeChoice.ExactChoice(new ItemBuilder(Material.OAK_SLAB).setAmount(2).build())));
        Bukkit.getScheduler().runTaskTimer(this, this::saveConfig, 6000L, 6000L);
        getLogger().info("[NoaPlugin] Plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("[NoaPlugin] Plugin disabled.");
    }
}
