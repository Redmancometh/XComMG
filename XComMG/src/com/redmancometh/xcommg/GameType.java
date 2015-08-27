package com.redmancometh.xcommg;

import java.util.List;

import javafx.util.Pair;

import org.bukkit.event.Listener;

import com.redmancometh.xcommg.listeners.ArenaListeners;
import com.redmancometh.xcommg.listeners.MobArenaListeners;

public enum GameType
{
	MOB_ARENA("mobarena", MobArenaListeners.class), ONE_VS_ONE("1v1", ArenaListeners.class), TWO_VS_TWO("2v2", ArenaListeners.class), CTF("ctf", ArenaListeners.class), FFA("ffa", ArenaListeners.class), NONE("NONE", null);
	private String name;
	private Class<? extends Listener> listener;
	private static List<Listener> listenersRegistered;
	GameType(String name, Class<? extends Listener> listener)
	{
		this.name=name;
		this.listener=listener;
	}
	
	public Pair<Boolean, Listener> tryRegisterListener()
	{
		for(Listener listenerTwo : listenersRegistered)
		{
			if(this.getListenerClass().isInstance(listenerTwo))
			{
				return new Pair(false, null);
			}
		}
		return new Pair(true, this.listener);
	}
	
	public static GameType getByName(String name)
	{
		for(GameType type : GameType.values())
		{
			if(type.getName().equalsIgnoreCase(name))
			{
				return type;
			}
		}
		return null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Class<? extends Listener> getListenerClass()
	{
		return listener;
	}

}
