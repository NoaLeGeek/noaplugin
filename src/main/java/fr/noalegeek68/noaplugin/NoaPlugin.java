package fr.noalegeek68.noaplugin;

import fr.noalegeek68.noaplugin.commands.*;
import fr.noalegeek68.noaplugin.commands.moderation.ModCommand;
import fr.noalegeek68.noaplugin.commands.moderation.ReportCommand;
import fr.noalegeek68.noaplugin.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class NoaPlugin extends JavaPlugin {

    public static final String pluginPrefix = String.format("%s[%sNoaPlugin%s] ", ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GRAY);
    public static final ArrayList<UUID> arrayModerators = new ArrayList<>();
    private static NoaPlugin instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        System.out.println("[NoaPlugin] Le plugin a été démarré.");
        registerListeners();
        registerCommands();
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Listeners(), this);
    }

    public void registerCommands(){
        registerCommand("alert", new AlertCommand(),"a");
        registerCommand("broadcast", new BroadcastCommand(),"bc");
        registerCommand("spawn", new SpawnCommand(),"s");
        registerCommand("craft", new CraftCommand(), "c");
        registerCommand("enderchest", new EnderchestCommand(), "ec");
        registerCommand("invsee", new InvseeCommand(), "is");
        registerCommand("mod", new ModCommand(), "m");
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
}
