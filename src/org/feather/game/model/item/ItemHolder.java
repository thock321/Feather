package org.feather.game.model.item;

public class ItemHolder {
	
	private Item[] items;
	
	public ItemHolder(int size) {
		if (size < 1)
			throw new IllegalArgumentException();
		items = new Item[size];
	}
	
	public Item[] getItems() {
		return items;
	}
	
	public void switchSlot(int originalSlot, int destinationSlot) {
		Item dest = items[destinationSlot];
		if (dest == null) {
			items[destinationSlot] = items[originalSlot];
			items[originalSlot] = null;
		} else {
			items[destinationSlot] = items[originalSlot];
			items[originalSlot] = dest;
		}
	}
	
	public boolean addItem(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				return true;
			}
		}
		return false;
	}
	
	public boolean removeItem(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(item)) {
				items[i] = null;
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Item item) {
		for (Item i : items) {
			if (i.equals(item))
				return true;
		}
		return false;
	}
	
	/**
	 * Gets the slot for a specific item.  This will only get the slot it first appears in.
	 * @param item
	 * @return
	 */
	public int getSlotForItem(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].getId() == item.getId()) {
				return i;
			}
		}
		return -1;
	}
	
	public Item getItemForSlot(int slot) {
		if (slot >= items.length || slot < 0)
			return null;
		try {
			return items[slot];
		} catch (NullPointerException e) {
			return null;
		}
	}

}
