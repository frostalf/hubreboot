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
       
         
    long thour = (20 * 60 * 60 * config.getLong("hour"));
    long tminutes = (20 * 60 * config.getLong("minute"));
    long tseconds = ( 20 * config.getLong("seconds"));
    long time2 = (thour + tminutes + tseconds);       
   @Override
    public void onEnable(){
       
       config = getConfig();
       getConfig().options().copyDefaults(true);
       saveConfig();
       cfile = new File(getDataFolder(), "config.yml");
       
       Bukkit.getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().shutdown();
            }
        }, config.getLong("time"));
    }
   
    @Override    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        
        if (cmd.getName().equalsIgnoreCase("hubreload")) {
            config = YamlConfiguration.loadConfiguration(cfile);
            sender.sendMessage(ChatColor.GREEN + "Reloaded Hub Restart Config!");
        }
        
        if(cmd.getName().equalsIgnoreCase("hubsettime")) {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.RED + "You didn't specify a time!");
                return true;
            }
            if(args.length > 3) {
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
                return true;
            }
            if(args.length == 3){
            
                Long displaytime = Long.valueOf(args[0]);
                getConfig().set("time", displaytime);
                saveConfig();
                reloadConfig();
                config = YamlConfiguration.loadConfiguration(cfile);
                sender.sendMessage(ChatColor.GREEN + "Time Set To: " + displaytime);
            return true;
            }                        
        }
        
         if(cmd.getName().equalsIgnoreCase("hubtime")) {
             Long displaytime = getConfig().getLong("time");
                //Long displaytime = Long.valueOf(getConfig().getString("time"));
             sender.sendMessage(ChatColor.GREEN + "Configured time: " + displaytime);
             return true;
            }
         
         if(cmd.getName().equalsIgnoreCase("hubtime2")){
             sender.sendMessage(ChatColor.YELLOW + "TESTING NEW TIME: " + time2 );
             return true;
         }
    return true;    
    }
}