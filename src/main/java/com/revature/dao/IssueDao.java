package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		PreparedStatement ps = null;
		String sql = "Insert into issues (i_guestid, i_rezid, i_hostid, i_resolved, i_title, i_contents) values (?, ?, ?, ?, ?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getGuest().getId());
		ps.setInt(2, obj.getReservation().getId());
		if (obj.getHost() == null)
			ps.setNull(3, java.sql.Types.NUMERIC);
		else
			ps.setInt(3, obj.getHost().getId());
		if (obj.isResolved())
			ps.setInt(4, 1);
		else
			ps.setInt(4, 0);
		ps.setString(5, obj.getTitle());
		ps.setString(6, obj.getContents());
		ResultSet rs = ps.executeQuery();

		sql = "SELECT sq_issues_pk.CURRVAL FROM dual";
		ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();

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
	public Issue read(int id) throws SQLException {
		GuestDao gdao = new GuestDao(connection);
		HostDao hdao = new HostDao(connection);
		ReservationDao rdao = new ReservationDao(connection);

		PreparedStatement ps = null;
		Issue issue = null;
		String sql = "SELECT * FROM issues where i_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			boolean approved = rs.getInt("i_resolved") == 1 ? true : false;
			issue = new Issue(rs.getInt("i_id"), rdao.read(rs.getInt("i_rezid")), gdao.read(rs.getInt("i_guestid")),
					hdao.read(rs.getInt("i_hostid")), rs.getString("i_title"), rs.getString("i_contents"), approved);
		}

		rs.close();
		ps.close();

		return issue;
	}

	@Override
	public List<Issue> readAll() throws SQLException {
		GuestDao gdao = new GuestDao(connection);
		HostDao hdao = new HostDao(connection);
		ReservationDao rdao = new ReservationDao(connection);
		List<Issue> issues = new ArrayList<>();

		PreparedStatement ps = null;
		Issue issue = null;
		String sql = "SELECT * FROM issues";
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			boolean approved = rs.getInt("i_resolved") == 1 ? true : false;
			issue = new Issue(rs.getInt("i_id"), rdao.read(rs.getInt("i_rezid")), gdao.read(rs.getInt("i_guestid")),
					hdao.read(rs.getInt("i_hostid")), rs.getString("i_title"), rs.getString("i_contents"), approved);
			issues.add(issue);
		}

		rs.close();
		ps.close();

		return issues;
	}

	@Override
	public void update(Issue obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Update issues set i_guestid = ?, i_rezid = ?, i_hostid = ?, i_resolved = ?, i_title = ?, i_contents = ? where i_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getGuest().getId());
		ps.setInt(2, obj.getReservation().getId());
		if (obj.getHost() == null)
			ps.setNull(3, java.sql.Types.NUMERIC);
		else
			ps.setInt(3, obj.getHost().getId());
		if (obj.isResolved())
			ps.setInt(4, 1);
		else
			ps.setInt(4, 0);
		ps.setString(5, obj.getTitle());
		ps.setString(6, obj.getContents());
		ps.setInt(7, obj.getId());
		ps.executeQuery();

		ps.close();
	}

	@Override
	public void delete(Issue obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Delete from issues where i_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getId());
		ps.executeQuery();

		ps.close();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
