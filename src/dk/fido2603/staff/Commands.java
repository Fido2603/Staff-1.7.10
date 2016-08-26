package dk.fido2603.staff;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dk.fido2603.staff.Staff;

@SuppressWarnings("unused")
public class Commands 
{
	private Staff	plugin;

	Commands(Staff p)
	{
		this.plugin = p;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	//'CommandSender sender' - who sent the command
	//'Command cmd' - the command that was executed
	//'String Label' - the command alias that was used
	//String[] args - an array of additional arguments, e.g. typing /hello abc def would put abc in args[0], and def in args[1]
	{
		Player player = null;
		
		if (sender instanceof Player)
		{
			player = (Player) sender;			
		}

		// Check that the command is something we want to handle at all
		if (!cmd.getName().equalsIgnoreCase("staff"))
		{
			return false;
		}
					
		// If the player is null, it means the command comes from the server console
		if (player == null)
		{
			if (args.length == 1)
			{
				if(args[0].equalsIgnoreCase("reload"))
				{
					CommandReload(player);
					plugin.log(sender.getName() + " /staff reload");

					return true;
				}
			}

			return true;
		}
		
		if (player == null)
		{
			if (args.length == 1)
			{
				if(args[0].equalsIgnoreCase("help"))
				{
					CommandHelp(player);
					plugin.log(sender.getName() + " /staff help");

					return true;
				}
			}

			return true;
		}
		
		if (player == null)
		{
			if (args.length == 1)
			{
				if(args[0].equalsIgnoreCase("info"))
				{
					CommandInfo(player);
					plugin.log(sender.getName() + " /staff info");

					return true;
				}
			}

			return true;
		}
		
		if (player == null)
		{
			if (args.length == 1)
			{
				if(args[0].equalsIgnoreCase("add"))
				{
					CommandAddMissing(sender);
					plugin.log(sender.getName() + " /staff add");

					return true;
				}
			}

			return true;
		}
		
		if (args.length == 1)
		{
			if(args[0].equalsIgnoreCase("add"))
			{
				CommandAddMissing(sender);
				plugin.log(sender.getName() + " /staff add");

				return true;
			}
		}
		
		if (args.length == 2)
		{
			if(args[0].equalsIgnoreCase("add"))
			{
				CommandAdd(sender, cmd, label, args);
				plugin.log(sender.getName() + " /staff add <something>");

				return true;
			}
		}
		
		if (player == null)
		{
			if (args.length == 2)
			{
				if(args[0].equalsIgnoreCase("add"))
				{
					CommandAdd(sender, cmd, label, args);
					plugin.log(sender.getName() + " /staff add <something>");
	
					return true;
				}
			}
		}

		// User has just written /Staff command and nothing else 
		if (args.length == 0)
		{
			CommandStaff(player, sender, cmd, label, args);
			plugin.log(sender.getName() + " /staff");
			return true;	
		}
		
		// User has written /Staff <something> 
		if (args.length == 1)
		{
			switch(args[0].toLowerCase())
			{
				case "info" : CommandInfo(player); break;
			    case "help" : CommandHelp(player); break;
				case "reload" : CommandReload(player); break;
				default : player.sendMessage(ChatColor.RED + "Invalid Staff command");				
			}
			
			return true;
		}
		
		sender.sendMessage(ChatColor.RED + "Too many arguments!");
		return true;		
	}
	
	//Main command
    public void CommandStaff(Player player, CommandSender sender, Command command, String label, String[] args) 
    {
    	{
    		if (player.hasPermission("staff.staff"))
    		{
		        // On command send the rules from config.yml to the sender of the command
		        List<String> staffList = plugin.getConfig().getStringList("staff");
		        for (String s : staffList){
		            sender.sendMessage(s);
		        }
		        return;		        
		    }
		else
		{
			player.sendMessage(ChatColor.RED + "You do not have permission for this command!");
		}}
	}
	
	//Shows some info
	public void CommandInfo(Player player)
	{
		if (player.hasPermission("staff.info"))
		{
			// Show some info
			// like
			player.sendMessage(ChatColor.YELLOW + "--------------- Staff-1.7.10 V0.0.1 ---------------");
			player.sendMessage(ChatColor.AQUA + "By Fido2603");
			player.sendMessage("");
			player.sendMessage(ChatColor.AQUA + "This Plugin is awesome!");
		}
		else
		{
			player.sendMessage(ChatColor.RED + "You do not have permission for this command!");
		}
	}
	
	//Shows help
	public boolean CommandHelp(Player player)
	{
		if (player.hasPermission("staff.help"))
		{
			if (player == null)
			{
				this.plugin.log(ChatColor.WHITE + "/staff" + ChatColor.AQUA + " - Shows staff!");
			}
			else
			{
				player.sendMessage(ChatColor.YELLOW + "---------- " + this.plugin.getDescription().getFullName() + " ----------");
	
				//Permission handling
				if (player.hasPermission("staff.help"))
				{
					player.sendMessage(ChatColor.AQUA + "/staff help" + ChatColor.WHITE + " - Shows help");
				}
				if (player.hasPermission("staff.info"))
				{
					player.sendMessage(ChatColor.AQUA + "/staff info" + ChatColor.WHITE + " - Shows info about the plugin");
				}
				if (player.hasPermission("staff.reload"))
				{
					player.sendMessage(ChatColor.AQUA + "/staff reload" + ChatColor.WHITE + " - Reloads the plugin");
				}
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "You do not have permission for this command!");
		}
		
		return true;
	}	
	
	//Reloads the config
	public void CommandReload(Player player)
	{
		if (player.hasPermission("staff.reload"))
		{
			plugin.getConfig();
			plugin.saveConfig();
			plugin.reloadConfig();
			
			if (player == null)
			{
				this.plugin.log(this.plugin.getDescription().getFullName() + ": Reloaded configuration.");
			}
			else
			{
				player.sendMessage(ChatColor.YELLOW + this.plugin.getDescription().getFullName() + ": " + ChatColor.WHITE + "Reloaded configuration.");
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "You do not have permission for this command!");
		}
	}
	
    public boolean CommandAdd(CommandSender sender, Command cmd, String label, String [] args) 
    {
        if(sender instanceof Player) {
        Player player = (Player)sender;
		{
			if (player.hasPermission("staff.add"))
			{
				
				if (player == null)
				{
					this.plugin.log(this.plugin.getDescription().getFullName() + ": Please type the command from ingame..");
				}
				else
				{		             
							@SuppressWarnings("unchecked")
							List<String> staffList = (List<String>)plugin.getConfig().getList("staff");
							staffList.add(args[1]);
							plugin.getConfig().set("staff", staffList);
							player.sendMessage(ChatColor.YELLOW + this.plugin.getDescription().getFullName() + ": " + ChatColor.WHITE + "Saving the config...");
	                        plugin.saveConfig();
	        				player.sendMessage(ChatColor.RED + this.plugin.getDescription().getFullName() + ": " + ChatColor.WHITE + "Reloading the config, to make your new staff show up.");
	                        plugin.reloadConfig();
	                        player.sendMessage("Done");
	                        return true;		            
				}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission for this command!");
				}
			}
        }
        return false;
	}
    
    public boolean CommandAddMissing(CommandSender sender) 
    {
        if(sender instanceof Player) {
        Player player = (Player)sender;
		{
			if (player.hasPermission("staff.add"))
			{
				
				if (player == null)
				{
					this.plugin.log(this.plugin.getDescription().getFullName() + ": Please type the command from ingame..");
				}
				else
				{		             
							player.sendMessage(ChatColor.RED + "Missing arguments..");
							player.sendMessage(ChatColor.RED + "Please use /staff add <user>");	
	                        return true;		            
				}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission for this command!");
				}
			}
        }
        return false;
	}

	//Doesn't do shit
	private void CommandStaffList(CommandSender sender)
	{
	}
}