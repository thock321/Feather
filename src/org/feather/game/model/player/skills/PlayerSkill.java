package org.feather.game.model.player.skills;

public class PlayerSkill {
	
	private int currentLevel;
	
	private double xp;
	
	public PlayerSkill(int currentLevel, double xp) {
		this.currentLevel = currentLevel;
		this.xp = xp;
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public double getXP() {
		return xp;
	}
	
	public void increaseLevel(int toIncrease) {
		currentLevel += toIncrease;
	}
	
	public void increaseLevel() {
		currentLevel++;
	}
	
	public void decreaseLevel(int toDecrease) {
		currentLevel -= toDecrease;
		if (currentLevel < 1)
			currentLevel = 1;
	}
	
	public void decreaseLevel() {
		currentLevel++;
	}
	
	public int getActualLevel() {
		int points = 0;
		int output;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= xp) {
				return lvl;
			}
		}
		return 99;
	}

}
