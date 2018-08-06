package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Room;

public class RoomDao implements Dao<Room> {
	private Connection connection;

	/**
	 * @param connection
	 */
	public RoomDao(Connection connection) {
		super();
		this.setConnection(connection);
	}

	@Override
	public void create(Room obj) throws SQLException {
		PreparedStatement ps = null;

		String sql = "Insert into rooms values (?, ?)";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getRoomNumber());
		ps.setString(2, obj.getRoomPic());
		ResultSet rs = ps.executeQuery();

		rs.close();
		ps.close();
	}

	@Override
	public Room read(int id) throws SQLException {
		PreparedStatement ps = null;
		Room ret;

		String sql = "Select * from rooms where rm_id=?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (!rs.next())
			return null;
		else
			ret = new Room(rs.getInt("rm_id"), rs.getString("rm_pic"));

		rs.close();
		ps.close();

		return ret;
	}

	@Override
	public List<Room> readAll() throws SQLException {
		PreparedStatement ps = null;
		Room r = null;
		List<Room> rooms = new ArrayList<Room>();
		String sql = "SELECT * FROM rooms";
		ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("rm_id");
			String pic = rs.getString("rm_pic");

			r = new Room(id, pic);

			rooms.add(r);
		}

		rs.close();
		ps.close();

		return rooms;
	}

	@Override
	public void update(Room obj) throws SQLException {
		PreparedStatement ps = null;

		String sql = "Update rooms set rm_pic=? where rm_id=?";
		ps = connection.prepareStatement(sql);
		ps.setString(1, obj.getRoomPic());
		ps.setInt(2, obj.getRoomNumber());
		ResultSet rs = ps.executeQuery();

		rs.close();
		ps.close();
	}

	@Override
	public boolean delete(Room obj) throws SQLException {
		PreparedStatement ps = null;

		String sql = "Delete from rooms where rm_id=?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getRoomNumber());
		int i = ps.executeUpdate();

		boolean ret = (i == 0) ? false : true;

		ps.close();

		return ret;
	}

	public boolean isAvailable(Room room, LocalDate date) throws SQLException {
		PreparedStatement ps = null;
		boolean ret = false;

		String sql = "Select * from reservations where rez_roomid = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, room.getRoomNumber());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			LocalDate checkin = rs.getDate("rez_checkin").toLocalDate();
			LocalDate checkout = rs.getDate("rez_checkout").toLocalDate();

			// Room is not available if the proposed date is within an existing checkout
			if (date.isAfter(checkin) || date.isBefore(checkout))
				ret = true;
			// Note that we don't need to check if they're requesting the same date as a
			// checkout;
			// we can always check someone in to a room that was vacated earlier in the day
			else if (date.isEqual(checkin))
				ret = true;
		}

		return ret;
	}

	public boolean isAvailable(Room room, LocalDate begin, LocalDate end) throws SQLException {
		PreparedStatement ps = null;
		boolean ret = true;

		String sql = "Select * from reservations where rez_roomid = ?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, room.getRoomNumber());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			LocalDate checkin = rs.getDate("rez_checkin").toLocalDate();
			LocalDate checkout = rs.getDate("rez_checkout").toLocalDate();

			// 4 possibilities for overlap:
			// 1)Existing reservation entirely within proposed reservation (or exactly overlaps)
			if ((begin.isBefore(checkin) || begin.isEqual(checkin)) && (end.isAfter(checkout) || end.isEqual(checkout)))
				ret = false;
			// 2)Begins before existing reservation but runs into it
			else if (end.isAfter(checkin) && (end.isBefore(checkout) || end.isEqual(checkout)))
				ret = false;
			// 3)Begins during an existing reservation
			else if (begin.isEqual(checkin) || (begin.isAfter(checkin) && begin.isBefore(checkout)))
				ret = false;
			// 4)Begins and ends within an existing reservation
			else if (begin.isAfter(checkin) && end.isBefore(checkout))
				ret = false;
		}

		return ret;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
