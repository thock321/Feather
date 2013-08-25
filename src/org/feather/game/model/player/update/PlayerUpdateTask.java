package org.feather.game.model.player.update;

import org.feather.game.model.player.Player;

/**
 * 
 * @author Thock321
 *
 */
public class PlayerUpdateTask extends UpdateTask {
	
	private Player player;
	
	protected PlayerUpdateTask(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
