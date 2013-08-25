package org.feather.game.model.npc.drop;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.feather.util.MathUtil;

public abstract class DropTable {
	
	private List<Drop> drops = new LinkedList<Drop>();
	
	public List<Drop> getDrops() {
		return Collections.unmodifiableList(drops);
	}
	
	public Drop getRandomDrop() {
		return (Drop) drops.toArray()[MathUtil.random(drops.toArray().length - 1)];
	}
	
	public DropTable addDrop(Drop drop) {
		drops.add(drop);
		return this;
	}
	
	public DropTable addAll(List<Drop> drops) {
		drops.addAll(drops);
		return this;
	}
	
	public abstract void loadDrops();

}
