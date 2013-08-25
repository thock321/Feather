package org.feather.game.model.player.update;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.feather.game.GameWorld;
import org.feather.game.model.player.Player;

/**
 * 
 * @author Thock321
 *
 */
public class MultiCorePlayerUpdater implements Updater {
	
	private ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new LabelledThreadFactory("PlayerUpdateThread"));
	
	private Phaser phaser = new Phaser(1);

	@Override
	public void update() {
		List<Player> players = GameWorld.getWorld().getPlayers();
		phaser.bulkRegister(players.size());
		for (final Player player : players) {
			service.submit(new Runnable() {

				@Override
				public void run() {
					try {
						new PrePlayerUpdateTask(player).run();
					} finally {
						phaser.arriveAndDeregister();
					}
				}
				
			});
		}
		phaser.arriveAndAwaitAdvance();
		phaser.bulkRegister(players.size());
		for (final Player player : players) {
			service.submit(new Runnable() {

				@Override
				public void run() {
					try {
						new PlayerUpdateTask(player).run();
					} finally {
						phaser.arriveAndDeregister();
					}
				}
				
			});
		}
		phaser.arriveAndAwaitAdvance();
		phaser.bulkRegister(players.size());
		for (final Player player : players) {
			service.submit(new Runnable() {

				@Override
				public void run() {
					try {
						new PostPlayerUpdateTask(player).run();
					} finally {
						phaser.arriveAndDeregister();
					}
				}
				
			});
		}
	}

}
