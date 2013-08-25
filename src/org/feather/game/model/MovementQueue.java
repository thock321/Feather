package org.feather.game.model;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 
 * @author Thock321
 *
 */
public class MovementQueue {
	
	public static final byte[] DIRECTION_DELTA_X = new byte[]{ -1, 0, 1, -1, 1, -1, 0, 1 };
	
	public static final byte[] DIRECTION_DELTA_Y = new byte[]{ 1, 1, 1, 0, 0, -1, -1, -1 };
	
	private ActiveEntity e;
	
	private int walkDirection;
	
	private int runDirection;
	
	private Deque<WalkingPoint> queuedWalkingPoints = new LinkedList<WalkingPoint>();
	
	private boolean isRunning;
	
	public MovementQueue(ActiveEntity e) {
		this.e = e;
	}
	
	private static class WalkingPoint {
		
		private int x;
		
		private int y;
		
		private int direction;
		
		public WalkingPoint(int x, int y, int direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}
		
	}
	
	public int getWalkDirection() {
		return walkDirection;
	}
	
	public int getRunDirection() {
		return runDirection;
	}
	
	public void finish() {
		queuedWalkingPoints.removeFirst();
	}
	
	public void addWalkingPoint(Location loc) {
		if (queuedWalkingPoints.size() < 0)
			reset();
		WalkingPoint lastWalkingPoint = queuedWalkingPoints.peekLast();
		int tmpX = loc.getX() - lastWalkingPoint.x;
		int tmpY = loc.getY() - lastWalkingPoint.y;
		int maxSteps = Math.max(Math.abs(tmpX), Math.abs(tmpY));
		int tmp = 0;
		while (tmp < maxSteps) {
			if (tmpX < 0)
				tmpX++;
			else if (tmpX > 0)
				tmpX--;
			if (tmpY < 0)
				tmpY++;
			else if (tmpY > 0)
				tmpY--;
			takeStep(loc.getX() - tmpX, loc.getY() - tmpY);
		}
	}
	
	private void takeStep(int x, int y) {
		if (queuedWalkingPoints.size() > 50)
			return;
		WalkingPoint lastWalkingPoint = queuedWalkingPoints.peekLast();
		int tmpX = x - lastWalkingPoint.x;
		int tmpY = y - lastWalkingPoint.y;
		if (direction(tmpX, tmpY) >= 0)
			queuedWalkingPoints.add(new WalkingPoint(x, y, direction(tmpX, tmpY)));
	}
	
	public void reset() {
		isRunning = false;
		queuedWalkingPoints.clear();
		queuedWalkingPoints.add(new WalkingPoint(e.getLocation().getX(), e.getLocation().getY(), -1));
	}
	
	public void processMovement() {
		if (e == null)
			return;
		WalkingPoint walkPoint = queuedWalkingPoints.poll();
		WalkingPoint runPoint = null;
		if (e.teleporting() && e.getTeleportDestination() != null) {
			reset();
			e.setLocation(e.getTeleportDestination());
			e.resetTeleportDestination();
		} else {
			if (isRunning)
				runPoint = queuedWalkingPoints.poll();
			if (walkPoint != null) {
				e.setLocation(new Location(e.getLocation().getX() + DIRECTION_DELTA_X[walkPoint.direction], 
						e.getLocation().getY() + DIRECTION_DELTA_Y[walkPoint.direction], e.getLocation().getH()));
			}
			if (runPoint != null) {
				e.setLocation(new Location(e.getLocation().getX() + DIRECTION_DELTA_X[runPoint.direction], 
						e.getLocation().getY() + DIRECTION_DELTA_Y[runPoint.direction], e.getLocation().getH()));
			}
			walkDirection = walkPoint == null ? -1 : walkPoint.direction;
			runDirection = runPoint == null ? -1 : runPoint.direction;
		}
		int diffX = e.getLocation().getX() - e.getPreviousLocation().getRegionX() * 8;
		int diffY = e.getLocation().getY() - e.getPreviousLocation().getRegionY() * 8;
		if (diffX < 16 || diffX >= 88 || diffY < 16 || diffY >= 88) {
			e.setMapRegionChange(true);
		}
	}
	
	public static int direction(int x, int y) {
		if (x < 0) {
			if (y < 0)
				return 5;
			else if (y > 0)
				return 0;
			else
				return 3;
		} else if (x > 0) {
			if (y < 0)
				return 7;
			else if (y > 0)
				return 2;
			else
				return 4;
		} else {
			if (y < 0)
				return 6;
			else if (y > 0)
				return 1;
			else
				return -1;
		}
	}

}
