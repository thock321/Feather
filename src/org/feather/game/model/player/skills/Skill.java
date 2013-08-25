package org.feather.game.model.player.skills;

import org.feather.game.model.player.Player;

public class Skill {
	
	private Player player;
	
	protected Skill(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}

}
