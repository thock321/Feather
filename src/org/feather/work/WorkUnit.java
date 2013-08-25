package org.feather.work;

import java.util.LinkedList;
import java.util.List;

import org.feather.Engine;


/**
 * A single work unit.
 * @author Thock321
 *
 */
public abstract class WorkUnit {
	
	/**
	 * The type of work unit this is.
	 */
	private WorkUnitType type;
	
	/**
	 * The work that this work unit is supposed to carry out.
	 */
	List<Work> workToCarryOut = new LinkedList<Work>();
	
	/**
	 * Creates a new work unit.
	 * @param type The type of work unit.
	 * @param work The work this unit is supposed to carry out.
	 */
	public WorkUnit(WorkUnitType type, Work... work) {
		if (work.length == 1 && type != WorkUnitType.SINGLE)
			throw new IllegalArgumentException("A work unit executing one piece of work should be type: SINGLE!");
		else if (work.length > 1 && type == WorkUnitType.SINGLE)
			throw new IllegalArgumentException("A SINGLE type work unit should not have multiple pieces of work to execute!");
		this.type = type;
		for (Work work_ : work) {
			workToCarryOut.add(work_);
		}
	}
	
	/**
	 * Executes the work unit.
	 */
	public void executeWork() {
		if (type == WorkUnitType.SINGLE)
			workToCarryOut.get(0).carryOutWork();
		else if (type == WorkUnitType.MULTIPLE_CONSECUTIVE) {
			for (Work work : workToCarryOut) {
				work.carryOutWork();
			}
		} else if (type == WorkUnitType.MULTIPLE_SIMULTANEOUS) {
			for (final Work work : workToCarryOut) {
				Engine.getEngine().submitRunnable(new Runnable() {

					@Override
					public void run() {
						work.carryOutWork();
					}
					
				});
			}
		}
	}

}
