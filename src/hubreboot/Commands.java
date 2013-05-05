/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hubreboot;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Frost
 */
public class Commands extends JavaPlugin implements CommandExecutor {

    Commands(HubReboot aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        FileConfiguration config;
        File cfile;
        config = getConfig();
        cfile = new File(getDataFolder(), "config.yml");
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
                sender.sendMessage(ChatColor.GREEN + "Time Set To: " + ChatColor.BLUE + hour + " Hours " + minute + " Minutes " + second + " Seconds OR " + displaytime + " Ticks");
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
             sender.sendMessage(ChatColor.GREEN + "Configured time: " + ChatColor.BLUE + hours + " Hours " + minutes + " Minutes " + seconds + " Seconds OR " + displaytime + " ticks");
             return true;
            }
         return false;
    }
    
}
