package org.feather.util.pathfinding;

/**
 * Created with IntelliJ IDEA.
 * User: Albert
 * Date: 8/30/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PathFinder {

    public Path findPath(TilePoint start, TilePoint dest, BlockedTiles blockedTiles);

}
