package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for the basic functions of any DAO named according to CRUD
 * principles (create, read, update, delete)
 *
 * @param <T> The type of object this DAO will be handling
 */
public interface Dao<T> {
	void create(T obj) throws SQLException;

	T read(int id) throws SQLException;

	List<T> readAll() throws SQLException;

	void update(T obj) throws SQLException;

	boolean delete(T obj) throws SQLException;
}
