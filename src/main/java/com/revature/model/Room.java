package com.revature.model;

public class Room {
	private int roomNumber;
	private String roomPic;

	/**
	 * 
	 */
	public Room() {
		super();
	}

	/**
	 * @param roomNumber
	 * @param roomPic
	 */
	public Room(int roomNumber, String roomPic) {
		super();
		this.roomNumber = roomNumber;
		this.roomPic = roomPic;
	}

	/**
	 * @return the roomPic
	 */
	public String getRoomPic() {
		return roomPic;
	}

	/**
	 * @param roomPic the roomPic to set
	 */
	public void setRoomPic(String roomPic) {
		this.roomPic = roomPic;
	}

	/**
	 * @return the roomNumber
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

}
