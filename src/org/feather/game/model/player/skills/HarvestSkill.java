package org.feather.game.model.player.skills;

import org.feather.tick.TickUnit;
import org.feather.game.GameWorld;
import org.feather.game.model.object.WorldObject;
import org.feather.game.model.player.Player;


/**
 * A primary sector skill represents a skill that is in the primary sector where you harvest raw materials.
 * @author Albert's_Computer
 *
 */
public abstract class HarvestSkill extends Skill {
	
	public HarvestSkill(Player player) {
		super(player);
	}
	
	private boolean started;
	
	public abstract void startSkill(WorldObject toHarvest);
	
	public abstract void harvest();
	
	public abstract boolean canHarvest();
	
	public abstract void giveReward();
	
	public abstract boolean stopHarvesting();
	
	public abstract void playAnimation();
	
	public abstract void reset();
	
	public void handleSkill() {
		GameWorld.getWorld().submit(new TickUnit(1) {

			@Override
			public void executeUnit() {
				if (stopHarvesting()) {
					reset();
					this.stop();
					return;
				}
				if (canHarvest())
					harvest();
			}
			
		});
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

}
