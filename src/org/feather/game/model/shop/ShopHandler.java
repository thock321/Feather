package org.feather.game.model.shop;


import java.util.ArrayList;
import java.util.List;

import org.feather.game.model.item.Item;
import org.feather.game.model.player.currency.Currency;

public class ShopHandler {
	
	private List<Shop> shops = new ArrayList<Shop>();
	
	{
		shops.add(new Shop(1) {
			
			@Override
			public String getName() {
				return "General Store";
			}

			@Override
			public Currency getCurrency() {
				return new Currency(new Item(995));
			}

			@Override
			public Item[] getStock() {
				return new Item[] {new Item(1)};
			}
			
		});
	}

}
