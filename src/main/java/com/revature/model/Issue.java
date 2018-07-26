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
	public Reservation getReservation() {
		return rez;
	}

	/**
	 * @param rez the rez to set
	 */
	public void setReservation(Reservation rez) {
		this.rez = rez;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + ((guest == null) ? 0 : guest.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + id;
		result = prime * result + (resolved ? 1231 : 1237);
		result = prime * result + ((rez == null) ? 0 : rez.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Issue))
			return false;
		Issue other = (Issue) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (guest == null) {
			if (other.guest != null)
				return false;
		} else if (!guest.equals(other.guest))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (id != other.id)
			return false;
		if (resolved != other.resolved)
			return false;
		if (rez == null) {
			if (other.rez != null)
				return false;
		} else if (!rez.equals(other.rez))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Issue [id=" + id + ", rez=" + rez + ", guest=" + guest + ", host=" + host + ", title=" + title
				+ ", contents=" + contents + ", resolved=" + resolved + "]";
	}

}
