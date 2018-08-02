package com.revature.controller;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.dao.*;
import com.revature.model.User;

public abstract class UserController<T extends User> {
	protected Connection connection;
	protected UserDao<T> userdao;

	/**
	 * Connects to a persistent data source using the specified connection object.
	 * 
	 * @param connection
	 */
	public UserController(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public abstract T login(String username, String password) throws SQLException;
	public abstract T createUser(String username, String firstname, String lastname, String email, String password) throws SQLException;
	public abstract boolean deleteUser(T user) throws SQLException;
	public abstract boolean validate(T user) throws SQLException;
	public abstract void logout(T user) throws SQLException;

}
