package com.redmancometh.xcommg.networking.requests;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import javafx.util.Pair;
import com.redmancometh.xcom.networking.NetworkMessageOut;
import com.redmancometh.xcommg.GameType;
import com.redmancometh.xcommg.XComMG;
import com.redmancometh.xcommg.events.QueueReadyEvent;
import com.redmancometh.xcommg.networking.MGRequest;
import com.redmancometh.xcommg.util.ConcurrentList;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.MessageRequest;
import mc.alk.arena.BattleArena;
import mc.alk.arena.objects.arenas.Arena;

public class QueueJoinRequest extends MGRequest
{
	private String channel;
	private static Map<String, List<Pair<String, UUID>>> arenaQueue = new ConcurrentHashMap(); //String is arena name
	static
	{
		initMapLists();
	}
	
	public static void initMapLists()
	{
		for(String type : XComMG.miniGames.keySet())
		{
			arenaQueue.put(type, new ConcurrentList(new ArrayList()));
		}
	}
	
	public QueueJoinRequest(String channel)
	{
		super(channel);
	}

	@Override
	public CompletableFuture<Pair<Boolean, Integer>> receive(String server, String channel, String message)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			Arena chosen = null;
			for (int x = 0; x < 5; x++)
			{
				String arenaName = super.getGame(message) + "-" + x;
				Arena a = BattleArena.getArena(arenaName);
				if (a == null)
				{
					if(chosen!=null)
					{
						return new Pair(true, 1);
					}
				}
				int minSize = a.getParams().getMinPlayers();
				int maxSize = a.getParams().getMaxPlayers();
				int inQueue = arenaQueue.get(arenaName).size();
				if (inQueue < maxSize && inQueue >= minSize)
				{

					arenaQueue.get(arenaName).add(new Pair(server, super.getUUID(message)));
					chosen=a;
					Bukkit.getPluginManager().callEvent(new QueueReadyEvent(arenaName, super.getGame(message), arenaQueue.get(arenaName)));
				}
				else if (inQueue < maxSize)
				{
					chosen=a;
				}
			}
			return new Pair(false, 0);
		});
	}

	@Override
	public void processResponse(String server, UUID uuid, GameType type)
	{
		//Receiving something like "survival, "request_queue", "uuid:ctf"
		//Survival requests the player with uuid queue for ctf
		CompletableFuture.runAsync(() ->
		{
			
		});
	}

	@Override
	public void executeRequest(String server, UUID uuid, GameType type)
	{
		CompletableFuture.runAsync(() ->
		{
			Request request;
			try
			{
				for (String to : XComMG.miniGames.get(type.getName()))
				{
					request = new MessageRequest(to, channel, uuid + ":" + type.getName());
					NetworkMessageOut.getConnect().request(request).await().getStatusCode();
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
	}

}
