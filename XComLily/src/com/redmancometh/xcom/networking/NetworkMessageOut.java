package com.redmancometh.xcom.networking;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import lilypad.client.connect.api.Connect;
public class NetworkMessageOut
{
	static Connect connect;

	public static Connect getConnect()
	{
		return Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}

	public static void sendMessage(String message)
	{
		CompletableFuture.runAsync(() ->
		{
			Out.sendToAllServers("chat", message);
		});
	}
	
	public void processMessage()
	{
		
	}
}
