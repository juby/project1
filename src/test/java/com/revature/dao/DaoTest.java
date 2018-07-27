package com.revature.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import org.junit.Test;

import com.revature.model.*;
import com.revature.util.ConnectionUtil;

public class DaoTest {

	@Test
	public void testRoomDao() throws Exception {
		// Initialize variables
		Room r1 = new Room(401, "401.jpg");
		Room r2 = new Room(402, "402.jpg");
		ArrayList<Room> rooms = new ArrayList<Room>();
		rooms.add(r1);
		rooms.add(r2);
		Connection connection = ConnectionUtil.getConnection();
		connection.setAutoCommit(false);
		RoomDao dao = new RoomDao(connection);

		try {
			// create 2 new rooms
			dao.create(r1);
			dao.create(r2);

			// read each room
			assertEquals(r1, dao.read(r1.getRoomNumber()));
			assertEquals(r2, dao.read(r2.getRoomNumber()));

			// read all rooms
			assertEquals(rooms, dao.readAll());

			// update rooms
			r1.setRoomPic("401.png");
			r2.setRoomPic("402.png");
			dao.update(r1);
			dao.update(r2);

			// read updated rooms
			assertEquals(r1, dao.read(r1.getRoomNumber()));
			assertEquals(r2, dao.read(r2.getRoomNumber()));

			// delete rooms
			dao.delete(r1);
			dao.delete(r2);

			// attempt to read deleted rooms
			assertNull(dao.read(r1.getRoomNumber()));
			assertNull(dao.read(r2.getRoomNumber()));

		} catch (SQLException e) {
			fail(e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
		} finally {
			connection.rollback();
			connection.close();
		}
	}

	@Test
	public void testHostDao() throws Exception {
		// Initialize variables
		Host h1 = new Host(0, "harry.potter", "Harry", "Potter", "harry.potter@hogwarts.edu");
		Host h2 = new Host(0, "andrew.wiggins", "Andrew", "Wiggins", "ender@fleet.gov");
		ArrayList<Host> hosts = new ArrayList<Host>();
		hosts.add(h1);
		hosts.add(h2);
		Connection connection = ConnectionUtil.getConnection();
		connection.setAutoCommit(false);
		HostDao dao = new HostDao(connection);

		try {
			// create 2 new hosts
			dao.create(h1);
			dao.create(h2);

			// read each hosts
			assertEquals(h1, dao.read(h1.getId()));
			assertEquals(h2, dao.read(h2.getId()));

			// read all hosts
			assertEquals(hosts, dao.readAll());

			// update hosts
			h1.setEmail("fake@example.org");
			h2.setEmail("anotherfake@example.org");
			dao.update(h1);
			dao.update(h2);

			// read updated hosts
			assertEquals(h1, dao.read(h1.getId()));
			assertEquals(h2, dao.read(h2.getId()));

			// TODO test check/set password methods

			// delete hosts
			dao.delete(h1);
			dao.delete(h2);

			// attempt to read deleted hosts
			assertNull(dao.read(h1.getId()));
			assertNull(dao.read(h2.getId()));

		} catch (SQLException e) {
			fail(e.getMessage() + "\n" + e.getStackTrace());
		} finally {
			connection.rollback();
			connection.close();
		}
	}

	@Test
	public void testGuestDao() throws Exception {
		// Initialize variables
		Guest g1 = new Guest(0, "harry.potter", "Harry", "Potter", "harry.potter@hogwarts.edu");
		Guest g2 = new Guest(0, "andrew.wiggins", "Andrew", "Wiggins", "ender@fleet.gov");
		ArrayList<Guest> guests = new ArrayList<Guest>();
		guests.add(g1);
		guests.add(g2);
		Connection connection = ConnectionUtil.getConnection();
		connection.setAutoCommit(false);
		GuestDao dao = new GuestDao(connection);

		try {
			// create 2 new guests
			dao.create(g1);
			dao.create(g2);

			// read each guest
			assertEquals(g1, dao.read(g1.getId()));
			assertEquals(g2, dao.read(g2.getId()));

			// read all guests
			assertEquals(guests, dao.readAll());

			// update guests
			g1.setEmail("fake@example.org");
			g2.setEmail("anotherfake@example.org");
			dao.update(g1);
			dao.update(g2);

			// read updated guests
			assertEquals(g1, dao.read(g1.getId()));
			assertEquals(g2, dao.read(g2.getId()));

			// TODO password set/check methods

			// delete guests
			dao.delete(g1);
			dao.delete(g2);

			// attempt to read deleted guests
			assertNull(dao.read(g1.getId()));
			assertNull(dao.read(g2.getId()));

		} catch (SQLException e) {
			fail(e.getMessage() + "\n" + e.getErrorCode() + "\n" + e.getStackTrace());
		} finally {
			connection.rollback();
			connection.close();
		}
	}

	@Test
	public void testReservationDao() throws Exception {
		// Initialize variables
		Connection connection = ConnectionUtil.getConnection();
		connection.setAutoCommit(false);

		Room r1 = new Room(401, "401.jpg");
		Room r2 = new Room(402, "402.jpg");
		RoomDao rdao = new RoomDao(connection);
		rdao.create(r1);
		rdao.create(r2);

		Host h1 = new Host(0, "albus.dumbledore", "Albus", "Dumbledore", "headmaster@hogwarts.edu");
		HostDao hdao = new HostDao(connection);
		hdao.create(h1);

		Guest g1 = new Guest(0, "harry.potter", "Harry", "Potter", "harry.potter@hogwarts.edu");
		Guest g2 = new Guest(0, "andrew.wiggins", "Andrew", "Wiggins", "ender@fleet.gov");
		GuestDao gdao = new GuestDao(connection);
		gdao.create(g1);
		gdao.create(g2);

		Reservation rez1 = new Reservation(0, g1, r1, h1, LocalDate.of(2018, 10, 15), LocalDate.of(2018, 10, 22),
				false);
		Reservation rez2 = new Reservation(0, g2, r2, null, LocalDate.of(2018, 11, 15), LocalDate.of(2018, 11, 22),
				true);

		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		reservations.add(rez1);
		reservations.add(rez2);

		ReservationDao dao = new ReservationDao(connection);

		try {
			// create 2 new reservations
			dao.create(rez1);
			dao.create(rez2);

			// read each reservation
			assertEquals(rez1, dao.read(rez1.getId()));
			assertEquals(rez2, dao.read(rez2.getId()));

			// read all reservations
			assertEquals(reservations, dao.readAll());

			// update reservations
			rez1.setCheckin(LocalDate.of(2018, 10, 7));
			rez2.setCheckin(LocalDate.of(2018, 11, 7));
			dao.update(rez1);
			dao.update(rez2);

			// read updated reservations
			assertEquals(rez1, dao.read(rez1.getId()));
			assertEquals(rez2, dao.read(rez2.getId()));

			// delete reservations
			dao.delete(rez1);
			dao.delete(rez2);

			// attempt to read deleted reservations
			assertNull(dao.read(rez1.getId()));
			assertNull(dao.read(rez2.getId()));

		} catch (SQLException e) {
			fail(e.getMessage() + "\n" + e.getStackTrace());
		} finally {
			connection.rollback();
			connection.close();
		}
	}

	@Test
	public void testIssueDao() throws Exception {
		// Initialize variables
		Connection connection = ConnectionUtil.getConnection();
		connection.setAutoCommit(false);

		Room r1 = new Room(401, "401.jpg");
		Room r2 = new Room(402, "402.jpg");
		RoomDao rdao = new RoomDao(connection);
		rdao.create(r1);
		rdao.create(r2);

		Host h1 = new Host(0, "albus.dumbledore", "Albus", "Dumbledore", "headmaster@hogwarts.edu");
		HostDao hdao = new HostDao(connection);
		hdao.create(h1);

		Guest g1 = new Guest(0, "harry.potter", "Harry", "Potter", "harry.potter@hogwarts.edu");
		Guest g2 = new Guest(0, "andrew.wiggins", "Andrew", "Wiggins", "ender@fleet.gov");
		GuestDao gdao = new GuestDao(connection);
		gdao.create(g1);
		gdao.create(g2);

		Reservation rez1 = new Reservation(0, g1, r1, h1, LocalDate.of(2018, 10, 15), LocalDate.of(2018, 10, 22),
				false);
		Reservation rez2 = new Reservation(0, g2, r2, null, LocalDate.of(2018, 11, 15), LocalDate.of(2018, 11, 22),
				false);
		ReservationDao rezdao = new ReservationDao(connection);
		rezdao.create(rez1);
		rezdao.create(rez2);

		Issue i1 = new Issue(0, rez1, g1, h1, "Moving rooms", "The rooms keep moving around!", false);
		Issue i2 = new Issue(0, rez2, g2, null, "Buggers", "There are bugs in the bed!", true);

		ArrayList<Issue> issues = new ArrayList<Issue>();
		issues.add(i1);
		issues.add(i2);
		IssueDao dao = new IssueDao(connection);

		try {
			// create 2 new issues
			dao.create(i1);
			dao.create(i2);

			// read each issue
			assertEquals(i1, dao.read(i1.getId()));
			assertEquals(i2, dao.read(i2.getId()));

			// read all issues
			assertEquals(issues, dao.readAll());

			// update issues
			i1.setTitle("Attn: Prof. Dumbledore");
			i2.setTitle("Attn: Col. Graff");
			dao.update(i1);
			dao.update(i2);

			// read updated issues
			assertEquals(i1, dao.read(i1.getId()));
			assertEquals(i2, dao.read(i2.getId()));

			// delete issues
			dao.delete(i1);
			dao.delete(i2);

			// attempt to read deleted issues
			assertNull(dao.read(i1.getId()));
			assertNull(dao.read(i2.getId()));

		} catch (SQLException e) {
			fail(e.getMessage() + "\n" + e.getStackTrace());
		} finally {
			connection.rollback();
			connection.close();
		}
	}
}
