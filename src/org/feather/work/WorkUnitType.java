package org.feather.work;

/**
 * The different types of work units.
 * @author Thock321
 *
 */
public enum WorkUnitType {
	
	/**
	 * A work unit that only carrys out a single piece of work.
	 */
	SINGLE,
	
	/**
	 * A work unit that carrys out multiple pieces of work consecutively.
	 */
	MULTIPLE_CONSECUTIVE,
	
	/**
	 * A work unit that carrys out multiple pieces of work at the same time.
	 */
	MULTIPLE_SIMULTANEOUS;

}
