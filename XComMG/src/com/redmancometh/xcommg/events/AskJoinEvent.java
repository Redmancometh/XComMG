package com.redmancometh.xcommg.events;
import java.util.UUID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.redmancometh.xcommg.GameType;
public class AskJoinEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private GameType type;
	private UUID uuid;
	private boolean sending;
	public AskJoinEvent(GameType type, UUID uuid, boolean sending)
	{
		this.sending=sending;
		this.uuid=uuid;
		this.type=type;
	}

	public GameType getGameType()
	{
		return type;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public boolean sender()
	{
		return sending;
	}

}
