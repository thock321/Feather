package org.feather.game.model.player;

import java.io.File;
import java.io.IOException;

import org.feather.game.Configuration;


import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerSaver {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static void save(Player player) {
		try {
			objectMapper.writeValue(new File(Configuration.PLAYER_SAVE_LOCATION + player.details().getUsername() + ".feathersave"), player.details());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
