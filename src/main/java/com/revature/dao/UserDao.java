package com.revature.dao;

import java.sql.SQLException;

public interface UserDao<T> extends Dao<T> {
	T read(String username, String password) throws SQLException;
	public boolean hasSession(T user) throws SQLException;
	public int makeSession(T user) throws SQLException;
	public void create(T user) throws SQLException;
	public boolean delete(T user) throws SQLException;
}
