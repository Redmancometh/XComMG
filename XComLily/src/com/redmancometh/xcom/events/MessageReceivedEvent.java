package com.redmancometh.xcom.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class MessageReceivedEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private String message;
	private String hoverText;
	private String clickText;

	public MessageReceivedEvent(String message, String hoverText, String clickText)
	{
		this.hoverText = hoverText;
		this.clickText = clickText;
		this.message = message;
		System.out.println("constructed!");
	}

	public String getRawClickText()
	{
		return this.clickText;
	}

	public String getRawHoverText()
	{
		return this.hoverText;
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
}
