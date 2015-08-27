package com.redmancometh.xcommg.networking.requests;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import javafx.util.Pair;
import com.redmancometh.xcom.networking.NetworkMessageOut;
import com.redmancometh.xcommg.GameType;
import com.redmancometh.xcommg.XComMG;
import com.redmancometh.xcommg.events.AskJoinEvent;
import com.redmancometh.xcommg.networking.MGRequest;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.MessageRequest;

public class ConfirmQueuedNotification extends MGRequest
{
	public ConfirmQueuedNotification(String channel)
	{
		super(channel);
	}

	@Override
	public void processResponse(String server, UUID uuid, GameType type)
	{
		new BukkitRunnable()
		{
			public void run()
			{
				Player p = Bukkit.getPlayer(uuid);
				if (p != null)
				{
					p.sendMessage(ChatColor.GOLD + "[Lava" + ChatColor.RED + "PvP]: " + ChatColor.AQUA + " You have been queued up for "+type.getName()+"!");
				}
			}
		}.runTask(XComMG.getInstance());
	}

	@Override
	public CompletableFuture<Pair<Boolean, Integer>> receive(String server, String channel, String message)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			Player p = super.getPlayer(message);
			if (p != null)
			{
				return new Pair(true, 1);
			}
			else
			{
				return new Pair(false, 0);
			}
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
				request = new MessageRequest(server, super.getChannel(), uuid + ":" + type.getName());
				NetworkMessageOut.getConnect().request(request).await().getStatusCode();
				Bukkit.getPluginManager().callEvent(new AskJoinEvent(type, uuid, true));
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
	}
}
