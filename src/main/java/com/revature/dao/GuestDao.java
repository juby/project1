package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Guest;

public class GuestDao implements Dao<Guest> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public GuestDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void create(Guest obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Guest read(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Guest> readAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Guest obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Guest obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkPassword(Guest obj, String passHash) throws SQLException {
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
