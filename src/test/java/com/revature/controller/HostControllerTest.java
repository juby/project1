package com.revature.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.revature.model.Host;
import com.revature.util.ConnectionUtil;

public class HostControllerTest {

	@Test
	public void test() throws SQLException, IOException {
		Connection connection = ConnectionUtil.getConnection();
		connection.setAutoCommit(false);
		HostController controller = new HostController(connection);

		try {
			Host h1 = controller.createUser("hpotter", "harry", "potter", "h.potter@hogwarts.edu", "harryspassword");
			assertNotNull(h1);

			Host h2 = controller.login("hpotter", "harryspassword");
			assertEquals(h1, h2);

			boolean b = controller.deleteUser(h1);
			assertTrue(b);

		} catch (SQLException e) {
			fail(e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
		} finally {
			connection.rollback();
			connection.close();
		}
	}

}