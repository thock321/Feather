package org.feather.game.model.player.update;

import org.feather.game.model.player.Player;

/**
 * 
 * @author Thock321
 *
 */
public class PrePlayerUpdateTask extends UpdateTask {
	
	private Player player;
	
	protected PrePlayerUpdateTask(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		player.getMovementQueue().processMovement();
		player.process();
	}

}
