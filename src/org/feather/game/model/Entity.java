package org.feather.game.model;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.feather.game.listeners.Listener;
import org.feather.game.model.item.grounditem.GroundItem;
import org.feather.game.model.npc.Npc;
import org.feather.game.model.object.WorldObject;
import org.feather.game.model.player.Player;

public abstract class Entity {
	
	public Entity(Location location) {
		setLocation(location);
	}
	
	private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}
	
	public List<Listener> getListeners() {
		return Collections.unmodifiableList(listeners);
	}
	
	private FaceDirection faceDirection;
	
	public FaceDirection getFaceDirection() {
		return faceDirection;
	}
	
	public void turnTo(FaceDirection faceDirection) {
		this.faceDirection = getFaceDirection();
	}
	
	public void setFaceDirection(FaceDirection faceDirection) {
		this.faceDirection = faceDirection;
	}
	
	private int index = -1;
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	protected Location location;
	
	protected Location previousLocation;
	
	public Location getLocation() {
		return location;
	}
	
	public Location getPreviousLocation() {
		return previousLocation;
	}
	
	public void setLocation(Location location) {
		previousLocation = this.location;
		this.location = location;
	}
	
	private Entity interactingWith;
	
	/**
	 * Sets what entity this entity is interacting with.
	 * @param interactingWith The entity this entity is interacting with.
	 */
	public void setInteractingWith(Entity interactingWith) {
		this.interactingWith = interactingWith;
	}
	
	/**
	 * Gets the entity this entity is interacting with.
	 * @return The entity this entity is interacting with.
	 */
	public Entity getInteractingWith() {
		return interactingWith;
	}
	
	/**
	 * Resets the who this entity is interacting with.
	 */
	public void resetInteractingWith() {
		interactingWith = null;
	}
	
	public boolean isPlayer() {
		return this instanceof Player;
	}
	
	public boolean isNpc() {
		return this instanceof Npc;
	}
	
	public boolean isGroundItem() {
		return this instanceof GroundItem;
	}
	
	public boolean isGameObject() {
		return this instanceof WorldObject;
	}

}
