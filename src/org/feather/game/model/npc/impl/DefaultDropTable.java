package org.feather.game.model.npc.impl;

import org.feather.game.model.item.Item;
import org.feather.game.model.npc.drop.Drop;
import org.feather.game.model.npc.drop.DropTable;

public class DefaultDropTable extends DropTable {

	@Override
	public void loadDrops() {
		addDrop(new Drop(new Item(526), 1));
	}

}
