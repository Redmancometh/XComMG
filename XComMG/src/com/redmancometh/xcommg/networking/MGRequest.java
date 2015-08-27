package com.redmancometh.xcommg.networking;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.redmancometh.xcommg.CommString;
import com.redmancometh.xcommg.GameType;
public abstract class MGRequest
{
	private String channel;
	public MGRequest(String channel)
	{
		this.channel = channel;
	}

	public abstract void executeRequest(String server, UUID UUID, GameType type);

	public String getChannel()
	{
		return channel;
	}

	public void respond(String server, String channel, String message)
	{
		MGRequest request = CommString.getRequest(channel);
		if (request != null)
		{
			String[] rawMessage = message.split(":");
			processResponse(server, UUID.fromString(rawMessage[0]), GameType.getByName(rawMessage[1]));
		}
	}

	public abstract void processResponse(String server, UUID UUID, GameType type);

	public abstract CompletableFuture<Pair<Boolean, Integer>> receive(String server, String channel, String message);

	public static String getPlayerName(String message)
	{
		return message.split(":")[1];
	}

	public static Player getPlayer(String message)
	{
		return Bukkit.getPlayer(getPlayerName(message));
	}

	public static String getUUID(String message)
	{
		return message.split(" ")[1];
	}

	public GameType getGame(String message)
	{
		return GameType.getByName(message.split(":")[0]);
	}
}
