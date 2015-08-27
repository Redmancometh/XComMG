package com.redmancometh.xcom.databasing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

import com.redmancometh.xcom.XCom;

public class DBUtil
{
	public static CompletableFuture<List<String>> getServerNames()
	{
		return CompletableFuture.supplyAsync(() ->
		{
			List<String> nameList = new CopyOnWriteArrayList<String>();
			Connection c = XCom.getInstance().getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				c = XCom.getInstance().getConnection();
				ps = c.prepareStatement("select * from servers;");
				rs = ps.executeQuery();
				while (rs.next())
				{
					nameList.add(rs.getString("name"));
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
			}
			return nameList;
		});
	}

	public static void addServer(String serverName)
	{
		CompletableFuture.runAsync(() ->
		{
			Connection c = null;
			PreparedStatement ps = null;
			try
			{
				c = XCom.getInstance().getConnection();
				ps = c.prepareStatement("insert into servers values(?);");
				ps.setString(1, serverName);
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
			}
		});
	}

}
