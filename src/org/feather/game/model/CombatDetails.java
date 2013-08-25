package org.feather.game.model;


import java.util.LinkedList;
import java.util.List;

import org.feather.game.model.combat.attack.AttackType;
import org.feather.game.model.combat.effect.Effect;

public class CombatDetails {
	
	private int currentHealth;
	private int maximumHealth;
	private int attack;
	private int defence;
	private List<Effect> activeEffects = new LinkedList<Effect>();
	private ActiveEntity attacking;
	private ActiveEntity underAttackBy;
	private AttackType attackType;
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	public int getMaximumHealth() {
		return maximumHealth;
	}
	
	public void setMaximumHealth(int maximumHealth) {
		this.maximumHealth = maximumHealth;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	public List<Effect> getActiveEffects() {
		return activeEffects;
	}
	
	public void setActiveEffects(List<Effect> activeEffects) {
		this.activeEffects = activeEffects;
	}
	
	public ActiveEntity getAttacking() {
		return attacking;
	}
	
	public void setAttacking(ActiveEntity attacking) {
		this.attacking = attacking;
	}
	
	public ActiveEntity getUnderAttackBy() {
		return underAttackBy;
	}
	
	public void setUnderAttackBy(ActiveEntity underAttackBy) {
		this.underAttackBy = underAttackBy;
	}
	
	public AttackType getAttackType() {
		return attackType;
	}
	
	public void setAttackType(AttackType attackType) {
		this.attackType = attackType;
	}

}
