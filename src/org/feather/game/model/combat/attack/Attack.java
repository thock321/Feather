package org.feather.game.model.combat.attack;

import org.feather.tick.TickUnit;
import org.feather.game.model.ActiveEntity;


public abstract class Attack {
	
	private ActiveEntity attacker, target;
	
	public Attack(ActiveEntity attacker, ActiveEntity target) {
		this.attacker = attacker;
		this.target = target;
	}
	
	public ActiveEntity getAttacker() {
		return attacker;
	}
	
	public ActiveEntity getTarget() {
		return target;
	}
	
	public abstract TickUnit attack();

}
