package org.feather.game.model.player;

/**
 * The players details.  These details are saved in the player's character file.
 * @author Thock321
 *
 */
public class PlayerDetails {
	
	public PlayerDetails(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	private String username;
	
	public String getUsername() {
		return username;
	}
	
	private String password;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
