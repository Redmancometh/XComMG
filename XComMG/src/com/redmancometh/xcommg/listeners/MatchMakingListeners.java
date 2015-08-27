package com.redmancometh.xcommg.listeners;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javafx.util.Pair;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.bukkit.event.EventHandler;
import com.redmancometh.xcom.XCom;
import com.redmancometh.xcom.networking.NetworkMessageOut;
import com.redmancometh.xcommg.CommString;
import com.redmancometh.xcommg.events.QueueReadyEvent;
import com.redmancometh.xcommg.events.QueueRequestEvent;

public class MatchMakingListeners
{
	@EventHandler
	public void onRequest(QueueRequestEvent e)
	{
		
	}

	@EventHandler
	public void onReady(QueueReadyEvent e)
	{
		for (Pair<String, UUID> pair : e.getPlayers())
		{
			CommString.ASK_CONFIRM_READY.executeRequest(pair.getKey(), pair.getValue(), e.getType());
		}
	}

	public static void sendToAllServers(String channel, String msg)
	{
		CompletableFuture.runAsync(() ->
		{
			for (String serverName : XCom.getServerList())
			{
				if (!serverName.equals(XCom.getServerName()))
				{
					Request request = null;
					try
					{
						request = new MessageRequest(serverName, channel, msg);
						NetworkMessageOut.getConnect().request(request).await().getStatusCode();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
	}
}
