package com.redmancometh.xcom;

import java.util.concurrent.CompletableFuture;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.redmancometh.xcom.databasing.DBUtil;

public class InsertCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender.isOp() && args.length == 3)
		{
			if (args[0].equalsIgnoreCase("server"))
			{
				if (args[1].equalsIgnoreCase("add"))
				{
					CompletableFuture.runAsync(() ->
					{
						DBUtil.addServer(args[2]);
						sender.sendMessage("Server added: "+args[2]);
					});
					return true;
				}
			}
		}
		sender.sendMessage(ChatColor.DARK_RED + "Invalid Command.");
		return false;
	}

}
