package me.TheL0w3R.AdminOnService;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	List<String> staffMembers = new ArrayList<String>();

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Activating AdminOnService...");
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void onDisable() {
		getLogger().info("Disabling AdminOnService...");
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("serviceon")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "This command has to be executed by a Player. Please, use this command In-Game.");
				return true;
			}
			if (staffMembers.contains(sender.getName())) {
				//DO NOT ADD THE PLAYER TO THE ARRAYLIST.
				sender.sendMessage(ChatColor.RED 
						+ "You're already in the Helper list. Change your helper status back to false with /serviceoff");
				return true;
			} else {
				staffMembers.add(sender.getName());
				sender.sendMessage(ChatColor.AQUA
						+ "You're now on service! Players will ask you for help.");
				return true;
			}
		}
		// -----------------------------------------------------------------------------------------------------------------------------------
		else if (commandLabel.equalsIgnoreCase("checkstaff")) {
			sender.sendMessage(staffMembers.toString());
			return true;
		}
		// -----------------------------------------------------------------------------------------------------------------------------------
		else if (commandLabel.equalsIgnoreCase("addhelper")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED
						+ "You have to type the player who will be added as a helper. (/addhelper <user>)");
			} else {
				staffMembers.add(args[0].toString());
				sender.sendMessage(ChatColor.AQUA + "Player "
						+ args[0].toString() + " was added to the helper list.");
			}
			return true;
		}
		// -----------------------------------------------------------------------------------------------------------------------------------
		else if (commandLabel.equalsIgnoreCase("remhelper")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED
						+ "You have to type the player who will be removed from the Helper list. (/remhelper <user>)");
			} else if (staffMembers.contains(args[0])) {
				staffMembers.remove(args[0].toString());
				sender.sendMessage(ChatColor.GOLD + "Player "
						+ args[0].toString()
						+ " was successfully removed from the helper list!");
			} else if (!(staffMembers.contains(args[0]))) {
				sender.sendMessage(ChatColor.RED + "This player isn't in the Helper list.");
			}
			return true;
		}
		// -----------------------------------------------------------------------------------------------------------------------------------
		else if (commandLabel.equalsIgnoreCase("serviceoff")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "This command has to be executed by a Player. Please, use this command In-Game.");
				return true;
			}
			if (staffMembers.contains(sender)) {
				sender.sendMessage(ChatColor.RED + "You're not in the Helper list.");
				return true;
			} else {
				staffMembers.remove(sender.getName());
				sender.sendMessage(ChatColor.DARK_PURPLE
						+ "You're not on service, players won't be able to ask you for help.");
				return true;
			}
		}
		// -----------------------------------------------------------------------------------------------------------------------------------
		return false;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (staffMembers.toString() == "[]") {
			p.sendMessage(ChatColor.RED + "There are no Admins on service");
		} else {
			p.sendMessage(ChatColor.GREEN + "There are Admins on service!");			
		}
	}
}