package org.feather.game.model.player.update;

public class OptimizedPlayerUpdater implements PlayerUpdater {
	
	private PlayerUpdater updater;
	
	public OptimizedPlayerUpdater() {
		final int cores = Runtime.getRuntime().availableProcessors();
		if (cores == 1) {
			updater = new DefaultPlayerUpdater();
		} else {
			updater = new MultiCorePlayerUpdater();
		}
	}

	@Override
	public void update() {
		updater.update();
	}

}
