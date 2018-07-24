package com.revature.model;

import java.time.LocalDate;

public class Reservation {
	private int id;
	private Guest guest;
	private Room room;
	private Host host;
	private LocalDate checkin, checkout;
	private boolean status;

	/**
	 * 
	 */
	public Reservation() {
		super();
	}

	/**
	 * @param id
	 * @param guest
	 * @param room
	 * @param host
	 * @param checkin
	 * @param checkout
	 * @param status
	 */
	public Reservation(int id, Guest guest, Room room, Host host, LocalDate checkin, LocalDate checkout,
			boolean status) {
		super();
		this.id = id;
		this.guest = guest;
		this.room = room;
		this.host = host;
		this.checkin = checkin;
		this.checkout = checkout;
		this.status = status;
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
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
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
	 * @return the checkin
	 */
	public LocalDate getCheckin() {
		return checkin;
	}

	/**
	 * @param checkin the checkin to set
	 */
	public void setCheckin(LocalDate checkin) {
		this.checkin = checkin;
	}

	/**
	 * @return the checkout
	 */
	public LocalDate getCheckout() {
		return checkout;
	}

	/**
	 * @param checkout the checkout to set
	 */
	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	

}