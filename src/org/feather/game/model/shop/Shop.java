package org.feather.game.model.shop;

import org.feather.game.model.item.Item;
import org.feather.game.model.player.currency.Currency;

public abstract class Shop {
	
	public Shop(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	private int uniqueId;
	
	public int getUniqueId() {
		return uniqueId;
	}
	
	public abstract String getName();
	
	public abstract Currency getCurrency();
	
	public abstract Item[] getStock();

}
