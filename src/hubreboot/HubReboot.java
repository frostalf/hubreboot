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
                sender.sendMessage(ChatColor.RED + "You Don't have access to this command!");
                return true;
            }
            long thour = (20 * 60 * 60);
            long tminutes = (20 * 60);
            long tseconds = (20);
        if (cmd.getName().equalsIgnoreCase("hubreload")) {
            config = YamlConfiguration.loadConfiguration(cfile);
            sender.sendMessage(ChatColor.GREEN + "Reloaded Hub Restart Config!");
        }
        
        if(cmd.getName().equalsIgnoreCase("hubsettime")) {
            if(!sender.hasPermission("hubreboot.set")){
                sender.sendMessage(ChatColor.RED + "You Don't have access to this command!");
                return true;
            }
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
                sender.sendMessage(ChatColor.GREEN + "Time Set To: " + ChatColor.BLUE + "Hours: " + hour + " Minutes: " + minute + " Seconds: " + second + " OR " + displaytime + " Ticks");
                sender.sendMessage(ChatColor.RED + "Please type /hubreload to apply changes");
            return true;
            }                        
        }
        
            if(cmd.getName().equalsIgnoreCase("hubtime")) {
                if(!sender.hasPermission("hubreboot.view")){
                sender.sendMessage(ChatColor.RED + "You Don't have access to this command!");
                return true;
            }
             long hours = getConfig().getLong("hours");
             long minutes = getConfig().getLong("minutes");
             long seconds = getConfig().getLong("seconds");
             long displaytime = ((20 * 60 *60 * hours) + (20 *60 * minutes) + (20 * seconds));
             sender.sendMessage(ChatColor.GREEN + "Configured time: Hours: " + hours + " Minutes: " + minutes + " Seconds: " + seconds + " OR " + displaytime + " ticks");
             return true;
            }
         return true;
    }
}