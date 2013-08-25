package org.feather.game;

import java.util.Collections;

import org.feather.Engine;
import org.feather.event.Event;
import org.feather.event.EventHandler;
import org.feather.game.listeners.Listener;
import org.feather.game.listeners.PlayerListener;
import org.feather.game.model.region.RegionHandler;
import org.feather.threads.MassSaveThread;
import org.feather.tick.TickUnit;
import org.feather.game.model.ActiveEntity;
import org.feather.game.model.EntityList;
import org.feather.game.model.PassiveEntity;
import org.feather.game.model.item.grounditem.GroundItem;
import org.feather.game.model.npc.Npc;
import org.feather.game.model.object.WorldObject;
import org.feather.game.model.player.Player;
import org.feather.game.model.player.trade.TradeHandler;
import org.feather.game.model.shop.ShopHandler;


/**
 * Represents a game world.
 * @author Albert's_Computer
 *
 */
public class GameWorld {
	
	private static GameWorld world = new GameWorld();
	
	public static GameWorld getWorld() {
		return world;
	}
	
	private EntityList<ActiveEntity> activeEntities = new EntityList<ActiveEntity>(Configuration.MAX_PLAYERS + Configuration.MAX_NPCS);
	
	private EntityList<Player> players = new EntityList<Player>(Configuration.MAX_PLAYERS);
	
	private EntityList<Npc> npcs = new EntityList<Npc>(Configuration.MAX_NPCS);
	
	private EntityList<PassiveEntity> passiveEntities = new EntityList<PassiveEntity>(Integer.MAX_VALUE);
	
	private EntityList<WorldObject> objects = new EntityList<WorldObject>(Integer.MAX_VALUE);
	
	private EntityList<GroundItem> groundItems = new EntityList<GroundItem>(10000);
	
	private ShopHandler shopHandler = new ShopHandler();
	
	private TradeHandler tradeHandler = new TradeHandler();

    private RegionHandler regionHandler = new RegionHandler();

    public RegionHandler getRegionHandler() {
        return regionHandler;
    }
	
	public TradeHandler getTradeHandler() {
		return tradeHandler;
	}
	
	public ShopHandler getShopHandler() {
		return shopHandler;
	}
	
	public boolean loggedIn(Player p) {
		return activeEntities.contains(p);
	}
	
	public boolean worldFull() {
		return getPlayerCount() >= Configuration.MAX_PLAYERS;
	}
	
	public void saveAllPlayers() {
		for (Player player : players) {
			MassSaveThread.getInstance().save(player);
		}
	}
	
	public int getPlayerCount() {
		int amt = 0;
		for (Player player : players) {
			if (player != null)
				amt++;
		}
		return amt;
	}
	
	public void addPlayer(Player p) {
        for (Listener listener : p.getListeners()) {
            if (listener instanceof PlayerListener.DefaultPlayerListener) {
                final PlayerListener.DefaultPlayerListener dpListener = (PlayerListener.DefaultPlayerListener) listener;
                dpListener.login();
            }
        }
		players.add(p);
		activeEntities.add(p);
	}
	
	public void addNpc(Npc n) {
		npcs.add(n);
		activeEntities.add(n);
	}
	
	public void removePlayer(Player p) {
        for (Listener listener : p.getListeners()) {
            if (listener instanceof PlayerListener.DefaultPlayerListener) {
                final PlayerListener.DefaultPlayerListener dpListener = (PlayerListener.DefaultPlayerListener) listener;
                dpListener.logout();
            }
        }
		players.remove(p);
		activeEntities.remove(p);
	}
	
	public void removeNpc(Npc n) {
		npcs.remove(n);
		activeEntities.remove(n);
	}
	
	public void addGameObject(WorldObject gameObject) {
		objects.add(gameObject);
		passiveEntities.add(gameObject);
	}
	
	public void addGroundItem(GroundItem groundItem) {
		groundItems.add(groundItem);
		passiveEntities.add(groundItem);
	}
	
	public void removeGameObject(WorldObject gameObject) {
		objects.remove(gameObject);
		passiveEntities.remove(gameObject);
	}
	
	public void removeGroundItem(GroundItem groundItem) {
		groundItems.remove(groundItem);
		passiveEntities.remove(groundItem);
	}
	
	public EntityList<Player> getPlayers() {
		return (EntityList<Player>) Collections.unmodifiableList(players);
	}
	
	public EntityList<Npc> getNpcs() {
		return (EntityList<Npc>) Collections.unmodifiableList(npcs);
	}
	
	public EntityList<WorldObject> getGameObjects() {
		return (EntityList<WorldObject>) Collections.unmodifiableCollection(objects);
	}
	
	public EntityList<GroundItem> getGroundItems() {
		return (EntityList<GroundItem>) Collections.unmodifiableCollection(groundItems);
	}
	
	public EntityList<ActiveEntity> getActiveEntities() {
		return (EntityList<ActiveEntity>) Collections.unmodifiableCollection(activeEntities);
	}
	
	public EntityList<PassiveEntity> getPassiveEntities() {
		return (EntityList<PassiveEntity>) Collections.unmodifiableCollection(passiveEntities);
	}
	
	public void submit(TickUnit tickUnit) {
		Engine.getEngine().addTickUnit(tickUnit);
	}
	
	public void submit(Event event) {
		EventHandler.scheduleEvent(event);
	}

}
