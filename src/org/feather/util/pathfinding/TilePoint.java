package org.feather.util.pathfinding;

/**
 * Created with IntelliJ IDEA.
 * User: Albert
 * Date: 8/30/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class TilePoint {

    private int x, y;

    public TilePoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o.equals(this))
            return true;
        if (o instanceof TilePoint) {
            TilePoint point = (TilePoint) o;
            return point.getX() == x && point.getY() == y;
        }
        return false;
    }

}
