package org.feather.threads;


import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.feather.game.model.player.Player;
import org.feather.game.model.player.PlayerSaver;

public class MassSaveThread extends Thread {
	
	private static MassSaveThread instance = new MassSaveThread();
	
	private Queue<Player> queue = new LinkedBlockingQueue<Player>();
	
	private Player tmpPlr;
	
	public static MassSaveThread getInstance() {
		return instance;
	}
	
	public void save(Player player) {
		synchronized (queue) {
			queue.add(player);
		}
	}
	
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			synchronized (queue) {
				while ((tmpPlr = queue.poll()) != null) {
					PlayerSaver.save(tmpPlr);
				}
			}
		}
	}

}
