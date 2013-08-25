package org.feather.game.model;

public enum SpecificAnimation {
	
	YES(new Animation(855)),
	NO(new Animation(856));
	
	private Animation anim;
	
	private SpecificAnimation(Animation anim) {
		this.anim = anim;
	}
	
	public Animation getAnim() {
		return anim;
	}
	
	public int getAnimId() {
		return anim.getId();
	}

}
