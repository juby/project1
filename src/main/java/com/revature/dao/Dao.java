package com.revature.dao;

import java.util.List;

/**
 * Interface for the basic functions of any DAO named according to CRUD
 * principles (create, read, update, delete)
 *
 * @param <T> The type of object this DAO will be handling
 */
interface Dao<T> {
	void create(T obj);

	T read(int id);

	List<T> readAll();

	void update(T obj);

	void delete(T obj);
}
