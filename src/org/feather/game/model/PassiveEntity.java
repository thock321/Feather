package org.feather.game.model;

/**
 * A passive entity.  This includes ground items and objects.  They are passive because they do not do anything unless interacted with.
 * @author Thock321
 *
 */
public abstract class PassiveEntity extends Entity {

	public PassiveEntity(Location location) {
		super(location);
	}

}
