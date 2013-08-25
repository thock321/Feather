package org.feather.game.model.player;

import java.io.IOException;

import org.feather.game.Configuration;


import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerLoader {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static void load(Player player) {
		try {
			PlayerDetails toLoad = objectMapper.readValue(Configuration.PLAYER_SAVE_LOCATION + player.details().getUsername() + ".feathersave", PlayerDetails.class);
			if (toLoad.getPassword().equals(player.details().getPassword()));
			player.setDetails(toLoad);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
