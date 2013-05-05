/*
 * @author SithSpawn_1
 * @Copyright 2013
 */
package hubreboot;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

       
public class HubReboot extends JavaPlugin{
    FileConfiguration config;
    File cfile;
       
   @Override
    public void onEnable(){
       config = getConfig();
       getConfig().options().copyDefaults(true);
       saveConfig();
       cfile = new File(getDataFolder(), "config.yml");
       long thour = (20 * 60 * 60 * config.getLong("hours"));
       long tminute = (20 * 60 * config.getLong("minutes"));
       long tsecond = (20 * config.getLong("seconds"));
       long waittime = (thour + tminute + tsecond);
       Bukkit.getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().shutdown();
            }
        }, waittime);
       
       getCommand("hubreload").setExecutor(new Commands(this));
       getCommand("hubsettime").setExecutor(new Commands(this));
       getCommand("hubtime").setExecutor(new Commands(this));
       
    }
   
   
}