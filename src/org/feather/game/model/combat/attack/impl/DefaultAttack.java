package org.feather.game.model.combat.attack.impl;

import org.feather.tick.TickUnit;
import org.feather.game.model.ActiveEntity;
import org.feather.game.model.combat.attack.Attack;


public class DefaultAttack extends Attack {
	
	private int hitDelay;

	public DefaultAttack(ActiveEntity attacker, ActiveEntity target, int hitDelay) {
		super(attacker, target);
	}

	@Override
	public TickUnit attack() {
		// TODO Auto-generated method stub
		return new TickUnit(hitDelay) {

			@Override
			public void executeUnit() {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

}
