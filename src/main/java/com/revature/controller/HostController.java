package com.revature.controller;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.dao.HostDao;
import com.revature.model.Guest;
import com.revature.model.Host;

public class HostController extends UserController<Host> {

	public HostController(Connection connection) {
		super(connection);
		this.userdao = new HostDao(this.connection);
	}

	@Override
	public Host login(String username, String password) throws SQLException {
		return ((HostDao) userdao).read(username, password);
	}

	@Override
	public boolean deleteUser(Host user) throws SQLException {
		return ((HostDao) userdao).delete(user);
	}

	@Override
	public Host createUser(String username, String firstname, String lastname, String email, String password)
			throws SQLException {
		Host h = new Host(0, username, firstname, lastname, email);
		((HostDao) userdao).create(h);
		((HostDao) userdao).setPassword(h, password);
		return h;
	}
	
	public Guest createTempGuest(String username, String firstname, String lastname, String email) {
		return null;
		//TODO temporary guest
	}

}
