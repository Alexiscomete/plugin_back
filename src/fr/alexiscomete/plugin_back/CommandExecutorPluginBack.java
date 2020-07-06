package fr.alexiscomete.plugin_back;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandExecutorPluginBack implements CommandExecutor {

    public static Coordinates coosSpawn = new Coordinates(0.5, 100.0, 0.5);

    public static ArrayList<PlayerPluginBack> players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        switch (command.getName()) {
            case "getSpawnDist" :
                getSpawnDist(commandSender);
                return true;
            case "setSpawn" :
                setSpawn(commandSender, strings);
                return true;
            case "spawn" :
                spawn(commandSender);
                return true;
            case "back" :
                back(commandSender);
                return true;
        }
        commandSender.sendMessage("Erreur : commande invalide");
        return false;
    }

    public void getSpawnDist(CommandSender commandSender){
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            Location loc = player.getLocation();
            Coordinates coos = new Coordinates(loc.getX(), loc.getY(), loc.getZ());
            player.sendMessage("Vous êtes à " + coos.getDist(coosSpawn) + " du spawn !");
        }else{
            commandSender.sendMessage("Impossible d'utiliser cette commande pour vous, vous devez :\n- être un joueur");
        }
    }

    public void spawn(CommandSender commandSender){
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            Location loc = new Location(Bukkit.getWorld("overworld"), coosSpawn.getX(), coosSpawn.getY(), coosSpawn.getZ());
            player.teleport(loc);
        }else {
            commandSender.sendMessage("Vous devez être un joueur pour pouvoir utiliser cette commande !");
        }
    }

    public void back(CommandSender commandSender) {
        if (commandSender instanceof Player){
            PlayerPluginBack player = new PlayerPluginBack(commandSender.getName());
            if (System.currentTimeMillis()-player.getDeathTime() > 10000){
                if (player.isBack()){
                    commandSender.sendMessage("Vous avez déjà fait cette commande ! Ceci n'est pas un /home !");
                }else {
                    PlayerPluginBack p = players.get(players.indexOf(new PlayerPluginBack(commandSender.getName())));
                    Coordinates coos = p.getDeathCoos();
                    ((Player) commandSender).teleport(new Location(p.getW(), coos.getX(), coos.getY(), coos.getZ()));
                    p.setBack(true);
                }
            }else {
                commandSender.sendMessage("Le cooldown est encore en cours, il reste : " + (10-((System.currentTimeMillis()-player.getDeathTime())/10000)) + " secondes.");
            }
        }else {
            commandSender.sendMessage("Vous devez être un joueur pour utiliser cette commande");
        }
    }

    public void setSpawn(CommandSender commandSender, String[] args) {
        if (commandSender.isOp()){
            if (args.length > 0){
                if (args.length == 3){
                    coosSpawn = new Coordinates(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                }else {
                    commandSender.sendMessage("Il faut exactement 3 arguments dans cette commande");
                }
            }else{
                if (commandSender instanceof Player){
                    Player player = (Player) commandSender;
                    coosSpawn = new Coordinates(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                }else{
                    commandSender.sendMessage("Vous devez être un joueur pour utiliser cette commande sans arguments");
                }
            }
        }else{
            commandSender.sendMessage("Impossible d'utiliser cette commande pour vous, vous devez :\n- être op");
        }
    }
}
