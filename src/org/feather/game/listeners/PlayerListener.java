package org.feather.game.listeners;

/**
 * A class containing all listeners for players only.
 * Idea credits to: http://www.rune-server.org/runescape-development/rs2-server/tutorials/344245-hyperion-basic-playerlistener-system.html.
 * However, the code is entirely mine.
 * @author Thock321
 *
 */
public interface PlayerListener extends Listener {
	
	public static interface DefaultPlayerListener extends PlayerListener {
		
		public void login();
		
		public void logout();
		
	}

}
