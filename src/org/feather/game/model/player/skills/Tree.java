package org.feather.game.model.player.skills;

public class Tree {
	
	private int treeId, stumpId;
	
	public Tree(int treeId, int stumpId) {
		this.treeId = treeId;
		this.stumpId = stumpId;
	}
	
	public int getTreeId() {
		return treeId;
	}
	
	public int getStumpId() {
		return stumpId;
	}

}
