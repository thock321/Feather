package org.feather.tick;

import org.feather.Server;

/**
 * A single tick unit that does something every tick.
 * @author Thock321
 *
 */
public abstract class TickUnit {
	
	private int tickDelay;
	
	private int timer;
	
	private boolean active;
	
	private Object owner;
	
	/**
	 * What happens when the unit is executed.
	 */
	public abstract void executeUnit();
	
	/**
	 * Creates a new work unit.
	 * @param tickDelay The delay in ticks.
	 */
	public TickUnit(int tickDelay) {
		if (tickDelay < 1) {
			throw new IllegalArgumentException();
		}
		this.tickDelay = tickDelay;
		this.timer = tickDelay;
		this.active = true;
		this.owner = Server.class;
	}
	
	public TickUnit(int tickDelay, Object owner) {
		if (tickDelay < 1)
			throw new IllegalArgumentException();
		this.tickDelay = tickDelay;
		this.timer = tickDelay;
		this.active = true;
		this.owner = owner;
	}
	
	/**
	 * Kills the tick unit.
	 */
	public void stop() {
		this.active = false;
	}
	
	/**
	 * Decreases the timer.  If the timer reaches 0, the tick unit is executed.
	 * @return Whether or not the tick unit is active so the server knows whether or not to kill the unit.
	 */
	public boolean handleUnit() {
		timer--;
		if (timer == 0) {
			executeUnit();
			timer = tickDelay;
		}
		return active && owner != null;
	}

}
