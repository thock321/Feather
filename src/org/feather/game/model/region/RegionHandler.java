package org.feather.game.model.region;

import org.feather.game.model.Entity;
import org.feather.game.model.Location;
import org.feather.game.model.item.grounditem.GroundItem;
import org.feather.game.model.npc.Npc;
import org.feather.game.model.player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A class to handle regions.
 * @author Thock321
 *
 */
public class RegionHandler {

    private Map<Location, Region> regions = new HashMap<Location, Region>();

    private static final int REGION_SIZE = 32;

    /**
     * Gets the region that a location is in.
     * @param l The location.
     * @return The region.
     */
    public Region getRegion(Location l) {
        l = new Location(l.getX()/REGION_SIZE, l.getY()/REGION_SIZE, l.getH());
        if (regions.containsKey(l)) {
            return regions.get(l);
        } else {
            Region r = new Region(l);
            regions.put(l, r);
            return r;
        }
    }

    private Region getRegion(int x, int y, int h) {
        Location l = new Location(x, y, h);
        if (regions.containsKey(l)) {
            return regions.get(l);
        } else {
            Region r = new Region(l);
            regions.put(l, r);
            return r;
        }
    }

    /**
     * Gets the region that an entity is in.
     * @param e The entity.
     * @return The region.
     */
    public Region getRegion(Entity e) {
        return getRegion(e.getLocation());
    }

    /**
     * Gets the surround regions of a region's location.
     * @param l The location of the region.
     * @return The surrounding regions.
     */
    public Region[] getSurroundingRegions(Location l) {
        Region[] regions = new Region[9];
        regions[0] = getRegion(l.getX() - 1, l.getY() + 1, l.getH());
        regions[1] = getRegion(l.getX(), l.getY() + 1, l.getH());
        regions[2] = getRegion(l.getX() + 1, l.getY() + 1, l.getH());
        regions[3] = getRegion(l.getX() + 1, l.getY(), l.getH());
        regions[4] = getRegion(l.getX() + 1, l.getY() - 1, l.getH());
        regions[5] = getRegion(l.getX(), l.getY() - 1, l.getH());
        regions[6] = getRegion(l.getX() - 1, l.getY() - 1, l.getH());
        regions[7] = getRegion(l.getX() - 1, l.getY(), l.getH());
        regions[8] = getRegion(l.getX(), l.getY(), l.getH());
        return regions;
    }

    /**
     * Gets the surrounding players of a location.
     * @param l The location.
     * @return The surrounding players.
     */
    public List<Player> getSurroundingPlayers(Location l) {
        List<Player> players = new LinkedList<Player>();
        for (Region r : getSurroundingRegions(new Location(l.getX()/REGION_SIZE, l.getY()/REGION_SIZE, l.getH()))) {
            for (Player p : r.getPlayers()) {
                players.add(p);
            }
        }
        return players;
    }

    public List<Player> getSurroundingPlayers(Entity e) {
        return getSurroundingPlayers(e.getLocation());
    }

    /**
     * Gets the surrounding npcs of a location.
     * @param l The location.
     * @return The surrounding npcs.
     */
    public List<Npc> getSurroundingNpcs(Location l) {
        List<Npc> npcs = new LinkedList<Npc>();
        for (Region r : getSurroundingRegions(new Location(l.getX()/REGION_SIZE, l.getY()/REGION_SIZE, l.getH()))) {
            for (Npc n : r.getNpcs())
                npcs.add(n);
        }
        return npcs;
    }

    /**
     * Gets the surround ground items of a location.
     * @param l The location.
     * @return The surrounding ground items.
     */
    public List<GroundItem> getSurroundingGroundItems(Location l) {
        List<GroundItem> groundItems = new LinkedList<GroundItem>();
        for (Region r : getSurroundingRegions(new Location(l.getX()/REGION_SIZE, l.getY()/REGION_SIZE, l.getH()))) {
            for (GroundItem gi : r.getGroundItems())
                groundItems.add(gi);
        }
        return groundItems;
    }

}
