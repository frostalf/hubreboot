/*
 * @author SithSpawn_1
 * @Copyright 2013
 */
package hubreboot;

import java.io.File;
import java.text.MessageFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

       
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
        if(!sender.hasPermission("hubreboot.reload")){
                sender.sendMessage(MessageFormat.format("{0}You Don't have access to this command!", ChatColor.RED));
                return true;
            }
            long thour = (20 * 60 * 60);
            long tminutes = (20 * 60);
            long tseconds = (20);
        if (cmd.getName().equalsIgnoreCase("hubreload")) {
            config = YamlConfiguration.loadConfiguration(cfile);
            sender.sendMessage(MessageFormat.format("{0}Reloaded Hub Restart Config!", ChatColor.GREEN));
        }
        
        if(cmd.getName().equalsIgnoreCase("hubsettime")) {
            if(!sender.hasPermission("hubreboot.set")){
                sender.sendMessage(MessageFormat.format("{0}You Don't have access to this command!", ChatColor.RED));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(MessageFormat.format("{0}You didn't specify a time! Need 3 arguments(hours, minutes, seconds)", ChatColor.RED));
                return true;
            }
            if(args.length > 3) {
                sender.sendMessage(MessageFormat.format("{0}Too many arguments! Only need 3(hours, minutes, seconds)", ChatColor.RED));
                return true;
            }
            if((args.length > 0 ) && (args.length < 3)){
                sender.sendMessage(MessageFormat.format("{0}Too few arguements! Need 3 arguments(hours, minutes, seconds", ChatColor.RED));
                return true;
            }
            if(args.length == 3){
                long hour = Long.valueOf(args[0]);
                long minute = Long.valueOf(args[1]);
                long second = Long.valueOf(args[2]);
                long thour2 = (thour * Long.valueOf(args[0]));
                long tminutes2 = (tminutes * Long.valueOf(args[1]));
                long tseconds2 = (tseconds * Long.valueOf(args[2]));
                Long displaytime = (thour2 + tminutes2 + tseconds2);
                getConfig().set("hours", hour);
                getConfig().set("minutes", minute);
                getConfig().set("seconds", second);
                saveConfig();
                reloadConfig();
                
                config = YamlConfiguration.loadConfiguration(cfile);
                sender.sendMessage(MessageFormat.format("{0}Time Set To: {1}{2} Hours {3} Minutes {4} Seconds OR {5} Ticks", ChatColor.GREEN, ChatColor.BLUE, hour, minute, second, displaytime));
                sender.sendMessage(MessageFormat.format("{0}Please type /hubreload to apply changes", ChatColor.RED));
            return true;
            }                        
        }
        
            if(cmd.getName().equalsIgnoreCase("hubtime")) {
                if(!sender.hasPermission("hubreboot.view")){
                sender.sendMessage(MessageFormat.format("{0}You Don't have access to this command!", ChatColor.RED));
                return true;
            }
             long hours = getConfig().getLong("hours");
             long minutes = getConfig().getLong("minutes");
             long seconds = getConfig().getLong("seconds");
             long displaytime = ((20 * 60 *60 * hours) + (20 *60 * minutes) + (20 * seconds));
             sender.sendMessage(MessageFormat.format("{0}Configured time: {1}{2} Hours {3} Minutes {4} Seconds OR {5} ticks", ChatColor.GREEN, ChatColor.BLUE, hours, minutes, seconds, displaytime));
             return true;
            }
         return true;
    }   

   
   
}