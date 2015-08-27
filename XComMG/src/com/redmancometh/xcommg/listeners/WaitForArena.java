package com.redmancometh.xcommg.listeners;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import mc.alk.arena.BattleArena;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.redmancometh.xcommg.XComMG;

public class WaitForArena implements Listener
{
	private Map<UUID, String> waitingFor = new ConcurrentHashMap();
	
	@EventHandler
	public void onLogin(PlayerJoinEvent e)
	{
		String arenaName = waitingFor.get(e.getPlayer().getUniqueId());
		if(arenaName==null)
		{
		}
		new BukkitRunnable()
		{
			int x = 0;
			public void run()
			{
				if(x>5)
				{
					this.cancel();
					kickPlayer(e.getPlayer());
				}
			}
		}.runTaskTimer(XComMG.getInstance(), 1, 40);
		BattleArena.getArena(arenaName);
	}
	
	public void kickPlayer(Player p)
	{
		p.kickPlayer(ChatColor.DARK_RED+"There was an error with your arena queue!");
		return;
	}
}
