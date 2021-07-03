package fr.noalegeek68.noaplugin;

import fr.noalegeek68.noaplugin.commands.AlertCommand;
import fr.noalegeek68.noaplugin.commands.TestCommand;
import fr.noalegeek68.noaplugin.listener.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoaPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[NoaPlugin] Le plugin a été démarré.");
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getCommand("test").setExecutor(new TestCommand());
        getCommand("alert").setExecutor(new AlertCommand());
    }

    @Override
    public void onDisable() {
        System.out.println("[NoaPlugin] Le plugin a été éteint.");
    }
}
