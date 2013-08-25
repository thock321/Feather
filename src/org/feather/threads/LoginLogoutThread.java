package org.feather.threads;


import java.util.LinkedList;
import java.util.Queue;

import org.feather.game.listeners.Listener;
import org.feather.game.listeners.PlayerListener.DefaultPlayerListener;
import org.feather.game.model.player.Player;

public class LoginLogoutThread extends Thread {
	
	private Queue<Player> loginQueue = new LinkedList<Player>();
	
	private Queue<Player> logoutQueue = new LinkedList<Player>();
	
	private static LoginLogoutThread instance;
	
	public static void startUp() {
		instance = new LoginLogoutThread();
		instance.start();
	}
	
	public static LoginLogoutThread getInstance() throws NullPointerException {
		return instance;
	}
	
	public void login(Player player) {
		synchronized (loginQueue) {
			loginQueue.add(player);
		}
	}
	
	public void logout(Player player) {
		synchronized (logoutQueue) {
			logoutQueue.add(player);
		}
	}
	
	private Player tmp;
	
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			synchronized (loginQueue) {
				while ((tmp = loginQueue.poll()) != null) {
					for (Listener listener : tmp.getListeners()) {
						if (listener instanceof DefaultPlayerListener) {
							((DefaultPlayerListener) listener).login();
						}
					}
				}
			}
			synchronized (logoutQueue) {
				while ((tmp = logoutQueue.poll()) != null) {
					for (Listener listener : tmp.getListeners()) {
						if (listener instanceof DefaultPlayerListener) {
							((DefaultPlayerListener) listener).logout();
						}
					}
				}
			}
		}
	}

}
