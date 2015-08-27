package com.redmancometh.xcom.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import StrongChat.ChatUtil;

import com.redmancometh.xcom.events.MessageReceivedEvent;

public class ServerMessageListener implements Listener
{
	@EventHandler
	public void onMessageReceived(MessageReceivedEvent e)
	{
		System.out.println("Message Received, now constructing and sending");
		ChatUtil.sendToAllPlayers(ChatUtil.constructComp(e.getRawMessage(), e.getRawHoverText(), e.getRawClickText()));
	}
}
