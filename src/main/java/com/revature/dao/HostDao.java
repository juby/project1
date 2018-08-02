package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Host;

public class HostDao implements UserDao<Host> {
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

	public Host read(String username, String password) throws SQLException {
		PreparedStatement ps = null;
		Host h = null;
		String sql = "SELECT * FROM hosts where h_uname = ?";
		ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Host tmp = new Host(rs.getInt("h_id"), rs.getString("h_uname"), rs.getString("h_fname"),
					rs.getString("h_lname"), rs.getString("h_email"));
			if (this.checkPassword(tmp, password))
				h = tmp;
		}

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
	public boolean delete(Host obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Delete from hosts where h_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getId());
		int i = ps.executeUpdate();

		boolean ret = (i == 0) ? false : true;

		ps.close();

		return ret;
	}

	public boolean checkPassword(Host obj, String passHash) throws SQLException {
		return true;
		// TODO checkPassword
	}

	public void setPassword(Host obj, String password) throws SQLException {
		// TODO setPassword
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean hasSession(Host user) throws SQLException {
		PreparedStatement ps = null;
		ArrayList<Integer> expired = new ArrayList<Integer>();
		
		String sql = "Select * from host_sessions where hs_userid = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, user.getId());
		ResultSet rs = ps.executeQuery();
		boolean ret = false;
		while (rs.next()) {
			if (rs.getTimestamp("hs_expires").toLocalDateTime().isBefore(LocalDateTime.now())) {
				expired.add(rs.getInt("hs_id"));
			} else {
				ret = true;
			}
		}
		
		// clean up expired sessions
		for(int i = 0; i<expired.size(); i++) {
			ps = connection.prepareStatement("Delete from host_sessions where hs_id = ?");
			ps.setInt(1, expired.get(i));
			ps.executeUpdate();
		}
		
		return ret;
	}

	@Override
	public int makeSession(Host user) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Insert into host_sessions (hs_userid, hs_expires) values (?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, user.getId());
		ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
		return ps.executeUpdate();
	}

	@Override
	public void clearSessions(Host user) throws SQLException {
		String sql = "Delete from host_sessions where hs_userid = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, user.getId());
		ps.executeUpdate();
	}
}
