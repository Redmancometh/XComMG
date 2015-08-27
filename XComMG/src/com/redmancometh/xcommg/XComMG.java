package com.redmancometh.xcommg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import javafx.util.Pair;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.redmancometh.xcom.XCom;
import com.redmancometh.xcommg.databasing.DBUtil;
import com.redmancometh.xcommg.listeners.CommandListeners;
import com.redmancometh.xcommg.listeners.MobArenaListeners;
import com.redmancometh.xcommg.util.ConcurrentList;

public class XComMG extends JavaPlugin
{
	private static XComMG instance;
	public static Map<String, List<String>> miniGames = new ConcurrentHashMap();
	public static List<String> gameTypes;

	public void onEnable()
	{
		gameTypes = new ConcurrentList(new ArrayList());
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new CommandListeners(), this);
		instance = this;
		DBUtil.createTables();
		CompletableFuture.runAsync(() ->
		{
			try
			{
				miniGames = DBUtil.getMiniGameList().get();
				addMiniGames();
				handleGameTypes();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});

	}

	public void handleGameTypes()
	{
		for (String typeString : gameTypes)
		{
			GameType type = GameType.getByName(typeString);
			if (type != null)
			{
				Pair<Boolean, Listener> typePair = type.tryRegisterListener();
				if (typePair != null && typePair.getKey())
				{
					Bukkit.getPluginManager().registerEvents(typePair.getValue(), this);
				}
			}
			else
			{
				System.out.println("Server registered as unknown gametype!");
			}
		}
	}

	public void addMiniGames()
	{
		for (Entry<String, List<String>> list : miniGames.entrySet())
		{
			for (String s : list.getValue())
			{
				if (s.equalsIgnoreCase(XCom.getServerName()))
				{
					gameTypes.add(list.getKey());
				}
			}
		}
	}

	public void addGameType(String s)
	{
		gameTypes.add(s);
	}

	public void scheduleError(String arena)
	{
		new BukkitRunnable()
		{
			public void run()
			{
				System.out.println(arena + " not found, though this server is supposed to have it!");
			}
		}.runTaskTimer(this, 1, 10);
	}

	public void registerMAEvents(PluginManager pm)
	{
		if (hasMobArena())
		{
			pm.registerEvents(new MobArenaListeners(), this);
		}
		else
		{
			scheduleError("Battle Arena");
		}
	}

	public static XComMG getInstance()
	{
		return instance;
	}

	public boolean hasBattleArena()
	{
		return Bukkit.getPluginManager().getPlugin("BattleArena") == null;
	}

	public boolean hasMobArena()
	{
		return Bukkit.getPluginManager().getPlugin("MobArena") == null;
	}
}
