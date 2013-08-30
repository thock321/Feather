package org.feather.util.pathfinding;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Albert
 * Date: 8/30/13
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Path {

    private Deque<TilePoint> path = new LinkedList<>();

    public void addPoint(TilePoint point) {
        path.addFirst(point);
    }

    public void addPoints(TilePoint... points) {
        for (TilePoint point : points) {
            addPoint(point);
        }
    }

    public Deque<TilePoint> getPath() {
        return path;
    }

    public Deque<TilePoint> getReversedPath() {
        Deque<TilePoint> reversedPath = new LinkedList<>();
        Deque<TilePoint> copiedPath = path;
        TilePoint point = null;
        while ((point = copiedPath.pollLast()) != null) {
            reversedPath.add(point);
        }
        return reversedPath;
    }

}
