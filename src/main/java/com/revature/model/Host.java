package com.revature.model;

public class Host {
	private int id, iterations;
	private String userName, firstName, lastName, email, salt;
	/**
	 * 
	 */
	public Host() {
		super();
	}
	/**
	 * @param id
	 * @param iterations
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param salt
	 */
	public Host(int id, int iterations, String userName, String firstName, String lastName, String email, String salt) {
		super();
		this.id = id;
		this.iterations = iterations;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.salt = salt;
	}
	/**
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}
	/**
	 * @param iterations the iterations to set
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	
}
