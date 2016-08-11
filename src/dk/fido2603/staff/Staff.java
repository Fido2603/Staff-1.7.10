package dk.fido2603.staff;
// This is the package name

// This is the imported libraries
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.bukkit.command.*;
import org.bukkit.event.*;


// This is the main class of the plugin.
@SuppressWarnings("unused")
public class Staff extends JavaPlugin
{	
	private boolean debug = false;
	
	// The config is the file configuration stored in config.yml
	private FileConfiguration config = null;
	
	// The console is just a shortcut for pretty-printing text to the server console
	private ConsoleCommandSender console;
	
	// This is the servers name. The server owner should change this to his servers name
	private String serverName = "Your Server"; 

	// The command class handles all the command. We dont want too much code in this file
	private Commands commands	= null;
	
	ConsoleCommandSender consoles = Bukkit.getConsoleSender();
	
	private PermissionsManager permissionsManager = null;
	
	private Staff Title = null;

	public PermissionsManager getPermissionsManager()
	{
		return this.permissionsManager;
	}
	
	// This gets triggered once when the server starts
	@Override
	public void onEnable()
	{
        // Save a copy of the default config.yml if one is not there
        this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();

		// Make a command class 
		this.commands = new Commands(this);
		
		this.console = this.getServer().getConsoleSender();

		//this sends the message "[Staff] Lets get going WO" when the server starts
		consoles.sendMessage(ChatColor.DARK_AQUA + "[Staff] Started without failure!");

		// This defines the variable pm to be getServer.getPluginManager(). it is not needed but if you have lots of classes its nice to just use pm instead of getServer.getPluginManager()
		PluginManager pm = getServer().getPluginManager();
	}
	
	// This gets triggered once when the server closes
	public void onDisabled()
	{
		//this sends the message "[Staff] Oh no, don't leave me..."  when the server stops
		consoles.sendMessage(ChatColor.BLUE + "[Staff] Oh no, don't leave me...");			
	}
	
	public void log(String message)
	{
		console.sendMessage("[" + getDescription().getFullName() + "] " + message);
	}

	public void logDebug(String message)
	{
		if (this.debug)
		{
			console.sendMessage("[" + getDescription().getFullName() + "] " + message);
		}
	}

	public void reloadSettings()
	{
		reloadConfig();

		loadSettings();
	}

	public void loadSettings()
	{
		this.serverName = config.getString("Settings.ServerName", "Your Server");		
		this.debug = config.getBoolean("Settings.Debug", false);
	}

	public void saveSettings()
	{
		config.set("Settings.ServerName", this.serverName);

		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	//'CommandSender sender' - who sent the command
	//'Command cmd' - the command that was executed
	//'String Label' - the command alias that was used
	//String[] args - an array of additional arguments, e.g. typing /hello abc def would put abc in args[0], and def in args[1]
	{
		//this will send the parameters to the commands class
		return this.commands.onCommand(sender, cmd, label, args);
	}		
}

