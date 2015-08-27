package com.redmancometh.xcom.databasing;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource
{
	private ComboPooledDataSource cpds;	
	public DataSource(String user, String password, String database) throws IOException, SQLException, PropertyVetoException
	{
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/"+database);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(180);
	}


	public Connection getConnection() throws SQLException
	{
		return this.cpds.getConnection();
	}
}