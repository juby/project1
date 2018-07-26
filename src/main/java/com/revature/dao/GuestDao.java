package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Guest;

public class GuestDao implements Dao<Guest> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public GuestDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void create(Guest obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Insert into guests (g_uname, g_fname, g_lname, g_email, g_pwd, g_salt, g_istmp) values (?, ?, ?, ?, ?, ?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setString(1, obj.getUserName());
		ps.setString(2, obj.getFirstName());
		ps.setString(3, obj.getLastName());
		ps.setString(4, obj.getEmail());
		ps.setString(5, "temppass"); // TODO password hashing in create()
		ps.setString(6, "temphash");
		ps.setInt(7, 0);
		ps.executeUpdate();

		sql = "SELECT sq_guests_pk.CURRVAL FROM dual";
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
				
		if(!rs.next()) throw new SQLException("Data was not added");
		else {
			int id = rs.getInt(1);
			obj.setId(id);
		}

		rs.close();
		ps.close();
	}

	@Override
	public Guest read(int id) throws SQLException {
		PreparedStatement ps = null;
		Guest g = null;
		String sql = "SELECT * FROM guests where g_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			g = new Guest(rs.getInt("g_id"), rs.getString("g_uname"), rs.getString("g_fname"), rs.getString("g_lname"),
					rs.getString("g_email"));

		rs.close();
		ps.close();

		return g;
	}

	@Override
	public List<Guest> readAll() throws SQLException {
		PreparedStatement ps = null;
		Guest g = null;
		List<Guest> guests = new ArrayList<Guest>();
		String sql = "SELECT * FROM guests";
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			g = new Guest(rs.getInt("g_id"), rs.getString("g_uname"), rs.getString("g_fname"), rs.getString("g_lname"),
					rs.getString("g_email"));
			guests.add(g);
		}

		rs.close();
		ps.close();

		return guests;
	}

	@Override
	public void update(Guest obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Update guests set g_uname = ?, g_fname = ?, g_lname = ?, g_email = ? where g_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setString(1, obj.getUserName());
		ps.setString(2, obj.getFirstName());
		ps.setString(3, obj.getLastName());
		ps.setString(4, obj.getEmail());
		ps.setInt(5, obj.getId());
		ResultSet rs = ps.executeQuery();

		rs.close();
		ps.close();
	}

	@Override
	public void delete(Guest obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Delete from guests where g_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getId());
		ResultSet rs = ps.executeQuery();

		rs.close();
		ps.close();
	}

	public boolean checkPassword(Guest obj, String passHash) throws SQLException {
		return false;
		// TODO checkPassword
	}

	public void setPassword(Guest obj, String passHash, boolean isTemp) throws SQLException {
		// TODO setPassword
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
