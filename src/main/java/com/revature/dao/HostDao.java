package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Host;

public class HostDao implements Dao<Host> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public HostDao(Connection connection) {
		super();
		this.setConnection(connection);
	}

	@Override
	public void create(Host obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Host read(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Host> readAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Host obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Host obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkPassword(Host obj, String passHash) throws SQLException {
		return false;
		//TODO checkPassword
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
