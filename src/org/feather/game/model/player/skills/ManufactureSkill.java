package org.feather.game.model.player.skills;

import org.feather.game.model.player.Player;

/**
 * A secondary sector skill represents a skill in the secondary sector, where you manufacture things.
 * @author Albert's_Computer
 *
 */
public abstract class ManufactureSkill extends Skill {

	protected ManufactureSkill(Player player) {
		super(player);
	}
	
	public abstract void manufacture();
	
	public abstract boolean canManufacture();

}
