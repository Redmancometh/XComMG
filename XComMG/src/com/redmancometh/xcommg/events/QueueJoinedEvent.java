package com.redmancometh.xcommg.events;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
public class QueueJoinedEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private String arenaName;
    private String uuid;
    public QueueJoinedEvent(String arenaName, String uuid)
    {
    	this.arenaName=arenaName;
    	this.uuid=uuid;
    }
    
    
    public String getArenaName()
    {
    	return arenaName;
    }
    
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}


	public String getUUID()
	{
		return uuid;
	}
	
}
