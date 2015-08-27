package com.redmancometh.xcom.networking;

import java.util.concurrent.CompletableFuture;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.MessageRequest;
import com.redmancometh.xcom.XCom;

public class Out
{
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

	public static void sendPM(String msg, String name)
	{
		CompletableFuture.runAsync(() ->
		{
			sendToAllServers("privatemsg", name + "æ" + msg);
		});
	}

	public static void sendOffline(String name)
	{
		CompletableFuture.runAsync(() ->
		{
			sendToAllServers("useroffline", name);
		});
	}

}
