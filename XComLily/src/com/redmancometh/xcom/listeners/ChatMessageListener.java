package com.redmancometh.xcom.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.redmancometh.xcom.XCom;
import com.redmancometh.xcom.networking.NetworkMessageOut;
import com.redmancometh.xcom.networking.Out;

import StrongChat.ChatUtil;
import StrongChat.StrongChatEvent;

public class ChatMessageListener implements Listener
{
	@EventHandler
	public void onChat(StrongChatEvent e)
	{
		NetworkMessageOut.sendMessage(e.getMessage() + "æ" + e.getClickText() + "æ" + e.getHoverText());
	}

	@EventHandler
	public void onMsg(PlayerCommandPreprocessEvent e)
	{
		if (e.getMessage().toLowerCase().startsWith("/msg"))
		{
			String[] rawMsg = e.getMessage().split(" ");
			if (rawMsg.length > 2)
			{
				Player sendTo = Bukkit.getPlayer(rawMsg[1]);
				if (sendTo == null)
				{
					e.setCancelled(true);
					String message = ChatColor.GOLD+"[me -> "+rawMsg[1]+ChatColor.GOLD+"] "+ChatColor.WHITE+constructMessage(rawMsg);
					String outMsg = ChatColor.GOLD+"["+e.getPlayer().getDisplayName() +" -> me]"+ChatColor.GOLD+"] "+ChatColor.WHITE+constructMessage(rawMsg);
					String clickText = "/msg " + e.getPlayer().getName();
					String serverName = XCom.getServerName();
					String hoverText = ChatColor.AQUA + "Real Name: " + ChatColor.GOLD + e.getPlayer().getName() + "\n" + ChatColor.GOLD + "Server: " + ChatColor.AQUA + serverName.substring(0, 1).toUpperCase()+serverName.substring(1, serverName.length());
					e.getPlayer().spigot().sendMessage(ChatUtil.constructComp(message, hoverText, clickText));
					Out.sendPM(outMsg + "æ"+clickText +"æ"+ hoverText, rawMsg[1]);
				}
			}
		}
	}

	public String constructMessage(String[] rawMsg)
	{
		String message = "";
		for (int x = 2; x < rawMsg.length; x++)
		{
			if (x == 2)
			{
				message += " "+ rawMsg[x] + " ";
			}
			else
			{
				message += rawMsg[x] + " ";
			}
		}
		return message;
	}
}
