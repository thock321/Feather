package org.feather.game.model.npc.drop;

import org.feather.game.model.item.Item;

/**
 * Represents a single npc drop.
 * @author Albert's_Computer
 *
 */
public class Drop {
	
	private Item dropItem;
	
	/**
	 * The chance of an item dropping out of 100.
	 */
	private double dropChance;
	
	public Drop(Item dropItem, double dropChance) {
		this.dropChance = dropChance;
		this.dropItem = dropItem;
	}
	
	public Item getDropItem() {
		return dropItem;
	}
	
	public double getDropChance() {
		return dropChance;
	}

}
