package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Host;

public class HostDao implements Dao<Host> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public HostDao(Connection connection) {
		super();
		this.setConnection(connection);
	}

	@Override
	public void create(Host obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Insert into hosts (h_uname, h_fname, h_lname, h_email, h_pwd, h_salt) values (?, ?, ?, ?, ?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setString(1, obj.getUserName());
		ps.setString(2, obj.getFirstName());
		ps.setString(3, obj.getLastName());
		ps.setString(4, obj.getEmail());
		ps.setString(5, "temppass"); // TODO password hashing in create()
		ps.setString(6, "temphash");
		ps.executeUpdate();

		sql = "SELECT sq_hosts_pk.CURRVAL FROM dual";
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		if (!rs.next())
			throw new SQLException("Data was not added");
		else {
			int id = rs.getInt(1);
			obj.setId(id);
		}

		rs.close();
		ps.close();
	}

	@Override
	public Host read(int id) throws SQLException {
		PreparedStatement ps = null;
		Host h = null;
		String sql = "SELECT * FROM hosts where h_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			h = new Host(rs.getInt("h_id"), rs.getString("h_uname"), rs.getString("h_fname"), rs.getString("h_lname"),
					rs.getString("h_email"));

		rs.close();
		ps.close();

		return h;
	}

	@Override
	public List<Host> readAll() throws SQLException {
		PreparedStatement ps = null;
		Host h = null;
		List<Host> hosts = new ArrayList<Host>();
		String sql = "SELECT * FROM hosts";
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			h = new Host(rs.getInt("h_id"), rs.getString("h_uname"), rs.getString("h_fname"), rs.getString("h_lname"),
					rs.getString("h_email"));
			hosts.add(h);
		}

		rs.close();
		ps.close();

		return hosts;
	}

	@Override
	public void update(Host obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Update hosts set h_uname = ?, h_fname = ?, h_lname = ?, h_email = ? where h_id = ?";
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
	public void delete(Host obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Delete from hosts where h_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getId());
		ResultSet rs = ps.executeQuery();

		rs.close();
		ps.close();
	}

	public boolean checkPassword(Host obj, String passHash) throws SQLException {
		return false;
		// TODO checkPassword
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
