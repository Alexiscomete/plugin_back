package fr.alexiscomete.plugin_back;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandExecutorPluginBack implements CommandExecutor { //toutes les commandes

    public static Coordinates coosSpawn = new Coordinates(0.5, 100.0, 0.5); // les coos du spawn par défaut

    public static ArrayList<PlayerPluginBack> players = new ArrayList<>(); // la liste des joueurs sur le serveur au format du plugin

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) { //trouve quel commande à été tapée, toutes les commandes sont dans la même instance de cette class
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

    public void getSpawnDist(CommandSender commandSender){ //revoit la distance entre le spawn et le joueur, ne prend pas en compte le World.
        if (commandSender instanceof Player){ //vérification des autorisations
            Player player = (Player) commandSender;
            Location loc = player.getLocation(); //coos du joueur au format Mc
            Coordinates coos = new Coordinates(loc.getX(), loc.getY(), loc.getZ()); // coos du joueur au format du plugin
            player.sendMessage("Vous êtes à " + coos.getDist(coosSpawn) + " du spawn !"); //message de distance
        }else{ //message d'erreur
            commandSender.sendMessage("Impossible d'utiliser cette commande pour vous, vous devez :\n- être un joueur");
        }
    }

    public void spawn(CommandSender commandSender){
        if (commandSender instanceof Player){ //vérification des autorisations
            Player player = (Player) commandSender;
            PlayerPluginBack p = players.get(players.indexOf(new PlayerPluginBack(player.getName()))); //trouve le player qui fais le commande dans la liste au format du plugin
            Location l = player.getLocation(); //pour récupérer les coos facilement
            p.setBack(false); //les 4 prochaines lignes : autorise le joueur à se tp (après 10 secondes) avec /back à ses coos avant le /spawn
            p.setDeathTime();
            p.setDeathCoos(new Coordinates(l.getX(), l.getY(), l.getZ()));
            p.setW(player.getWorld());
            Location loc = new Location(Bukkit.getWorld("overworld"), coosSpawn.getX(), coosSpawn.getY(), coosSpawn.getZ()); //location du spawn au format Mc
            player.teleport(loc); //téléporte au spawn


        }else {
            commandSender.sendMessage("Vous devez être un joueur pour pouvoir utiliser cette commande !"); //message d'erreur
        }
    }

    public void back(CommandSender commandSender) { //retour au point de mort ou au point avant le /spawn
        if (commandSender instanceof Player){ //vérification des autorisations (seul un player peut de tp ...)
            PlayerPluginBack player = new PlayerPluginBack(commandSender.getName()); //player au format plugin
            if (System.currentTimeMillis()-player.getDeathTime() > 10000){ //cooldown
                if (player.isBack()){ //si il a déjà fait cette commande
                    commandSender.sendMessage("Vous avez déjà fait cette commande ! Ceci n'est pas un /home !");
                }else {
                    PlayerPluginBack p = players.get(players.indexOf(new PlayerPluginBack(commandSender.getName()))); //player avec toutes les infos
                    Coordinates coos = p.getDeathCoos(); //les coos où il se tp
                    ((Player) commandSender).teleport(new Location(p.getW(), coos.getX(), coos.getY(), coos.getZ())); //le tp
                    p.setBack(true); //il s'est donc déjà tp, on enlève l'autorisation
                }
            }else {
                commandSender.sendMessage("Le cooldown est encore en cours, il reste : " + (10-((System.currentTimeMillis()-player.getDeathTime())/10000)) + " secondes."); //temps restant, possible différence entre le cooldown de la condition
            }
        }else {
            commandSender.sendMessage("Vous devez être un joueur pour utiliser cette commande");
        }
    }

    public void setSpawn(CommandSender commandSender, String[] args) { //pour définir le spawn
        if (commandSender.isOp()){ //vérification des autorisations
            if (args.length > 0){ //voit si c'est avec des coos ou avec les coos du joueur
                if (args.length == 3){ //si c'est avec des coos alors il faut 3 arguments
                    coosSpawn = new Coordinates(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])); //nouveau point de spawn
                    Main.saveSpawn(); //sauvegarde du nouveau point
                }else {
                    commandSender.sendMessage("Il faut exactement 3 arguments dans cette commande");
                }
            }else{
                if (commandSender instanceof Player){ //si c'est sans alors il faut que se soit un player
                    Player player = (Player) commandSender;
                    coosSpawn = new Coordinates(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()); //nouveau point de spawn
                }else{
                    commandSender.sendMessage("Vous devez être un joueur pour utiliser cette commande sans arguments");
                }
            }
        }else{
            commandSender.sendMessage("Impossible d'utiliser cette commande pour vous, vous devez :\n- être op");
        }
    }
}
