package org.feather.game.model.object;

import org.feather.game.model.FaceDirection;
import org.feather.game.model.Location;
import org.feather.game.model.PassiveEntity;

public class WorldObject extends PassiveEntity {
	
	public WorldObject(int objectId, Location location, FaceDirection faceDirection, int objectType) {
		super(location);
		this.objectId = objectId;
		setFaceDirection(faceDirection);
		this.objectType = objectType;
	}
	
	private int objectId;
	
	private int objectType;
	
	public int getObjectId() {
		return objectId;
	}

	public int getObjectType() {
		return objectType;
	}

    private WorldObjectType type;

    public WorldObjectType getType() {
        return type;
    }

}
