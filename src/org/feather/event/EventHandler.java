package org.feather.event;

import java.util.concurrent.TimeUnit;

import org.feather.Engine;


/**
 * A class to handle events.
 * @author Thock321
 *
 */
public class EventHandler {
	
	/**
	 * Adds an event.
	 * @param event The event to add.
	 */
	public static void scheduleEvent(final Event event) {
		Engine.getEngine().scheduleRunnable(new Runnable() {
			@Override
			public void run() {
				if(event.active() && event.getOwner() != null) {
					event.run();
				} else {
					return;
				}
				scheduleEvent(event);
			}
		}, event.getDelay(), TimeUnit.MILLISECONDS);
	}

}
