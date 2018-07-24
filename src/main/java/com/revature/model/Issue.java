package com.revature.model;

public class Issue {
	private int id;
	private Reservation rez;
	private Guest guest;
	private Host host;
	private String title, contents;
	private boolean resolved;

	/**
	 * 
	 */
	public Issue() {
		super();
	}

	/**
	 * @param id
	 * @param rez
	 * @param guest
	 * @param host
	 * @param title
	 * @param contents
	 * @param resolved
	 */
	public Issue(int id, Reservation rez, Guest guest, Host host, String title, String contents, boolean resolved) {
		super();
		this.id = id;
		this.rez = rez;
		this.guest = guest;
		this.host = host;
		this.title = title;
		this.contents = contents;
		this.resolved = resolved;
	}

	/**
	 * @return the rez
	 */
	public Reservation getRez() {
		return rez;
	}

	/**
	 * @param rez the rez to set
	 */
	public void setRez(Reservation rez) {
		this.rez = rez;
	}

	/**
	 * @return the guest
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * @param guest the guest to set
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	/**
	 * @return the host
	 */
	public Host getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(Host host) {
		this.host = host;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the resolved
	 */
	public boolean isResolved() {
		return resolved;
	}

	/**
	 * @param resolved the resolved to set
	 */
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

}
