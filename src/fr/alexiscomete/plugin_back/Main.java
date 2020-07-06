package fr.alexiscomete.plugin_back;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
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
        try {
            File file = new File("spawn.txt");
            if (!file.exists()) file.createNewFile(); else {
                ObjectInputStream ois = new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(file)));
                CommandExecutorPluginBack.coosSpawn = (Coordinates) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        System.out.println("[plugin_back] Plugin en cours de sauvegarde");
        try {
            File file = new File("spawn.txt");
            file.delete();
            file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("spawn.txt"))));
            oos.writeObject(CommandExecutorPluginBack.coosSpawn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
