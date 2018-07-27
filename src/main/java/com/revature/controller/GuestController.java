package com.revature.controller;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.dao.GuestDao;
import com.revature.model.Guest;

public class GuestController extends UserController<Guest> {


	public GuestController(Connection connection) {
		super(connection);
		this.userdao = new GuestDao(this.connection);
	}

	@Override
	public Guest login(String username, String password) throws SQLException {
		return ((GuestDao) userdao).read(username, password);
	}

	@Override
	public boolean deleteUser(Guest user) throws SQLException {
		return ((GuestDao) userdao).delete(user);
	}

	@Override
	public Guest createUser(String username, String firstname, String lastname, String email, String password) throws SQLException {
		Guest g = new Guest(0, username, firstname, lastname, email);
		((GuestDao) userdao).create(g);
		((GuestDao) userdao).setPassword(g, password, false);
		return g;
	}

}
