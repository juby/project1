package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Issue;

public class IssueDao implements Dao<Issue> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public IssueDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void create(Issue obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Issue read(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Issue> readAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Issue obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Issue obj) throws SQLException {
		// TODO Auto-generated method stub

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
