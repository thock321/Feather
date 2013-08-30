package org.feather.util.pathfinding.impl;

import org.feather.util.pathfinding.BlockedTiles;
import org.feather.util.pathfinding.Path;
import org.feather.util.pathfinding.PathFinder;
import org.feather.util.pathfinding.TilePoint;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Albert
 * Date: 8/30/13
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AStarPathFinder implements PathFinder {

    private List<TilePoint> adjacentTiles = new LinkedList<>();

    private Path path = new Path();

    private int movementCost;

    @Override
    public Path findPath(TilePoint start, TilePoint dest, BlockedTiles blockedTiles) {
        //Get all adjacent tiles that are not blocked
        refreshAdjacentTiles(start, blockedTiles);
        //Get the best adjacent tile with the lowest cost + heuristic.
        TilePoint nextTile = getBestAdjacentTile(start, dest, start);
        //If nextTile is a null, then there will be no path, so return a null.
        if (nextTile == null)
            return null;
        //Add valid tile to the path.
        path.addPoint(nextTile);
        //Increase movement cost since we have added one tile.
        movementCost++;
        while (nextTile != null) {
            refreshAdjacentTiles(nextTile, blockedTiles);
            nextTile = getBestAdjacentTile(start, dest, nextTile);
            if (nextTile != null)
                path.addPoint(nextTile);
        }
        return path;
    }

    private int getDistance(TilePoint a, TilePoint b) {
        int deltaX = Math.abs(a.getX() - b.getX());
        int deltaY = Math.abs(a.getY() - b.getY());
        return Math.max(deltaX, deltaY);
    }

    private void refreshAdjacentTiles(TilePoint currentPoint, BlockedTiles blockedTiles) {
        //Clear current adjacent tiles.
        adjacentTiles.clear();
        //Add all new valid adjacent tiles.
        TilePoint tilePoint = new TilePoint(currentPoint.getX(), currentPoint.getY() + 1);
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
        tilePoint = new TilePoint(currentPoint.getX() + 1, currentPoint.getY() + 1);
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
        tilePoint = new TilePoint(currentPoint.getX() + 1, currentPoint.getY());
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
        tilePoint = new TilePoint(currentPoint.getX() + 1, currentPoint.getY() - 1);
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
        tilePoint = new TilePoint(currentPoint.getX(), currentPoint.getY() - 1);
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
        tilePoint = new TilePoint(currentPoint.getX() - 1, currentPoint.getY() - 1);
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
        tilePoint = new TilePoint(currentPoint.getX() - 1, currentPoint.getY());
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
        tilePoint = new TilePoint(currentPoint.getX() - 1, currentPoint.getY() + 1);
        if (!blockedTiles.isBlocked(tilePoint))
            adjacentTiles.add(tilePoint);
    }

    private TilePoint getBestAdjacentTile(TilePoint start, TilePoint dest, TilePoint lastTile) {
        if (adjacentTiles.size() > 0) {
            TilePoint best = null;
            int bestScore = Integer.MAX_VALUE;
            for (TilePoint tile : adjacentTiles) {
                if (!tile.equals(lastTile)) {
                    int score = getDistance(tile, dest) + movementCost + 1;//future movement cost + heuristic
                    if (score < bestScore) {
                        bestScore = score;
                        best = tile;
                    }
                }
            }
            return best;
        }
        return null;
    }

}
