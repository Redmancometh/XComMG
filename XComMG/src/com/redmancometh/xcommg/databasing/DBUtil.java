package com.redmancometh.xcommg.databasing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.redmancometh.xcom.XCom;

public class DBUtil
{
	public static void createTables()
	{
		CompletableFuture.runAsync(() ->
		{
			executeStaticStatement("create table minigames(name varchar(30), type varchar(30));");
		});
	}

	public static CompletableFuture<Map<String, List<String>>> getMiniGameList()
	{
		return CompletableFuture.supplyAsync(() ->
		{
			Map<String, List<String>> miniGameMap = new HashMap();
			Connection c = XCom.getInstance().getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				ps = c.prepareStatement("SELECT * from minigames;");
				rs = ps.executeQuery();
				while (rs.next())
				{
					String type = rs.getString("type");
					String name = rs.getString("name");
					if (miniGameMap.containsKey(type))
					{
						miniGameMap.get(type).add(name);
					}
					else
					{
						List<String> list = new ArrayList();
						list.add(name);
						miniGameMap.put(type, list);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					rs.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				try
				{
					ps.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				try
				{
					c.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return miniGameMap;
		});
	}

	public static void insertGame(String gameName, String gameType)
	{

		CompletableFuture.runAsync(() ->
		{
			Connection c = XCom.getInstance().getConnection();
			PreparedStatement ps = null;
			try
			{
				ps = c.prepareStatement("INSERT into minigames values(?,?);");
				ps.setString(1, gameName);
				ps.setString(2, gameType);
				ps.execute();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					ps.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				try
				{
					c.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		});
	}

	public static void executeStaticStatement(String statement)
	{
		CompletableFuture.runAsync(() ->
		{
			Connection c = XCom.getInstance().getConnection();
			PreparedStatement ps = null;
			try
			{
				ps = c.prepareStatement(statement);
				System.out.println("Executing Statement!");
				ps.execute();
			}
			catch (Exception e)
			{

			}
			finally
			{
				try
				{
					ps.close();
				}
				catch (Exception e)
				{
				}
				try
				{
					c.close();
				}
				catch (Exception e)
				{
				}
			}
		});
	}

}
