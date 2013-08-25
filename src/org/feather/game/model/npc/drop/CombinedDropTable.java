package org.feather.game.model.npc.drop;

/**
 * Combines more than one drop table to form one drop table.
 * @author Albert's_Computer
 *
 */
public class CombinedDropTable extends DropTable {
	
	public CombinedDropTable(DropTable...dropTables) {
		for (DropTable table : dropTables) {
			addAll(table.getDrops());
		}
	}

	@Override
	public void loadDrops() {
		//empty
	}

}
