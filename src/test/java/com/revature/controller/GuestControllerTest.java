package com.revature.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.revature.model.Guest;
import com.revature.util.ConnectionUtil;

public class GuestControllerTest {

	@Test
	public void test() throws SQLException, IOException {
		Connection connection = ConnectionUtil.getConnection();
		connection.setAutoCommit(false);
		GuestController controller = new GuestController(connection);

		try {
			Guest g1 = controller.createUser("hpotter", "harry", "potter", "h.potter@hogwarts.edu", "harryspassword");
			assertNotNull(g1);

			Guest g2 = controller.login("hpotter", "harryspassword");
			assertEquals(g1, g2);

			boolean b = controller.deleteUser(g1);
			assertTrue(b);

		} catch (SQLException e) {
			fail(e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
		} finally {
			connection.rollback();
			connection.close();
		}
	}

}
