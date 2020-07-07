package fr.alexiscomete.plugin_back;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.Serializable;
import java.util.Objects;

public class PlayerPluginBack implements Serializable {
    private Coordinates deathCoos = new Coordinates();
    private String name;
    private long deathTime = System.currentTimeMillis();
    private boolean back = true;
    private World w = Bukkit.getWorld("overworld");

    public World getW() {
        return w;
    }

    public void setW(World w) {
        this.w = w;
    }

    public Coordinates getDeathCoos() {
        return deathCoos;
    }

    public long getDeathTime() {
        return deathTime;
    }

    public boolean isBack() {
        return back;
    }

    public PlayerPluginBack(String name) {
        this.name = name;
    }

    public void setDeathCoos(Coordinates coos){
        this.deathCoos = coos;
    }

    public void setBack(boolean b){
        this.back = b;
    }

    public void setDeathTime(){
        deathTime = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) { //pour comparer seulement le nom dans les ArrayList
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPluginBack that = (PlayerPluginBack) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}