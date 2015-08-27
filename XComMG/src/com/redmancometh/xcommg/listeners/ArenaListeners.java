package com.redmancometh.xcommg.listeners;
import org.bukkit.event.Listener;

import mc.alk.arena.BattleArena;

import com.redmancometh.xcommg.events.QueueRequestEvent;
public class ArenaListeners implements Listener
{
	
	public void onQueue(QueueRequestEvent e)
	{
		switch (e.getType())
		{
		case ONE_VS_ONE:
			break;
		case TWO_VS_TWO:
			break;
		case FFA:
			break;
		case CTF:
			break;
		default:
			return;
		}
	}

	public void twoVtwo()
	{

	}

	public void FFA()
	{

	}
	
	public void CTF()
	{
		BattleArena.getArena("ctf");
	}

}
