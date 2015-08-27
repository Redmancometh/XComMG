package com.redmancometh.xcom;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lilypad.client.connect.api.Connect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.redmancometh.xcom.databasing.DBUtil;
import com.redmancometh.xcom.databasing.DataSource;
import com.redmancometh.xcom.listeners.ChatMessageListener;
import com.redmancometh.xcom.listeners.ServerMessageListener;
import com.redmancometh.xcom.networking.NetworkMessageListener;
import com.redmancometh.xcom.util.CfgManager;

public class XCom extends JavaPlugin
{
	private NetworkMessageListener listener = new NetworkMessageListener();
	private static String serverName;
	private static DataSource dataSource;
	private static XCom instance;
	private static List<String> serverList;

	public void onEnable()
	{
		instance = this;
		this.getCommand("xcom").setExecutor(new InsertCommand());
		File configFile = new File(this.getDataFolder(), "config.yml");
		if (!configFile.exists())
		{
			this.saveDefaultConfig();
		}
		CfgManager manager = new CfgManager(this.getConfig());
		try
		{
			XCom.dataSource = new DataSource(manager.getDBUser(), manager.getDBPass(), manager.getDBName());
			XCom.serverList = DBUtil.getServerNames().get();
		}
		catch (IOException | SQLException | PropertyVetoException | InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ChatMessageListener(), this);
		pm.registerEvents(new ServerMessageListener(), this);
		Connect connect = this.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
		serverName = connect.getSettings().getUsername();
		connect.registerEvents(listener);
	}

	public void onDisable()
	{
		Connect connect = this.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
		connect.unregisterEvents(listener);
	}

	public static String getServerName()
	{
		return serverName;
	}

	public Connection getConnection()
	{
		try
		{
			return dataSource.getConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static XCom getInstance()
	{
		return instance;
	}

	public static List<String> getServerList()
	{
		return serverList;
	}
}
