package org.feather.game.model.npc;

import org.feather.game.listeners.ActiveEntityListener.DefaultActiveEntityListener;
import org.feather.game.model.ActiveEntity;
import org.feather.game.model.Location;
import org.feather.game.model.npc.drop.DropTable;
import org.feather.game.model.npc.impl.DefaultDropTable;

public class Npc extends ActiveEntity {
	
	public Npc(Location location, DropTable dropTable) {
		super(location);
		this.dropTable = dropTable;
	}
	
	public Npc(Location location) {
		this(location, new DefaultDropTable());
	}
	
	{
		addListener(new DefaultActiveEntityListener() {
			
		});
	}

	@Override
	public void process() {
		
	}
	
	private DropTable dropTable;
	
	public DropTable getDropTable() {
		return dropTable;
	}

}
