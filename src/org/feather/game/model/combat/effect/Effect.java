package org.feather.game.model.combat.effect;

import org.feather.Engine;
import org.feather.tick.TickUnit;

public abstract class Effect {
	
	private int tickDuration, ticksLeft, tickDelayBetweenEffect;
	private boolean started;
	
	public Effect(int tickDuration) {
		this(tickDuration, 0);
	}
	
	public Effect(int tickDuration, int tickDelayBetweenEffect) {
		this.tickDuration = tickDuration;
		this.tickDelayBetweenEffect = tickDelayBetweenEffect;
		ticksLeft = tickDuration;
	}
	
	public int getTickDuration() {
		return tickDuration;
	}
	
	public int getTicksLeft() {
		return ticksLeft;
	}
	
	public int getTickDelayBetweenEffect() {
		return tickDelayBetweenEffect;
	}
	
	public void startEffect() {
		if (started)
			return;
		started = true;
		Engine.getEngine().addTickUnit(new TickUnit(tickDelayBetweenEffect) {
			
			@Override
			public void executeUnit() {
				if (ticksLeft <= 0) {
					this.stop();
					return;
				}
				ticksLeft -= tickDelayBetweenEffect;
				handleEffect();
			}
			
		});
	}
	
	public abstract void handleEffect();

}
