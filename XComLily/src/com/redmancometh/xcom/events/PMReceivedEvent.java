package com.redmancometh.xcom.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class PMReceivedEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private String message;
	private String player;
	
	public PMReceivedEvent(String message, String player)
	{
		this.setPlayer(player);
		this.message = message;
	}

	public String getRawMessage()
	{
		return message;
	}

	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	public String getPlayer()
	{
		return player;
	}

	public void setPlayer(String player)
	{
		this.player = player;
	}
}
