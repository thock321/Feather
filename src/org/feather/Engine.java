package org.feather;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.feather.tick.TickUnit;
import org.feather.game.Configuration;


/**
 * The server's engine.  
 * @author Thock321
 *
 */
public class Engine implements Runnable {
	
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	
	private List<TickUnit> tickUnits = new LinkedList<TickUnit>();
	
	private Iterator<TickUnit> itr;
	
	private Queue<TickUnit> tickUnitsToAdd = new LinkedBlockingDeque<TickUnit>();
	
	private static Engine engine = new Engine();
	
	/**
	 * Creates a new instance of the engine and starts it up.
	 */
	public Engine() {
		executorService.scheduleAtFixedRate(this, 0, Configuration.GAME_TICK_DELAY, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Gets the instance of the engine.
	 * @return The engine instance.
	 */
	public static Engine getEngine() {
		return engine;
	}

	/**
	 * Adds a tick unit.
	 * @param tickUnit The work unit to be added.
	 */
	public void addTickUnit(TickUnit tickUnit) {
		synchronized (tickUnitsToAdd) {
			tickUnitsToAdd.add(tickUnit);
		}
	}
	
	private TickUnit tmp;
	
	/**
	 * The main tick of the engine.
	 */
	@Override
	public void run() {
		synchronized (tickUnitsToAdd) {
			while ((tmp = tickUnitsToAdd.poll()) != null)
				tickUnits.add(tmp);
		}
		itr = tickUnits.iterator();
		while (itr.hasNext()) {
			tmp = itr.next();
			if (!tmp.handleUnit())
				itr.remove();
		}
	}
	
	/**
	 * Schedule a runnable to run after a certain amount of time.
	 * @param runnable The runnable to be run.
	 * @param delay Delay before the runnable is run.
	 * @param unit What units the <code>delay</code> is measured in.
	 * @return The event.
	 */
	public ScheduledFuture<?> scheduleRunnable(final Runnable runnable,
			final long delay, final TimeUnit unit) {
		return executorService.schedule(new Runnable() {
			
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
		}, delay, unit);
	}
	
	/**
	 * Submit a runnable to be run.
	 * @param runnable The runnable you want to run.
	 */
	public void submitRunnable(final Runnable runnable) {
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
		});
	}

}

