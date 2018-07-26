package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		if(!rs.next()) return null;
		else ret = new Room(rs.getInt("rm_id"), rs.getString("rm_pic"));

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
	public void delete(Room obj) throws SQLException {
		PreparedStatement ps = null;

		String sql = "Delete from rooms where rm_id=?";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, obj.getRoomNumber());
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
