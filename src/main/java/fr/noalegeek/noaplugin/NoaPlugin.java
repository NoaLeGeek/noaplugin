package fr.noalegeek.noaplugin;

import com.comphenix.protocol.ProtocolManager;
import fr.noalegeek.noaplugin.commands.*;
import fr.noalegeek.noaplugin.commands.moderation.ReportCommand;
import fr.noalegeek.noaplugin.listeners.Events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public final class NoaPlugin extends JavaPlugin {

    public static final String pluginPrefix = String.format("%s[%sNoaPlugin%s] ", ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GRAY);
    private static NoaPlugin instance;
    private static ProtocolManager protocolManager;

    @Override
    public void onLoad() {
        instance = this;
        protocolManager = getManager();
    }

    @Override
    public void onEnable() {
        System.out.println("[NoaPlugin] Le plugin a été démarré.");
        registerListeners();
        registerCommands();
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Events(), this);
    }

    public void registerCommands(){
        registerCommand("alert", new AlertCommand(),"a");
        registerCommand("broadcast", new BroadcastCommand(),"bc");
        registerCommand("spawn", new SpawnCommand(),"s");
        registerCommand("craft", new CraftCommand(), "c");
        registerCommand("enderchest", new EnderchestCommand(), "ec");
        registerCommand("invsee", new InvseeCommand(), "is");
        registerCommand("report", new ReportCommand(), "r");
    }

    public void registerCommand(@NotNull String commandName, @NotNull CommandExecutor commandExecutor, @Nullable String... commandAliases){
        PluginCommand command = getCommand(commandName);
        if(commandAliases != null && commandAliases.length != 0) command.setAliases(Arrays.asList(commandAliases));
        command.setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {
        System.out.println("[NoaPlugin] Le plugin a été éteint.");
    }

    public static NoaPlugin getInstance(){ return instance; }
    public static String getPermission(){ return "noaplugin."; }
    public static ProtocolManager getManager(){ return protocolManager; }
}
