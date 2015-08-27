package com.redmancometh.xcom.util;

import org.bukkit.configuration.file.FileConfiguration;

public class CfgManager
{
	FileConfiguration config;
	public CfgManager(FileConfiguration config)
	{
		this.config = config;
	}

	public String getDBUser()
	{
		return config.getString("ServerDB.Login");
	}

	public String getDBPass()
	{
		return config.getString("ServerDB.Password");
	}

	public String getDBName()
	{
		return config.getString("ServerDB.Database");
	}
}
