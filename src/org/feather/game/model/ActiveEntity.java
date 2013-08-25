package org.feather.game.model;


import java.util.HashMap;
import java.util.Map;

import org.feather.game.model.UpdateFlags.UpdateFlag;

/**
 * An active entity.  This includes players and npcs.  They are considered active because they are constantly doing something.
 * @author Albert's_Computer
 *
 */
public abstract class ActiveEntity extends Entity {
	
	public ActiveEntity(Location location) {
		super(location);
	}

	private CombatDetails combatDetails = new CombatDetails();
	
	private Location teleportDestination;
	
	private Location faceLocation;
	
	private Entity opponent;
	
	private boolean teleporting;
	
	private boolean mapRegionChange;
	
	private MovementQueue movementQueue = new MovementQueue(this);
	
	private UpdateFlags updateFlags = new UpdateFlags();
	
	private Map<String, Object> tempVariables = new HashMap<String, Object>();
	
	private Animation currentAnim;
	
	private Graphic currentGraphic;
	
	public abstract void process();
	
	public CombatDetails getCombatDetails() {
		return combatDetails;
	}
	
	public Animation getCurrentAnim() {
		return currentAnim;
	}
	
	public void setCurrentAnim(Animation currentAnim) {
		this.currentAnim = currentAnim;
	}
	
	public Graphic getCurrentGraphic() {
		return currentGraphic;
	}
	
	public void setCurrentGraphic(Graphic currentGraphic) {
		this.currentGraphic = currentGraphic;
	}
	
	public UpdateFlags getUpdateFlags() {
		return updateFlags;
	}
	
	public Location getFaceLocation() {
		return faceLocation;
	}
	
	/**
	 * Sets the opponent this entity is fighting.
	 * @param opponent The opponent.
	 */
	public void setOpponent(Entity opponent) {
		this.opponent = opponent;
	}
	
	/**
	 * Gets the opponent this entity is fighting.
	 * @return The opponent.
	 */
	public Entity getOpponent() {
		return opponent;
	}
	
	public void addTempVariable(String referenceName, Object value) {
		tempVariables.put(referenceName, value);
	}
	
	public Object getTempVariable(String referenceName) {
		return tempVariables.get(referenceName);
	}
	
	public MovementQueue getMovementQueue() {
		return movementQueue;
	}
	
	public boolean mapRegionChanged() {
		return mapRegionChange;
	}
	
	public void setMapRegionChange(boolean mapRegionChange) {
		this.mapRegionChange = mapRegionChange;
	}
	
	public boolean teleporting() {
		return teleporting;
	}
	
	public Location getTeleportDestination() {
		return teleportDestination;
	}
	
	public void setTeleportDestination(Location teleportDestination) {
		this.teleportDestination = teleportDestination;
	}
	
	public void resetTeleportDestination() {
		this.teleportDestination = null;
	}
	
	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
	}
	
	@Override
	public void turnTo(FaceDirection faceDirection) {
		setFaceDirection(faceDirection);
		updateFlags.setUpdateNeeded(UpdateFlag.FACE_COORDINATE);
	}
	
	public FaceDirection faceDirectionBasedOnpreviousLocation() {
		if (location.getX() > previousLocation.getX()) {
			if (location.getY() > previousLocation.getY()) {
				setFaceDirection(FaceDirection.NORTH_EAST);
			} else if (location.getY() < previousLocation.getY()) {
				setFaceDirection(FaceDirection.SOUTH_EAST);
			} else {
				setFaceDirection(FaceDirection.EAST);
			}
		} else if (location.getX() < previousLocation.getX()) {
			if (location.getY() > previousLocation.getY()) {
				setFaceDirection(FaceDirection.NORTH_WEST);
			} else if (location.getY() < previousLocation.getY()) {
				setFaceDirection(FaceDirection.SOUTH_WEST);
			} else {
				setFaceDirection(FaceDirection.WEST);
			}
		} else {
			if (location.getY() > previousLocation.getY()) {
				setFaceDirection(FaceDirection.NORTH);
			} else if (location.getY() < previousLocation.getY()) {
				setFaceDirection(FaceDirection.SOUTH);
			} else {
				setFaceDirection(FaceDirection.SOUTH);
			}
		}
		updateFlags.setUpdateNeeded(UpdateFlag.FACE_COORDINATE);
		return getFaceDirection();
	}
	
	public void turnTo(Location turnToLoc) {
		if (turnToLoc.getX() > location.getX()) {
			if (turnToLoc.getY() > location.getY()) {
				setFaceDirection(FaceDirection.NORTH_EAST);
			} else if (turnToLoc.getY() < location.getY()) {
				setFaceDirection(FaceDirection.SOUTH_EAST);
			} else {
				setFaceDirection(FaceDirection.EAST);
			}
		} else if (turnToLoc.getX() < location.getX()) {
			if (turnToLoc.getY() > location.getY()) {
				setFaceDirection(FaceDirection.NORTH_WEST);
			} else if (turnToLoc.getY() < location.getY()) {
				setFaceDirection(FaceDirection.SOUTH_WEST);
			} else {
				setFaceDirection(FaceDirection.WEST);
			}
		} else {
			if (turnToLoc.getY() > location.getY()) {
				setFaceDirection(FaceDirection.NORTH);
			} else if (turnToLoc.getY() < location.getY()) {
				setFaceDirection(FaceDirection.SOUTH);
			} else {
				setFaceDirection(faceDirectionBasedOnpreviousLocation());
			}
		}
		faceLocation = turnToLoc;
		updateFlags.setUpdateNeeded(UpdateFlag.FACE_COORDINATE);
	}

}
