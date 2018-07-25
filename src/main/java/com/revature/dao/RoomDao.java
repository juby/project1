package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Room;

public class RoomDao implements Dao<Room> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public RoomDao(Connection connection) {
		super();
		this.setConnection(connection);
	}

	@Override
	public void create(Room obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Room read(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Room> readAll() throws SQLException {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Room obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Room obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
