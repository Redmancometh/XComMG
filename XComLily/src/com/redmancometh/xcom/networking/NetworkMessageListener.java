package com.redmancometh.xcom.networking;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import StrongChat.ChatUtil;

import com.redmancometh.xcom.events.MessageReceivedEvent;

import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;

public class NetworkMessageListener
{
	@EventListener
	public void onMessage(MessageEvent messageEvent)
	{
		// String channel = messageEvent.getChannel();
		try
		{
			if (messageEvent.getChannel().equalsIgnoreCase("chat"))
			{
				String[] rawMsg = messageEvent.getMessageAsString().split("æ");
				Bukkit.getPluginManager().callEvent(new MessageReceivedEvent(rawMsg[0], rawMsg[2], rawMsg[1]));
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	@EventListener
	public void onPMNotOnline(MessageEvent messageEvent)
	{
		try
		{
			if (messageEvent.getChannel().equalsIgnoreCase("useroffline"))
			{
				Player sendTo = Bukkit.getPlayer(messageEvent.getMessageAsString());
				sendTo.sendMessage(ChatColor.DARK_RED + "Sorry, the user you tried to PM is offline!");
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	@EventListener
	public void onPM(MessageEvent messageEvent)
	{
		try
		{
			if (messageEvent.getChannel().equalsIgnoreCase("privatemsg"))
			{
				String[] rawMsg = messageEvent.getMessageAsString().split("æ");
				String name = rawMsg[0];
				String msg = rawMsg[1];
				String clickText = rawMsg[2];
				String hoverText = rawMsg[3];
				Player sendTo = Bukkit.getPlayer(name);
				if (sendTo != null)
				{
					sendTo.spigot().sendMessage(ChatUtil.constructComp(msg, hoverText, clickText));
				}
				else
				{
					System.out.println(name+" Failed");
					Out.sendOffline(name);
				}
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
}
