package fr.noalegeek68.noaplugin;

import fr.noalegeek68.noaplugin.commands.AlertCommand;
import fr.noalegeek68.noaplugin.commands.BroadcastCommand;
import fr.noalegeek68.noaplugin.commands.SpawnCommand;
import fr.noalegeek68.noaplugin.commands.TestCommand;
import fr.noalegeek68.noaplugin.listener.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class NoaPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[NoaPlugin] Le plugin a été démarré.");
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        getCommand("test").setExecutor(new TestCommand());
        getCommand("alert").setExecutor(new AlertCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
    }

    @Override
    public void onDisable() {
        System.out.println("[NoaPlugin] Le plugin a été éteint.");
    }
}
