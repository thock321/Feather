package org.feather.game.model.player.skills;

public class PlayerSkills {
	
	private Skills skills;
	
	public static enum Skills {
		ATTACK(new PlayerSkill(1, 0));
		
		private PlayerSkill skill;
		
		private Skills(PlayerSkill skill) {
			this.skill = skill;
		}
		
		public PlayerSkill getSkill() {
			return skill;
		}
		
		public int getId() {
			return ordinal();
		}
		
		public Skills forId(int id) {
			return Skills.values()[id];
		}
	}
	
	public PlayerSkill getSkill(int id) {
		return skills.forId(id).getSkill();
	}
	
	public PlayerSkill getSkill(String name) {
		for (Skills skill : Skills.values()) {
			if (skill.toString().equalsIgnoreCase(name)) {
				return skills.forId(skill.getId()).getSkill();
			}
		}
		return null;
	}

}
