package fr.noalegeek68.noaplugin.listeners;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.noalegeek68.noaplugin.NoaPlugin;
import fr.noalegeek68.noaplugin.objects.GUI;
import fr.noalegeek68.noaplugin.objects.ItemsGUI;
import fr.noalegeek68.noaplugin.utils.ItemBuilder;
import fr.noalegeek68.noaplugin.utils.ItemStackUtils;
import fr.noalegeek68.noaplugin.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Listeners implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if(player.getInventory().getHelmet() != null && player.getInventory().getHelmet().isSimilar(new ItemBuilder(Material.DIAMOND_HELMET)
                .build())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 1));
        } else {
            if(player.hasPotionEffect(PotionEffectType.SPEED)) player.removePotionEffect(PotionEffectType.SPEED);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(GUI.arrayKnight.contains(player)) GUI.arrayKnight.add(player);
        player.getInventory().clear();
        player.getInventory().setItem(4, ItemsGUI.KITS.itemStack);
        player.updateInventory();
        if(NoaPlugin.arrayModerators.contains(player.getUniqueId())){
            NoaPlugin.arrayModerators.remove(event.getPlayer().getUniqueId());
            player.teleport(new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX(), player.getWorld().getSpawnLocation().getY(), player.getWorld().getSpawnLocation().getZ(), player.getWorld().getSpawnLocation().getYaw(), player.getWorld().getSpawnLocation().getPitch()));
            // Unvanish the person
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if(itemStack != null) {
            if (itemStack.isSimilar(ItemsGUI.KITS.itemStack)) {
                event.setCancelled(true);
                GUI.KITS.smartInventory.open(player);
            }
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (ItemStackUtils.isAirOrNull(itemStack)) return;
        if(inventory == player.getInventory() && player.getGameMode().equals(GameMode.SURVIVAL)) {
            if(itemStack.isSimilar(ItemsGUI.KITS.itemStack)){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onFish(PlayerFishEvent event){
        Player player = event.getPlayer();
        if(event.getState() == PlayerFishEvent.State.IN_GROUND){
            event.setCancelled(true);
            SmartInventory fishingGUI = SmartInventory.builder()
                    .manager(NoaPlugin.getManager())
                    .size(6, 9)
                    .type(InventoryType.CHEST)
                    .title("Fishing Game")
                    .id("fishinggame")
                    .provider(new InventoryProvider() {
                        final ItemStack fishingRod = player.getItemInHand();
                        final FishingRewards itemCaught = FishingRewards.values()[new Random().nextInt(FishingRewards.values().length)];
                        int columnFishingRod = 4;
                        int columnItemCaught = 4;
                        int score = itemCaught.scoreToHave / 2;
                        @Override
                        public void init(Player player, InventoryContents contents) {
                            contents.fill(ClickableItem.empty(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                                    .setDisplayName(ChatColor.DARK_GRAY + "Rien")
                                    .build()));
                            contents.fillRect(4, 1, 4, 7, ClickableItem.empty(new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                                    .setDisplayName(ChatColor.DARK_AQUA + "Slot de déplacement")
                                    .addLoreLine(ChatColor.AQUA + "L'objet peut se déplacer ici.")
                                    .build()));
                            contents.set(1, 3, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE)
                                            .setDisplayName(ChatColor.DARK_BLUE + "Gauche")
                                            .addLoreLine(ChatColor.BLUE + "En appuyant sur cet item,")
                                            .addLoreLine(ChatColor.BLUE + "la canne à pêche va bouger à gauche.")
                                            .setCustomModelData(1)
                                            .build(),
                                    e -> {
                                        e.setCancelled(true);
                                        columnFishingRod--;
                                    }));
                            contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                                    .setDisplayName(ChatColor.GOLD + "Informations")
                                    .addLoreLine(ChatColor.GRAY + "Pourcentage d'avoir l'objet :")
                                    .addLoreLine(ChatColor.YELLOW + String.valueOf((score * 100 / itemCaught.scoreToHave)))
                                    .addLoreLine(ChatColor.GRAY + "Chance d'avoir l'objet :")
                                    .addLoreLine(ChatColor.YELLOW + String.valueOf(itemCaught.chanceToFish) + "%")
                                    .build()));
                            contents.set(1, 5, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE)
                                            .setDisplayName(ChatColor.DARK_BLUE + "Droite")
                                            .addLoreLine(ChatColor.BLUE + "En appuyant sur cet item,")
                                            .addLoreLine(ChatColor.BLUE + "la canne à pêche va bouger à droite.")
                                            .setCustomModelData(2)
                                            .build(),
                                    e -> {
                                        e.setCancelled(true);
                                        columnFishingRod++;
                                    }));
                            contents.set(3, columnFishingRod, ClickableItem.empty(fishingRod));
                            contents.set(4, 3, ClickableItem.empty(itemCaught.itemStack));
                        }
                        @Override
                        public void update(Player player, InventoryContents contents) {
                            int state = contents.property("state", 0);
                            contents.setProperty("state", state + 1);
                            int percentage = score * 100 / itemCaught.scoreToHave;
                            if(percentage <= 0) {
                                player.closeInventory();
                                player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.RED + "Vous n'avez pas réussi à attraper l'objet !");
                            }
                            if (percentage > 0) {
                                if (percentage >= 21) {
                                    if (percentage >= 41) {
                                        if (percentage >= 61) {
                                            if (percentage >= 81) {
                                                if (percentage >= 100) {
                                                    player.closeInventory();
                                                    player.sendMessage(NoaPlugin.pluginPrefix + ChatColor.GREEN + "Vous avez réussi à attraper l'objet !");
                                                    if(player.getInventory().firstEmpty() == -1){
                                                        player.getWorld().dropItem(player.getLocation(), itemCaught.itemStack);
                                                    } else {
                                                        player.getInventory().addItem(itemCaught.itemStack);
                                                    }
                                                }
                                                contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                                                        .setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Informations")
                                                        .addLoreLine(ChatColor.GRAY + "Pourcentage d'avoir l'objet :")
                                                        .addLoreLine(ChatColor.DARK_GREEN + String.valueOf((score * 100 / itemCaught.scoreToHave)))
                                                        .addLoreLine(ChatColor.GRAY + "Chance d'avoir l'objet :")
                                                        .addLoreLine(ChatColor.DARK_GREEN + String.valueOf(itemCaught.chanceToFish) + "%")
                                                        .build()));
                                            }
                                            contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                                                    .setDisplayName(ChatColor.DARK_GREEN + "Informations")
                                                    .addLoreLine(ChatColor.GRAY + "Pourcentage d'avoir l'objet :")
                                                    .addLoreLine(ChatColor.GREEN + String.valueOf((score * 100 / itemCaught.scoreToHave)))
                                                    .addLoreLine(ChatColor.GRAY + "Chance d'avoir l'objet :")
                                                    .addLoreLine(ChatColor.GREEN + String.valueOf(itemCaught.chanceToFish) + "%")
                                                    .build()));
                                        }
                                        contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                                                .setDisplayName(ChatColor.GOLD + "Informations")
                                                .addLoreLine(ChatColor.GRAY + "Pourcentage d'avoir l'objet :")
                                                .addLoreLine(ChatColor.YELLOW + String.valueOf((score * 100 / itemCaught.scoreToHave)))
                                                .addLoreLine(ChatColor.GRAY + "Chance d'avoir l'objet :")
                                                .addLoreLine(ChatColor.YELLOW + String.valueOf(itemCaught.chanceToFish) + "%")
                                                .build()));
                                    }
                                    contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                                            .setDisplayName(ChatColor.RED + "Informations")
                                            .addLoreLine(ChatColor.GRAY + "Pourcentage d'avoir l'objet :")
                                            .addLoreLine(ChatColor.GOLD + String.valueOf((score * 100 / itemCaught.scoreToHave)))
                                            .addLoreLine(ChatColor.GRAY + "Chance d'avoir l'objet :")
                                            .addLoreLine(ChatColor.GOLD + String.valueOf(itemCaught.chanceToFish) + "%")
                                            .build()));
                                }
                                contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                                        .setDisplayName(ChatColor.DARK_RED + "Informations")
                                        .addLoreLine(ChatColor.GRAY + "Pourcentage d'avoir l'objet :")
                                        .addLoreLine(ChatColor.RED + String.valueOf((score * 100 / itemCaught.scoreToHave)))
                                        .addLoreLine(ChatColor.GRAY + "Chance d'avoir l'objet :")
                                        .addLoreLine(ChatColor.RED + String.valueOf(itemCaught.chanceToFish) + "%")
                                        .build()));
                            }
                            if(columnFishingRod >= 8){
                                columnFishingRod = 7;
                            } else if(columnFishingRod <= 0){
                                columnFishingRod = 1;
                            } else {
                                contents.fillRect(3, 1, 3, 7, ClickableItem.empty(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                                        .setDisplayName(ChatColor.DARK_GRAY + "Rien")
                                        .build()));
                                contents.set(3, columnFishingRod, ClickableItem.empty(fishingRod));
                            }
                            if(columnFishingRod == columnItemCaught){
                                score += itemCaught.scoreToRemove + (itemCaught.scoreToAdd / 10);
                            }
                            if(state % (itemCaught.removeAfterTime * 20) == 0){
                                score -= itemCaught.scoreToRemove;
                            }
                            if(state % (itemCaught.moveAfterTime * 20) == 0){
                                for(int column = 1; column < 8; column++){ // Getting the column of the itemCaught.
                                    if(contents.get(4, column).get().getItem().isSimilar(itemCaught.itemStack)){
                                        columnItemCaught = column;
                                    }
                                }
                                contents.fillRect(4, 1, 4, 7, ClickableItem.empty(new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                                        .setDisplayName(ChatColor.DARK_AQUA + "Slot de déplacement")
                                        .addLoreLine(ChatColor.AQUA + "L'objet peut se déplacer ici.")
                                        .build()));
                                contents.set(4, Utils.randomMinMax(columnItemCaught == 1 ? 1 : columnItemCaught - 1, columnItemCaught == 7 ? 7 : columnItemCaught + 1), ClickableItem.empty(itemCaught.itemStack));
                            }
                        }
                    })
                    .build();
            fishingGUI.open(player);
        }
    }
    private enum FishingRewards {
        RAW_COD(new ItemStack(Material.COD), 100, 10, 10, 3, 3, 15),
        RAW_SALMON(new ItemStack(Material.SALMON), 150, 15, 15, 3, 3, 15),
        TROPICAL_FISH(new ItemStack(Material.TROPICAL_FISH), 200, 17,  2, 2, 3, 20),
        PUFFERFISH(new ItemStack(Material.PUFFERFISH), 125, 10,  1,2, 3, 20),
        BOW(new ItemStack(Material.BOW), 175, 15, 17,  2, 2, 15),
        FISHING_ROD(new ItemStack(Material.FISHING_ROD), 225, 17, 20, 2, 2, 10),
        GOLD_BLOCK(new ItemStack(Material.GOLD_BLOCK), 250, 20, 22, 1, 2, 5);

        private final ItemStack itemStack;
        private final int scoreToHave; // score to have to gain the item caught
        private final int scoreToAdd; // score to add when the player is above the item caught
        private final int scoreToRemove; // score to remove when the player is not above the item caught
        private final int removeAfterTime;
        private final int moveAfterTime;
        private final int chanceToFish;
        // addAfterTime, removeAfterTime and moveAfterTime are expressed in seconds, the conversion is done automatically

        FishingRewards(ItemStack itemStack, int scoreToHave, int scoreToAdd, int scoreToRemove, int removeAfterTime, int moveAfterTime, int chanceToFish) {
            this.itemStack = itemStack;
            this.scoreToHave = scoreToHave;
            this.scoreToAdd = scoreToAdd;
            this.scoreToRemove = scoreToRemove;
            this.removeAfterTime = removeAfterTime;
            this.moveAfterTime = moveAfterTime;
            this.chanceToFish = chanceToFish;
        }
    }
}
