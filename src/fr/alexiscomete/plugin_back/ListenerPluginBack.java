package fr.alexiscomete.plugin_back;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPluginBack implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        player.sendMessage("/back dans 10 secondes pour revenir au point de mort"); //message
        int i = CommandExecutorPluginBack.players.indexOf(new PlayerPluginBack(player.getName())); //index du joueur dans la liste des joueurs coos
        PlayerPluginBack p = CommandExecutorPluginBack.players.get(i); //player au format du plugin
        p.setBack(false);
        Location l = player.getLocation();
        p.setDeathCoos(new Coordinates(l.getX(), l.getY(), l.getZ()));
        p.setDeathTime();
        p.setW(player.getWorld());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        CommandExecutorPluginBack.players.add(new PlayerPluginBack(player.getName())); //ajout du joueur dans la liste
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        CommandExecutorPluginBack.players.remove(new PlayerPluginBack(player.getName())); //retrait du joueur dans la liste
    }
}
