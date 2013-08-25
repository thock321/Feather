package org.feather.game.model.player.update;

import java.util.concurrent.ThreadFactory;

/**
 * 
 * @author Thock321
 *
 */
public class LabelledThreadFactory implements ThreadFactory {
	
	private int threadFactoryId = 0;
	
	private String label;
	
	public LabelledThreadFactory(String label) {
		this.label = label;
	}

	@Override
	public Thread newThread(Runnable arg0) {
		threadFactoryId++;
		return new Thread(label + " - id:" + threadFactoryId);
	}
	
	

}
