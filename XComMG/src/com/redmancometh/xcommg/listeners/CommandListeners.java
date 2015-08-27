package com.redmancometh.xcommg.listeners;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.redmancometh.xcommg.XComMG;
import com.redmancometh.xcommg.databasing.DBUtil;
public class CommandListeners implements Listener
{
	@EventHandler
	public void onAdd(PlayerCommandPreprocessEvent e)
	{
		if(e.getPlayer().isOp()&&e.getMessage().startsWith("/mgadd"))
		{
			System.out.println("Dun");
			e.setCancelled(true);
			String[] splitString = e.getMessage().split(" ");
			if(splitString.length==3)
			{
				DBUtil.insertGame(splitString[1], splitString[2]);
			}
		}
		else if(e.getPlayer().isOp()&&e.getMessage().startsWith("/gmess"))
		{
			for(Entry<String, List<String>> s : XComMG.miniGames.entrySet())
			{
				System.out.println("GameMode: "+s.getKey());
				for(String string : s.getValue())
				{
					e.getPlayer().sendMessage("Server Name: "+string);
				}
			}
		}
	}
}
