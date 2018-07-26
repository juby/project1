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

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
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
		result = prime * result + roomNumber;
		result = prime * result + ((roomPic == null) ? 0 : roomPic.hashCode());
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
		if (!(obj instanceof Room))
			return false;
		Room other = (Room) obj;
		if (roomNumber != other.roomNumber)
			return false;
		if (roomPic == null) {
			if (other.roomPic != null)
				return false;
		} else if (!roomPic.equals(other.roomPic))
			return false;
		return true;
	}

}
