package com.redmancometh.xcommg.networking.requests;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javafx.util.Pair;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.redmancometh.xcom.networking.NetworkMessageOut;
import com.redmancometh.xcommg.GameType;
import com.redmancometh.xcommg.events.AskJoinEvent;
import com.redmancometh.xcommg.networking.MGRequest;
import com.redmancometh.xcommg.util.NetworkUtil;

import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.MessageRequest;

public class AskJoinRequest extends MGRequest
{
	public AskJoinRequest(String channel)
	{
		super(channel);
	}

	@Override
	public CompletableFuture<Pair<Boolean, Integer>> receive(String server, String channel, String message)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			Bukkit.getPluginManager().callEvent(new AskJoinEvent(super.getGame(message), UUID.fromString(super.getUUID(message)), false));
			Player p = super.getPlayer(message);
			if (p != null && p.isOnline())
			{
				return new Pair(true, 5);
			}
			else
			{
				return new Pair(false, 0);
			}
		});
	}

	@Override
	public void processResponse(String from, UUID uuid, GameType type)
	{
		Player p = Bukkit.getPlayer(uuid);
		if (p != null)
		{
			NetworkUtil.redirectRequest(from, p);
		}
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
