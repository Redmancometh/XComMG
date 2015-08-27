package com.redmancometh.xcommg.util;

import java.util.concurrent.CompletableFuture;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResultListener;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NetworkUtil
{
	public static void redirectRequest(String server, final Player player)
	{
		CompletableFuture.runAsync(() ->
		{
			try
			{
				Connect c = getBukkitConnect();
				c.request(new RedirectRequest(server, player.getName())).registerListener(new FutureResultListener<RedirectResult>()
				{
					public void onResult(RedirectResult redirectResult)
					{
						if (redirectResult.getStatusCode() == StatusCode.SUCCESS)
						{
							return;
						}
						player.sendMessage("Could not connect");
					}
				});
			}
			catch (Exception exception)
			{
				player.sendMessage("Could not connect");
			}

		});
	}

	public static Connect getBukkitConnect()
	{
		return (Connect) Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}
}
