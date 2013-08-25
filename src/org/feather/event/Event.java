package org.feather.event;

import org.feather.Server;

/**
 * A single event.
 * @author Thock321
 *
 */
public abstract class Event {
	
	/**
	 * The event's owner.
	 */
	private Object owner;
	
	/**
	 * The even't delay.
	 */
	private int delay;
	
	/**
	 * Is the event active or not.
	 */
	public boolean active;
	
	/**
	 * Creates a new event.
	 * @param delay The delay in milliseconds.
	 */
	public Event(int delay) {
		this.delay = delay;
		this.active = true;
		this.owner = Server.class;
	}
	
	public Event(int delay, Object owner) {
		this.delay = delay;
		this.active = true;
		this.owner = owner;
	}
	
	/**
	 * What happens when the event is run.
	 */
	public abstract void run();
	
	/**
	 * Gets the delay.
	 * @return The delay.
	 */
	public int getDelay() {
		return delay;
	}
	
	/**
	 * Gets if the event is active or not.
	 * @return If the event is active or not.
	 */
	public boolean active() {
		return active;
	}
	
	/**
	 * Gets the owner.
	 * @return The owner.
	 */
	public Object getOwner() {
		return owner;
	}
	
}
