package org.feather.game.model;

/**
 * A location within the game.
 * @author Thock321
 *
 */
public class Location {
	
	private int x;
	private int y;
	private int h;
	
	/**
	 * Creates a new location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param h The height.
	 */
	public Location(int x, int y, int h) {
		this.x = x;
		this.y = y;
		this.h = h;
	}
	
	/**
	 * Gets the x coordinate.
	 * @return The x coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate.
	 * @return The y coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the height.
	 * @return The height.
	 */
	public int getH() {
		return h;
	}
	
	/**
	 * Gets the current region x coordinate.
	 * @return The region x coordinate.
	 */
    public int getRegionX() {
        return getX() >> 3;
    }

    /**
     * Gets the current region y coordinate.
     * @return The region y coordinate.
     */
    public int getRegionY() {
        return getY() >> 3;
    }
    
    /**
     * Gets the local x coordinate.
     * @return The local x coordinate.
     */
    public int getLocalX() {
        return x - (8 * (getRegionX() - 6));
    }

    /**
     * Gets the local y coordinate.
     * @return The y coordinate.
     */
    public int getLocalY() {
        return y - (8 * (getRegionY() - 6));
    }
    
	/**
	 * Checks if this location is within distance of another location.
	 * @param other The other location.
	 * @return Whether or not the location is within distance of the other location.
	 */
	public boolean isWithinDistance(Location other) {
		if (h != other.getH())
			return false;
		int diffX = other.getX() - x, diffY = other.getY() - y;
		return diffX <= 14 && diffX >= -15 && diffY <= 14 && diffY >= -15;
	}
    
    /**
     * Checks if an object is equal to this one.
     */
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Location other = (Location) obj;
		if(x != other.x) {
			return false;
		}
		if(y != other.y) {
			return false;
		}
		if (h != other.h) {
			return false;
		}
		return true;
	}

}
