package com.redmancometh.xcommg.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.redmancometh.xcommg.GameType;

public class QueueRequestEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private GameType type;
    
    public QueueRequestEvent(GameType type)
    {
    	this.type=type;
    }
    
    public GameType getType()
    {
    	return type;
    }
    
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
}
