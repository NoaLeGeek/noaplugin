package net.noalegeek.noaplugin.objects;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.noalegeek.noaplugin.NoaPlugin;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static net.noalegeek.noaplugin.NoaPlugin.temporaryXpNeeded;

public enum Skills {

    WOODCUTTING(temporaryXpNeeded),
    MINING(temporaryXpNeeded),
    FISHING(temporaryXpNeeded),
    FARMING(temporaryXpNeeded),
    COMBAT(temporaryXpNeeded),
    ENCHANTING(temporaryXpNeeded),
    ALCHEMY(temporaryXpNeeded);

    private final List<Long> xpNeededEachLevel;
    //private final List<Void> rewardsForLevel;


    Skills(List<Long> xpNeededEachLevel) {
        this.xpNeededEachLevel = xpNeededEachLevel;
    }

    public static void addXp(Skills skill, Player player, double xpAdded) {
        //Add xpAdded for the player's skill
        NoaPlugin.getInstance().getConfig().set(player.getUniqueId() + ".skills." + skill.name().toLowerCase() + ".actualXp", NoaPlugin.getInstance().getConfig().getDouble(player.getUniqueId() + ".skills." + skill.name().toLowerCase() + ".actualXp") + xpAdded);
        //Verify if the actual xp exceeds the required level for the next level, if true the player get to the next level for the specified skill and receives rewards for the level
        while (NoaPlugin.getInstance().getConfig().getDouble(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".actualXp") >= skill.getXpNeededEachLevel().get(NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level"))) {
            //Increase the player's skill level
            NoaPlugin.getInstance().getConfig().set(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level", NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level") + 1);
            //Remove the amount of xp needed for the next level from the player's actual xp
            NoaPlugin.getInstance().getConfig().set(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".actualXp", NoaPlugin.getInstance().getConfig().getDouble(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".actualXp") - skill.getXpNeededEachLevel().get(NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level") - 1));
            //Play a sound for the player
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            //Send a level up message for the player
            player.sendMessage(ChatColor.DARK_AQUA + "================================================================\n" +
                    ChatColor.AQUA + ChatColor.BOLD + "SKILL LEVEL UP " + ChatColor.RESET + ChatColor.DARK_AQUA + StringUtils.capitalize(skill.name().toLowerCase()) + " " + (NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level") == 1 ? ChatColor.DARK_AQUA + fr.sunderia.sunderiautils.utils.StringUtils.integerToRoman(NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level")) : ChatColor.DARK_GRAY + fr.sunderia.sunderiautils.utils.StringUtils.integerToRoman(NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level") - 1) + " -> " + ChatColor.DARK_AQUA + fr.sunderia.sunderiautils.utils.StringUtils.integerToRoman(NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." + skill.name().toLowerCase(Locale.ROOT) + ".level"))) + "\n" +
                    ChatColor.GREEN + ChatColor.BOLD + "REWARDS\n" +
                    ChatColor.RESET + ChatColor.WHITE + " Lmao you don't have rewards for this level noob\n" +
                    ChatColor.DARK_AQUA + "================================================================"
            );
            //Save config
            NoaPlugin.getInstance().saveConfig();
        }
        //Send a message in the player's actionbar with the xp gained, the actual xp for woodcutting skill and xp needed to level up
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "+" + (String.valueOf(xpAdded).endsWith(".0") ? String.valueOf(xpAdded).substring(0, String.valueOf(xpAdded).length() - 2) : xpAdded) + " " +
                StringUtils.capitalize(skill.name().toLowerCase()) + " (" +
                (String.valueOf(NoaPlugin.getInstance()
                        .getConfig()
                        .getDouble(player.getUniqueId() + ".skills." + skill.name().toLowerCase() + ".actualXp")).endsWith(".0") ?
                        String.valueOf(NoaPlugin.getInstance()
                                        .getConfig()
                                        .getDouble(player.getUniqueId() + ".skills." + skill.name().toLowerCase() + ".actualXp"))
                                .substring(0, String.valueOf(NoaPlugin.getInstance()
                                        .getConfig()
                                        .getDouble(player.getUniqueId() + ".skills." + skill.name().toLowerCase() + ".actualXp")).length() - 2) :
                        NoaPlugin.getInstance().getConfig().getDouble(player.getUniqueId() + ".skills." + skill.name().toLowerCase() + ".actualXp")) +
                "/" + Skills.WOODCUTTING.getXpNeededEachLevel().get(NoaPlugin.getInstance().getConfig().getInt(player.getUniqueId() + ".skills." +
                skill.name().toLowerCase() + ".level")) + ")"));
        //Play a sound for the player
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        //Verify if the player can level up
    }

    //TODO Aller sur hypixel et commencer une nouvelle île, augmenter d'un niveau dans n'importe (mining par exemple) et regarder ce qu'il mettent pour le niveau 1

    public List<Long> getXpNeededEachLevel() {
        return xpNeededEachLevel;
    }
}