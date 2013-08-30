package org.feather.util.pathfinding;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Albert
 * Date: 8/30/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockedTiles {

    private List<TilePoint> blockedTiles = new LinkedList<>();

    public void addBlockedTile(TilePoint tile) {
        blockedTiles.add(tile);
    }

    public void addBlockedTiles(TilePoint... tiles) {
        for (TilePoint tile : tiles) {
            addBlockedTile(tile);
        }
    }

    public List<TilePoint> getBlockedTiles() {
        return Collections.unmodifiableList(blockedTiles);
    }

    public boolean isBlocked(TilePoint tile) {
        for (TilePoint blockedTile : blockedTiles) {
            if (blockedTile.equals(tile))
                return true;
        }
        return false;
    }

}
