package com.redmancometh.xcommg.listeners;
import java.util.concurrent.CompletableFuture;
import com.redmancometh.xcommg.CommString;
import com.redmancometh.xcommg.networking.MGRequest;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
public class NetworkListeners
{
	@EventListener
	public void onMessage(MessageEvent messageEvent)
	{
		CompletableFuture.runAsync(() ->
		{
			MGRequest request = CommString.getRequest(messageEvent.getChannel());
			if (request != null)
			{
				try
				{
					String server = messageEvent.getSender();
					String channel = messageEvent.getSender();
					String message = messageEvent.getSender();
					request.respond(server, channel, message);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
