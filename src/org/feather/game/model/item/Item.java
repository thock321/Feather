package org.feather.game.model.item;

public class Item {
	
	private int id;
	
	private int amount;
	
	public Item(int id, int amount) {
		if (amount < 0 || id < 0)
			throw new IllegalArgumentException();
		this.id = id;
		this.amount = amount;
	}
	
	public Item(int id) {
		this(id, 1);
	}
	
	public int getId() {
		return id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void replaceWith(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	
	public void replaceWith(int id) {
		replaceWith(id, this.amount);
	}
	
	public void add(int toAdd) {
		this.amount += toAdd;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Item) {
			if (((Item)other).id == this.id && ((Item)other).amount == this.amount) {
				return true;
			}
		}
		return false;
	}

}
