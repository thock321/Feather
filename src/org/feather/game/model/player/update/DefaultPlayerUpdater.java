package org.feather.game.model.player.update;

import java.util.List;

import org.feather.game.GameWorld;
import org.feather.game.model.player.Player;


/**
 * 
 * @author Thock321
 *
 */
public class DefaultPlayerUpdater implements PlayerUpdater {

	@Override
	public void update() {
		List<Player> players = GameWorld.getWorld().getPlayers();
		for (Player player : players) {
			new PrePlayerUpdateTask(player).run();
		}
		for (Player player : players) {
			new PlayerUpdateTask(player).run();
		}
		for (Player player : players) {
			new PostPlayerUpdateTask(player).run();
		}
	}

}
