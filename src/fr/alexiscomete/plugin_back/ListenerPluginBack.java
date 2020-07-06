package fr.alexiscomete.plugin_back;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ListenerPluginBack implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        player.sendMessage("/back dans 10 secondes pour revenir au point de mort");
    }
}
