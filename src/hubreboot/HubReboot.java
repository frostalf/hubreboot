/*
 * @author SithSpawn_1
 * @Copyright 2013
 */
package hubreboot;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.SimplePluginManager;

       
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
    }
   
    @Override    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
            long thour = (20 * 60 * 60);
            long tminutes = (20 * 60);
            long tseconds = (20);
        if (cmd.getName().equalsIgnoreCase("hubreload")) {
            config = YamlConfiguration.loadConfiguration(cfile);
            sender.sendMessage(ChatColor.GREEN + "Reloaded Hub Restart Config!");
        }
        
        if(cmd.getName().equalsIgnoreCase("hubsettime")) {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.RED + "You didn't specify a time! Need 3 arguments(hours, minutes, seconds)");
                return true;
            }
            if(args.length > 3) {
                sender.sendMessage(ChatColor.RED + "Too many arguments! Only need 3(hours, minutes, seconds)");
                return true;
            }
            if((args.length > 0 ) && (args.length < 3)){
                sender.sendMessage(ChatColor.RED + "Too few arguements! Need 3 arguments(hours, minutes, seconds");
                return true;
            }
            if(args.length == 3){
                
                long thour2 = (thour * Long.valueOf(args[0]));
                long tminutes2 = (tminutes * Long.valueOf(args[1]));
                long tseconds2 = (tseconds * Long.valueOf(args[2]));
                Long displaytime = (thour2 + tminutes2 + tseconds2);
                getConfig().set("hours", thour2);
                getConfig().set("minutes", tminutes2);
                getConfig().set("seconds", tseconds2);
                saveConfig();
                reloadConfig();
                config = YamlConfiguration.loadConfiguration(cfile);
                sender.sendMessage(ChatColor.GREEN + "Time Set To: " + ChatColor.BLUE + "Hours: " + thour2 + " Minutes: " + tminutes2 + " Seconds: " + tseconds2 + " OR " + displaytime + " Ticks");
            return true;
            }                        
        }
        
         if(cmd.getName().equalsIgnoreCase("hubtime")) {
             long hours = getConfig().getLong("hours");
             long minutes = getConfig().getLong("minutes");
             long seconds = getConfig().getLong("seconds");
             long displaytime = (hours + minutes + seconds);
             sender.sendMessage(ChatColor.GREEN + "Configured time: Hours: " + hours + " Minutes: " + minutes + " Seconds: " + seconds + " OR " + displaytime + " ticks");
             return true;
            }
    return true;    
    }
}