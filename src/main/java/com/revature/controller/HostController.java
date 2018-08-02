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
		Host user = userdao.read(username, password);
		if(user != null) userdao.makeSession(user);
		return user;
	}

	@Override
	public boolean deleteUser(Host user) throws SQLException {
		return userdao.delete(user);
	}

	@Override
	public Host createUser(String username, String firstname, String lastname, String email, String password)
			throws SQLException {
		Host h = new Host(0, username, firstname, lastname, email);
		userdao.create(h);
		((HostDao) userdao).setPassword(h, password);
		return h;
	}
	
	public Guest createTempGuest(String username, String firstname, String lastname, String email) {
		return null;
		//TODO temporary guest
	}

	@Override
	public boolean validate(Host user) throws SQLException {
		return (userdao.read(user.getId()) != null) &&
				userdao.hasSession(user);
	}

}
