package fr.alexiscomete.plugin_back;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[plugin_back] Plugin activé, récupération des fichiers.");
        Listener l = new ListenerPluginBack();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(l, this);
        CommandExecutor executor = new CommandExecutorPluginBack();
        Objects.requireNonNull(getCommand("back")).setExecutor(executor);
        Objects.requireNonNull(getCommand("getSpawnDist")).setExecutor(executor);
        Objects.requireNonNull(getCommand("spawn")).setExecutor(executor);
        Objects.requireNonNull(getCommand("setSpawn")).setExecutor(executor);
    }

    @Override
    public void onDisable() {
        System.out.println("[plugin_back] Plugin en cours de sauvegarde");
    }
}
