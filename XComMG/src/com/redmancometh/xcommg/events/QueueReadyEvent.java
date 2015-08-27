package com.redmancometh.xcommg.events;
import java.util.List;
import java.util.UUID;

import javafx.util.Pair;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.redmancometh.xcommg.GameType;
public class QueueReadyEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private String arenaName;
    private GameType type;
    private List<Pair<String, UUID>> playerList;
    public QueueReadyEvent(String arenaName, GameType type, List<Pair<String, UUID>> list)
    {
    	this.arenaName=arenaName;
    	this.playerList=list;
    	this.type=type;
    }
    
    public GameType getType()
    {
    	return type;
    }
    
    public List<Pair<String, UUID>> getPlayers()
    {
    	return playerList;
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
	
}
