package org.feather.game;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.feather.game.model.Location;

/**
 * Server constants for things that don't change.
 * @author Thock321
 *
 */
public class Configuration {
	
	private static final String CONFIG_FILE_LOCATION = "./configuration.cfg";
	
	public static final void loadConfigurationFile() throws IOException {
		BufferedReader configReader = new BufferedReader(new FileReader(CONFIG_FILE_LOCATION));
		try {
			String line;
			String[] line_;
			while ((line = configReader.readLine()) != null) {
				line_ = line.split("=");
				line_[0] = line_[0].toUpperCase();
				switch (line_[0]) {
				case "PLAYER_SAVE_LOCATION":
					PLAYER_SAVE_LOCATION = line_[1];
					break;
				case "BANK_SIZE":
					BANK_SIZE = Short.parseShort(line_[1]);
					break;
				case "GAME_TICK_DELAY":
					GAME_TICK_DELAY = Short.parseShort(line_[1]);
					break;
				case "PORT":
					PORT = Integer.parseInt(line_[1]);
					break;
				case "START_LOCATION":
					START_LOCATION = new Location(Integer.parseInt(line_[1].split(",")[0]), Integer.parseInt(line_[1].split(",")[1]), 
							Integer.parseInt(line_[1].split(",")[2]));
					break;
				case "MAX_PLAYERS":
					MAX_PLAYERS = Integer.parseInt(line_[1]);
					break;
				case "MAX_NPCS":
					MAX_NPCS = Integer.parseInt(line_[1]);
					break;
				}
			}
		} finally {
			configReader.close();
		}
	}
	
	/**
	 * Skill ids by name
	 */
	public final static int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
			HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
			WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
			CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
			AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19,
			RUNECRAFTING = 20;
	
	/**
	 * The location of player saves.
	 */
	public static String PLAYER_SAVE_LOCATION;
	
	/**
	 * The size of a player's bank.
	 */
	public static short BANK_SIZE;
	
	/**
	 * The delay between ticks.
	 */
	public static short GAME_TICK_DELAY;
	
	/**
	 * The port the server is bound to.
	 */
	public static int PORT;
	
	/**
	 * The location players start at.
	 */
	public static Location START_LOCATION;
	
	/**
	 * The maximum amount of players allowed at once.
	 */
	public static int MAX_PLAYERS;
	
	public static int MAX_NPCS;

}
