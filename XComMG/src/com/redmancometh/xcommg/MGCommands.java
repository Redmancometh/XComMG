package com.redmancometh.xcommg;

import java.util.concurrent.CompletableFuture;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MGCommands implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (args.length == 1)
		{
			GameType type = GameType.getByName(args[0]);
			if (type != null)
			{
				CompletableFuture.runAsync(() ->
				{
					CommString.REQUEST_QUEUE.executeRequest("all", ((Player) sender).getUniqueId(), type);
				});
			}
			else
			{
				printHelp(sender);
			}
		}
		else
		{
			printHelp(sender);
		}
		return true;
	}

	public void printHelp(CommandSender p)
	{
		p.sendMessage(ChatColor.DARK_RED+"Unknown gamemode!");
	}

}
