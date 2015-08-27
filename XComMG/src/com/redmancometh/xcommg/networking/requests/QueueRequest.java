package com.redmancometh.xcommg.networking.requests;

import com.redmancometh.xcommg.networking.MGRequest;


public abstract class QueueRequest extends MGRequest
{
	public QueueRequest(String channel)
	{
		super(channel);
	}
}
