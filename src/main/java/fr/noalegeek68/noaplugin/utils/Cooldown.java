package fr.noalegeek68.noaplugin.utils;

import fr.noalegeek68.noaplugin.NoaPlugin;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author <a href="https://bukkit.org/threads/cooldown-manager.290459/">LCastr0</a>
 */
public class Cooldown {
    
    public enum CooldownType {
        HEAL_FEED_COMMAND("status", 15),
        GRAPPLING_HOOK("grappling_hook", 2),
        ;

        public final String name;
        public final int defaultTime;
        
        CooldownType(String name, int i) {
            this.name = name;
            this.defaultTime = i;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final Map<String, Cooldown> cooldowns = new HashMap<>();
    private long start;
    private final int timeInSeconds;
    private final UUID id;
    private final String cooldownName;

    public Cooldown(UUID id, String cooldownName, int timeInSeconds) {
        this.id = id;
        this.cooldownName = cooldownName;
        this.timeInSeconds = timeInSeconds;
    }

    public Cooldown(UUID id, CooldownType cooldownType, int timeInSeconds) {
        this(id, cooldownType.name, timeInSeconds);
    }

    public Cooldown(UUID id, CooldownType cooldownType) {
        this(id, cooldownType.name, cooldownType.defaultTime);
    }

    public static boolean isInCooldown(UUID id, String cooldownName) {
        if(getTimeLeft(id, cooldownName) >= 1) {
            return true;
        } else {
            stop(id, cooldownName);
            return false;
        }
    }

    private static void stop(UUID id, String cooldownName) {
        Cooldown.cooldowns.remove(id + cooldownName);
    }

    private static Cooldown getCooldown(UUID id, String cooldownName) {
        return cooldowns.get(id + cooldownName);
    }

    public static int getTimeLeft(UUID id, String cooldownName) {
        Cooldown cooldown = getCooldown(id, cooldownName);
        int f = -1;
        if(cooldown != null) {
            long now = System.currentTimeMillis();
            long cooldownTime = cooldown.start;
            int totalTime = cooldown.timeInSeconds;
            int r = (int) (now - cooldownTime) / 1000;
            f = (r - totalTime) * (-1);
        }
        return f;
    }

    public void start() {
        this.start = System.currentTimeMillis();
        cooldowns.put(this.id.toString() + this.cooldownName, this);
    }

    public static String cooldownMessage(UUID uuid, CooldownType type) {
        return NoaPlugin.pluginPrefix + ChatColor.RED + "Please wait " + ChatColor.GOLD + getTimeLeft(uuid, type.name) + ChatColor.RED + " before doing that !";
    }
}
