package com.revature.dao;

import java.sql.SQLException;

public interface User<T> {
	public boolean hasSession(T user) throws SQLException;
	public void makeSession(T user) throws SQLException;
}
