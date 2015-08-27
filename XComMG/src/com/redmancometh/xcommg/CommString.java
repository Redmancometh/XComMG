package com.redmancometh.xcommg;

import java.util.UUID;

import com.redmancometh.xcommg.networking.MGRequest;
import com.redmancometh.xcommg.networking.requests.AskConfirmReadyRequest;
import com.redmancometh.xcommg.networking.requests.AskJoinRequest;
import com.redmancometh.xcommg.networking.requests.ConfirmQueuedNotification;
import com.redmancometh.xcommg.networking.requests.ConfirmReadyRequest;
import com.redmancometh.xcommg.networking.requests.QueueFailedResponse;
import com.redmancometh.xcommg.networking.requests.QueueJoinRequest;

public enum CommString
{
	CONFIRM_QUEUED(new ConfirmQueuedNotification("player_queued")), CONFIRM_READY(new ConfirmReadyRequest("ready_confirmed")),
	ASK_CONFIRM_READY(new AskConfirmReadyRequest("ask_confirm_ready")), REQUEST_JOIN(new AskJoinRequest("request_join")),
	REQUEST_QUEUE(new QueueJoinRequest("request_queue")), QUEUE_FAILED(new QueueFailedResponse("queue_failed"));
	private MGRequest request;

	CommString(MGRequest request)
	{
		this.request = request;
	}

	public String getChannel()
	{
		return request.getChannel();
	}

	public void executeRequest(String server, UUID uuid, GameType type)
	{
		getRequest(this.getChannel()).executeRequest(server, uuid, type);
	}

	public static MGRequest getRequest(String channelName)
	{
		for (CommString requestString : CommString.values())
		{
			if (requestString.getChannel().equals(channelName))
			{
				return requestString.request;
			}
		}
		return null;
	}
}
