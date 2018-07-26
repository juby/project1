package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Reservation;

public class ReservationDao implements Dao<Reservation> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public ReservationDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void create(Reservation obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Insert into reservations (rez_guestid, rez_roomid, rez_hostid, rez_status, rez_checkin, rez_checkout) values (?, ?, ?, ?, ?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getGuest().getId());
		ps.setInt(2, obj.getRoom().getRoomNumber());
		if (obj.getHost() == null)
			ps.setNull(3, java.sql.Types.NUMERIC);
		else
			ps.setInt(3, obj.getHost().getId());
		if (obj.isApproved())
			ps.setInt(4, 1);
		else
			ps.setInt(4, 0);
		ps.setDate(5, Date.valueOf(obj.getCheckin()));
		ps.setDate(6, Date.valueOf(obj.getCheckout()));
		ps.executeUpdate();

		sql = "SELECT sq_reservations_pk.CURRVAL FROM dual";
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
	public Reservation read(int id) throws SQLException {
		GuestDao gdao = new GuestDao(connection);
		HostDao hdao = new HostDao(connection);
		RoomDao rdao = new RoomDao(connection);

		PreparedStatement ps = null;
		Reservation rez = null;
		String sql = "SELECT * FROM reservations where rez_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			boolean approved = rs.getInt("rez_status") == 1 ? true : false;
			rez = new Reservation(rs.getInt("rez_id"), gdao.read(rs.getInt("rez_guestid")),
					rdao.read(rs.getInt("rez_roomid")), hdao.read(rs.getInt("rez_hostid")),
					rs.getDate("rez_checkin").toLocalDate(), rs.getDate("rez_checkout").toLocalDate(), approved);
		}

		rs.close();
		ps.close();

		return rez;
	}

	@Override
	public List<Reservation> readAll() throws SQLException {
		GuestDao gdao = new GuestDao(connection);
		HostDao hdao = new HostDao(connection);
		RoomDao rdao = new RoomDao(connection);

		PreparedStatement ps = null;
		Reservation rez = null;
		List<Reservation> rezes = new ArrayList<Reservation>();
		String sql = "SELECT * FROM reservations";
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			boolean approved = rs.getInt("rez_status") == 1 ? true : false;
			rez = new Reservation(rs.getInt("rez_id"), gdao.read(rs.getInt("rez_guestid")),
					rdao.read(rs.getInt("rez_roomid")), hdao.read(rs.getInt("rez_hostid")),
					rs.getDate("rez_checkin").toLocalDate(), rs.getDate("rez_checkout").toLocalDate(), approved);
			rezes.add(rez);
		}

		rs.close();
		ps.close();

		return rezes;
	}

	@Override
	public void update(Reservation obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Update reservations set rez_guestid = ?, rez_roomid = ?, rez_hostid = ?, rez_status = ?, rez_checkin = ?, rez_checkout = ? where rez_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getGuest().getId());
		ps.setInt(2, obj.getRoom().getRoomNumber());
		if (obj.getHost() == null)
			ps.setNull(3, java.sql.Types.NUMERIC);
		else
			ps.setInt(3, obj.getHost().getId());
		if (obj.isApproved())
			ps.setInt(4, 1);
		else
			ps.setInt(4, 0);
		ps.setDate(5, Date.valueOf(obj.getCheckin()));
		ps.setDate(6, Date.valueOf(obj.getCheckout()));
		ps.setInt(7, obj.getId());
		ps.executeUpdate();

		ps.close();
	}

	@Override
	public void delete(Reservation obj) throws SQLException {
		PreparedStatement ps = null;
		String sql = "Delete from reservations where rez_id = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getId());
		ResultSet rs = ps.executeQuery();

		rs.close();
		ps.close();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
