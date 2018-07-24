package com.revature.dao;

import java.util.List;

import com.revature.model.Guest;
import com.revature.model.Host;

public class GuestDao implements Dao<Guest> {

	@Override
	public void create(Guest obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Guest read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Guest> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Guest obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Guest obj) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkPassword(Guest obj, String passHash) {
		return false;
		//TODO checkPassword
	}

}
