package org.feather.game.model.player.skills.impl;

import java.util.HashMap;

import org.feather.game.model.item.Item;
import org.feather.game.model.object.WorldObject;
import org.feather.game.model.player.Player;
import org.feather.game.model.player.skills.HarvestSkill;


public class Woodcutting extends HarvestSkill {

	public Woodcutting(Player player) {
		super(player);
	}
	
	private class Tree {
		
		private Trees definition;
		private WorldObject treeObject;
		
		private Tree(Trees definition, WorldObject treeObject) {
			this.definition = definition;
			this.treeObject = treeObject;
		}
		
	}
	
	private enum Trees {
		NORMAL(new int[]{1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1286, 1289,
				1290, 1291, 1315, 1316, 1318, 1319, 1330, 1331, 1332, 1333, 1365, 1383, 1384,
				2409, 3033, 3034, 3035, 3036, 3881, 3882, 3883, 5902, 5903, 5904}, 1, 25, 1511, 
				1342, 75, 100),
		OAK(new int[]{1281, 2037}, 15, 37.5, 1521, 1356, 14, 25),
		WILLOW(new int[]{1308, 5551, 5552, 5553}, 30, 67.5, 1519, 7399, 14, 5),
		MAPLE(new int[]{1307, 4677}, 45, 100, 1517, 1343, 59, 15),
		YEW(new int[]{1309}, 60, 175, 1515, 7402, 100, 5),
		MAGIC(new int[]{1306}, 75, 250, 1513, 7401, 200, 3),
		ACHEY(new int[]{2023}, 1, 25, 2862, 3371, 75, 100),
		MAHOGANY(new int[]{9034}, 50, 125, 6332, 9035, 80, 10),
		ARCTIC(new int[]{21273}, 55, 175, 10810, 21274, 80, 6),
		TEAK(new int[]{9036}, 35, 85, 6333, 9037, 14, 20),
		HOLLOW(new int[]{2289, 4060}, 45, 83, 3239, 2310, 59, 15),
		DRAWMEN(new int[]{1292}, 36, 0, 771, 1513, 59, 100);
		
		private int[] objectId;
		private int reqLvl, logId, stumpId, respawnTime, decayChance;
		private double xp;
		
		private Trees(int[] objectId, int reqLvl, double xp, int logId, int stumpId, 
				int respawnTime, int decayChance) {
			this.objectId = objectId;
			this.reqLvl = reqLvl;
			this.xp = xp;
			this.logId = logId;
			this.stumpId = stumpId;
			this.respawnTime = respawnTime;
			this.decayChance = decayChance;
		}
		
		private static HashMap<Integer, Trees> trees = new HashMap<Integer, Trees>();
		
		static {
			for (Trees t : Trees.values()) {
				for (int i : t.objectId) {
					trees.put(i, t);
				}
			}
		}
		
		public static Trees getTree(int id) {
			return trees.get(id);
		}
		
		public int[] getObjectId() {
			return objectId;
		}
		
		public int getReqLvl() {
			return reqLvl;
		}
		
		public double getXp() {
			return xp;
		}
		
		public int getLogId() {
			return logId;
		}
		
		public int getStumpId() {
			return stumpId;
		}
		
		public int getRespawnTime() {
			return respawnTime;
		}
		
		public int getDecayChance() {
			return decayChance;
		}
	}
	
	private enum Axes {
		BRONZE(1351, 1, 508, 879, 0),
		IRON(1349, 1, 510, 877, 1),
		STEEL(1353, 6, 512, 875, 2),
		BLACK(1361, 6, 514, 873, 3),
		MITHRIL(1355, 21, 516, 871, 4),
		ADAMANT(1357, 31, 518, 869, 5),
		RUNE(1359, 41, 520, 867, 6),
		DRAGON(6739, 61, 6743, 2846, 7);
		
		private int itemId, reqLvl, headId, anim, bonus;
		
		private Axes(int itemId, int reqLvl, int headId, int anim, int bonus) {
			this.itemId = itemId;
			this.reqLvl = reqLvl;
			this.headId = headId;
			this.anim = anim;
			this.bonus = bonus;
		}
		
		private static HashMap<Integer, Axes> axes = new HashMap<Integer, Axes>();
		
		static {
			for (Axes a : Axes.values()) {
				axes.put(a.getItemId(), a);
			}
		}
		
		public static Axes getAxe(int id) {
			return axes.get(id);
		}

		public int getItemId() {
			return itemId;
		}
		
		public int getReqLvl() {
			return reqLvl;
		}
		
		public int getHeadId() {
			return headId;
		}
		
		public int getAnim() {
			return anim;
		}
		
		public int getBonus() {
			return bonus;
		}
	}
	
	private Tree tree;
	
	private Axes axe;

	@Override
	public void harvest() {
		if ((Math.random() * 100) < tree.definition.getDecayChance()) {
			
		}
	}

	@Override
	public boolean canHarvest() {
		if (!isStarted())
			return false;
		if (tree == null)
			return false;
		return false;
	}

	@Override
	public void giveReward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean stopHarvesting() {
		if (tree == null || axe == null)
			return true;
		return false;
	}

	@Override
	public void playAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	public Axes getAxe() {
		for (Axes axe : Axes.values()) {
			if (getPlayer().getEquipment().contains(new Item(axe.getItemId())) || getPlayer().getInventory().contains(new Item(axe.getItemId()))) {
				return axe;
			}
		}
		return null;
	}

	@Override
	public void startSkill(WorldObject toHarvest) {
		if (Trees.getTree(toHarvest.getObjectId()) == null || getAxe() == null)
			return;
		tree = new Tree(Trees.getTree(toHarvest.getObjectId()), toHarvest);
		axe = getAxe();
		setStarted(true);
	}

}
