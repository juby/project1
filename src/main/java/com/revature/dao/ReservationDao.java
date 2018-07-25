package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Reservation;

public class ReservationDao implements Dao<Reservation> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public ReservationDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void create(Reservation obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Reservation read(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> readAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Reservation obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Reservation obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
