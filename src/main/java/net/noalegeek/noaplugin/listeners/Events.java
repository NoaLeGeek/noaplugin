package net.noalegeek.noaplugin.listeners;

import fr.sunderia.sunderiautils.SunderiaUtils;
import fr.sunderia.sunderiautils.utils.ItemStackUtils;
import net.noalegeek.noaplugin.objects.Inventories;
import net.noalegeek.noaplugin.objects.Items;
import net.noalegeek.noaplugin.objects.Skills;
import net.noalegeek.noaplugin.utils.BrokenBlocksService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Events implements Listener {

    private final List<Block> placedBlocks = new ArrayList<>();

    public static void onSecondEvent() {

    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MILK_BUCKET)
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 999999, 255, false, false));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 999999, 255, false, false));
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        if (event.getNewState().getType() != Material.COBBLESTONE) return;
        int random = SunderiaUtils.getRandom().nextInt(100);
        if (random < 50) {
            event.getNewState().setType(Material.COBBLESTONE);
        } else if (random < 75) {
            event.getNewState().setType(Material.COAL_ORE);
        } else if (random < 90) {
            event.getNewState().setType(Material.IRON_ORE);
        } else if (random < 97) {
            event.getNewState().setType(Material.GOLD_ORE);
        } else {
            event.getNewState().setType(Material.DIAMOND_ORE);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player) || event.getEntityType() != EntityType.SNOWBALL || event.getEntity().getCustomName() == null || !event.getEntity().getCustomName().equals("Grenada"))
            return;
        event.getEntity().getWorld().createExplosion(event.getHitBlock() == null ? event.getHitEntity().getLocation() : event.getHitBlock().getLocation(), 1, false, false);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        //Verify if the block isn't placed
        if (!placedBlocks.contains(event.getBlock())) {
            //Set xpAdded depending on the type of the block broken
            switch (event.getBlock().getType()) {
                //Mining skill
                case ICE, NETHERRACK -> Skills.addXp(Skills.MINING, event.getPlayer(), 0.5);
                case GRAVEL, STONE, COBBLESTONE -> Skills.addXp(Skills.MINING, event.getPlayer(), 1);
                case END_STONE, SAND, RED_SAND, MYCELIUM -> Skills.addXp(Skills.MINING, event.getPlayer(), 3);
                case COPPER_ORE -> Skills.addXp(Skills.MINING, event.getPlayer(), 4);
                case NETHER_QUARTZ_ORE, COAL_ORE, IRON_ORE -> Skills.addXp(Skills.MINING, event.getPlayer(), 5);
                case GOLD_ORE -> Skills.addXp(Skills.MINING, event.getPlayer(), 6);
                case GLOWSTONE, LAPIS_ORE, REDSTONE_ORE -> Skills.addXp(Skills.MINING, event.getPlayer(), 7);
                case EMERALD_ORE -> Skills.addXp(Skills.MINING, event.getPlayer(), 9);
                case DIAMOND_ORE -> Skills.addXp(Skills.MINING, event.getPlayer(), 10);
                case OBSIDIAN -> Skills.addXp(Skills.MINING, event.getPlayer(), 20);
                case ANCIENT_DEBRIS -> Skills.addXp(Skills.MINING, event.getPlayer(), 25);
                //Farming
                case CACTUS, RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK ->
                        Skills.addXp(Skills.FARMING, event.getPlayer(), 2);
                case COCOA_BEANS, MELON, BEETROOTS, CARROTS, POTATOES, WHEAT ->
                        Skills.addXp(Skills.FARMING, event.getPlayer(), 4);
                case PUMPKIN -> Skills.addXp(Skills.FARMING, event.getPlayer(), 4.5);
                case RED_MUSHROOM, BROWN_MUSHROOM, SUGAR_CANE -> Skills.addXp(Skills.FARMING, event.getPlayer(), 6);
                default -> {
                    //Verify if the block is a log to give xp to the player
                    if (event.getBlock().getType().name().contains("LOG"))
                        Skills.addXp(Skills.WOODCUTTING, event.getPlayer(), 6);
                }
            }
        } else
            placedBlocks.remove(event.getBlock());
    }

    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event) {
        if (!BrokenBlocksService.isBrokenBlock(event.getPlayer().getTargetBlock(Set.of(Material.AIR, Material.WATER), 5).getLocation()))
            return;
        BrokenBlocksService.getBrokenBlock(event.getPlayer().getTargetBlock(Set.of(Material.AIR, Material.WATER), 5).getLocation()).incrementDamage(event.getPlayer(), event.getPlayer().getTargetBlock(Set.of(Material.AIR, Material.WATER), 5).getType().getHardness());
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        //Verify if it's a player
        if (event.getEntity() instanceof Player player) {
            //Verify if the item picked up is a wool
            if (!ItemStackUtils.hasPersistentDataContainer(event.getItem().getItemStack(), SunderiaUtils.key("xpReceived"), PersistentDataType.BYTE)) {
                for (int i = 0; i < event.getItem().getItemStack().getAmount(); i++) {
                    if (event.getItem().getItemStack().getType().name().contains("WOOL")) {
                        Skills.addXp(Skills.FARMING, player, 2);
                    } else {
                        switch (event.getItem().getItemStack().getType()) {
                            //Farming
                            case LEATHER -> Skills.addXp(Skills.FARMING, player, 1);
                            case PORKCHOP, RABBIT_FOOT, RABBIT, RABBIT_HIDE, CHICKEN, FEATHER, BEEF, MUTTON ->
                                    Skills.addXp(Skills.FARMING, player, 2);
                        }
                    }
                    ItemMeta itemMeta = event.getItem().getItemStack().hasItemMeta() ? event.getItem().getItemStack().getItemMeta() : Bukkit.getItemFactory().getItemMeta(event.getItem().getItemStack().getType());
                    itemMeta.getPersistentDataContainer().set(SunderiaUtils.key("xpReceived"), PersistentDataType.BYTE, (byte) 1);
                    event.getItem().getItemStack().setItemMeta(itemMeta);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            switch (event.getEntityType()) {
                case BAT -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 1);
                case CHICKEN -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 2);
                case SHEEP, COW, RABBIT, PIG -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 3);
                case SLIME -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 4);
                case SILVERFISH, MUSHROOM_COW, SKELETON ->
                        Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 5);
                case ZOMBIE -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 6);
                case ZOMBIE_VILLAGER -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 7);
                case WITCH, CREEPER -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 8);
                case SPIDER -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 9);
                case WOLF, BLAZE, CAVE_SPIDER -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 10);
                case ZOMBIFIED_PIGLIN -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 20);
                case ENDERMITE -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 25);
                case ENDERMAN -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 28);
                case IRON_GOLEM, WITHER_SKELETON -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 40);
                case MAGMA_CUBE -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 45);
                case GHAST -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 50);
                case ENDER_DRAGON -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 300);
                case WITHER -> Skills.addXp(Skills.COMBAT, event.getEntity().getKiller(), 1000);
            }
        }
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        BrokenBlocksService.createBrokenBlock(event.getBlock().getLocation(), (int) Math.round(event.getBlock().getType().getHardness() / (event.getBlock().getBreakSpeed(event.getPlayer()) * (1 / 0.00081))));
    }

    @EventHandler
    public void onBlockDamageAbort(BlockDamageAbortEvent event) {
        BrokenBlocksService.removeBrokenBLock(event.getBlock().getLocation());
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        //Add the block to the placedBlocks list to verify if the block is placed
        placedBlocks.add(event.getBlockPlaced());
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        //The block is removed from the list when the block that's added to the list is pushed with a piston, so we remove the block from the list and add the block to the list that has a new location modified by the piston
        event.getBlocks()
                .stream()
                .filter(placedBlocks::contains)
                .map(Block::getState)
                .forEach(block -> {
                    placedBlocks.remove(block.getBlock());
                    //We need to do a scheduler because the block is added to the list too fast while he's not pushed to the new location
                    Bukkit.getScheduler().runTask(SunderiaUtils.getPlugin(), () -> placedBlocks.add(block.getLocation().add(event.getDirection().getDirection()).getBlock()));
                });
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        //The block is removed from the list when the block that's added is pushed with a piston, so we remove the block from the list and add the block to the list that has a new location modified by the piston
        event.getBlocks()
                .stream()
                .filter(placedBlocks::contains)
                .map(Block::getState)
                .forEach(block -> {
                    placedBlocks.remove(block.getBlock());
                    //We need to do a scheduler because the block is added to the list too fast while the block is not pushed to the new location
                    Bukkit.getScheduler().runTask(SunderiaUtils.getPlugin(), () -> placedBlocks.add(block.getLocation().add(event.getDirection().getDirection()).getBlock()));
                });
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent event) {
        //Go through all blocks that have the "isPlaced" metadata to remove that metadata because when saplings that have "isPlaced" metadata grow the logs in same position of the saplings have the "isPlaced" metadata
        event.getBlocks()
                .stream()
                .map(BlockState::getBlock)
                .filter(placedBlocks::contains)
                .forEach(placedBlocks::remove);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        //Cancel the opening of the default crafting GUI and open the custom crafting GUI
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CRAFTING_TABLE && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
            event.getPlayer().openInventory(Inventories.CRAFTING_GUI);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().setItem(4, Items.MINEMOBS_GUN);
    }

    @EventHandler
    public void onPlayerDebug(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase("debug")) {
            event.setCancelled(true);
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (ItemStackUtils.isNotAirNorNull(itemStack) && itemStack.hasItemMeta())
                        if (itemStack.getItemMeta().getPersistentDataContainer().has(SunderiaUtils.key("wasPlaced"), PersistentDataType.STRING))
                            System.out.println(itemStack);
                }
            }
        }
    }
}
