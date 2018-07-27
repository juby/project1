package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Guest;

public class GuestDao implements Dao<Guest>, User<Guest> {
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
		ps.setString(5, "temppass");
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
	
	public Guest read(String username, String password) throws SQLException {
		PreparedStatement ps = null;
		Guest g = null;
		String sql = "SELECT * FROM guests where g_uname = ?";
		ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Guest tmp = new Guest(rs.getInt("g_id"), rs.getString("g_uname"), rs.getString("g_fname"),
					rs.getString("g_lname"), rs.getString("g_email"));
			if (this.checkPassword(tmp, password))
				g = tmp;
		}

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
	public boolean delete(Guest obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Delete from guests where g_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getId());
		int i = ps.executeUpdate();
		
		boolean ret = (i == 0) ? false : true;

		ps.close();
		
		return ret;
	}

	public boolean checkPassword(Guest obj, String password) throws SQLException {
		return true;
		// TODO checkPassword
	}

	public void setPassword(Guest obj, String password, boolean isTemp) throws SQLException {
		// TODO setPassword
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean hasSession(Guest user) throws SQLException {
		PreparedStatement ps = null, ps2 = null;
		String sql = "Select * from guest_sessions where gs_userid = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, user.getId());
		ResultSet rs = ps.executeQuery();
		boolean ret = false;
		while(rs.next()) {
			if(rs.getTimestamp("gs_expires").toLocalDateTime().isBefore(LocalDateTime.now())) {
				//clean up expired sessions
				ps2 = connection.prepareStatement("Delete from guest_sessions where hs_id = ?");
				ps2.setInt(1, rs.getInt("gs_id"));
				ps.executeUpdate();
			} else {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void makeSession(Guest user) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Insert into guest_sessions (gs_userid, gs_expires) values (?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, user.getId());
		ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
		ps.executeUpdate();
	}
	
}
