package fr.alexiscomete.plugin_back;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandExecutorPluginBack implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        switch (command.getName()) {
            case "getSpawnDist" :
                getSpawnDist(commandSender);
                return true;
            case "setSpawn" :
                setSpawn(commandSender);
                return true;
            case "spawn" :
                spawn();
                return true;
            case "back" :
                back(commandSender);
                return true;
        }
        commandSender.sendMessage("Erreur : commande invalide");
        return false;
    }

    public void getSpawnDist(CommandSender commandSender){

    }

    public void spawn(){

    }

    public void back(CommandSender commandSender) {

    }

    public void setSpawn(CommandSender commandSender) {

    }
}
