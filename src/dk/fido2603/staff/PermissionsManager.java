package dk.fido2603.staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;

@SuppressWarnings("unused")
public class PermissionsManager
{
	private String pluginName = "null";
	private PluginManager pluginManager = null;
	private Staff plugin;
	private GroupManager groupManager = null;

	public PermissionsManager(Staff p)
	{
		this.plugin = p;
	}

	public void load()
	{
		// Detect what permission plugin is used on this server and setup accordingly
		pluginManager = plugin.getServer().getPluginManager();
	

		if (pluginManager.getPlugin("GroupManager") != null)
		{
			plugin.log("Using GroupManager");
			pluginName = "GroupManager";
			groupManager = ((GroupManager) pluginManager.getPlugin("GroupManager"));
		}
	}


	public boolean hasPermission(Player player, String node)
	{
		return false;
	}
}