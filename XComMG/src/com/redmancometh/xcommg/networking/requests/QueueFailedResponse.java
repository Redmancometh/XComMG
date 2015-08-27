package com.redmancometh.xcommg.networking.requests;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javafx.util.Pair;

import com.redmancometh.xcom.networking.NetworkMessageOut;
import com.redmancometh.xcommg.CommString;
import com.redmancometh.xcommg.GameType;
import com.redmancometh.xcommg.events.AskJoinEvent;
import com.redmancometh.xcommg.networking.MGRequest;

import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.MessageRequest;

public class QueueFailedResponse extends MGRequest
{
	public QueueFailedResponse(String channel)
	{
		super(channel);
	}

	@Override
	public void processResponse(String server, UUID UUID, GameType type)
	{
		CompletableFuture.runAsync(() ->
		{
			try
			{
				CommString.CONFIRM_READY.executeRequest(server, UUID, type);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
	}

	@Override
	public CompletableFuture<Pair<Boolean, Integer>> receive(String server, String channel, String message)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			Player p = super.getPlayer(message);
			if (p != null && p.isOnline())
			{
				return new Pair(true, 1);
			}
			return new Pair(false, 0);
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
