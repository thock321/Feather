package org.feather.game.model;

import java.util.BitSet;

/**
 * Holds update flags.
 * 
 * @author Graham Edgecombe
 * @author Thock321
 * 
 */
public class UpdateFlags {
	
	/**
	 * The bitSet containing flag data.
	 */
	private BitSet bitSet = new BitSet();

	/**
	 * Represents a single type of update flag.
	 * 
	 * @author Graham Edgecombe
	 * 
	 */
	public enum UpdateFlag {

		/**
		 * Appearance update.
		 */
		APPEARANCE,

		/**
		 * Chat update.
		 */
		CHAT,

		/**
		 * Graphics update.
		 */
		GRAPHICS,

		/**
		 * Animation update.
		 */
		ANIMATION,

		/**
		 * Forced chat update.
		 */
		FORCED_CHAT,

		/**
		 * Interacting entity update.
		 */
		FACE_ENTITY,

		/**
		 * Face coordinate entity update.
		 */
		FACE_COORDINATE,

		/**
		 * Hit update.
		 */
		HIT,

		/**
		 * Hit 2 update/
		 */
		HIT_2,

		/**
		 * Update flag used to transform npc to another.
		 */
		TRANSFORM,

		/**
		 * Update flag used to signify force movement.
		 */
		FORCE_MOVEMENT;
	}
	
	/**
	 * Checks if an update is needed.
	 * @return <code>true</code> if an update is needed, otherwise <code>false</code>.
	 */
	public boolean updateNeeded() {
		return !bitSet.isEmpty();
	}

	/**
	 * Flags (sets to true) a flag.
	 * 
	 * @param flag
	 *            The flag to flag.
	 */
	public void setUpdateNeeded(UpdateFlag flag) {
		bitSet.set(flag.ordinal(), true);
	}

	/**
	 * Sets a flag.
	 * 
	 * @param flag
	 *            The flag.
	 * @param value
	 *            The value.
	 */
	public void setUpdateFlag(UpdateFlag flag, boolean value) {
		bitSet.set(flag.ordinal(), value);
	}

	/**
	 * Gets the value of a flag.
	 * 
	 * @param flag
	 *            The flag to get the value of.
	 * @return The flag value.
	 */
	public boolean getUpdateNeeded(UpdateFlag flag) {
		return bitSet.get(flag.ordinal());
	}

	public void clear() {
		bitSet.clear();
	}

}

