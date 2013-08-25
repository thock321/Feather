package org.feather.net.packet;

import org.feather.game.model.player.Player;

public abstract class PacketHandler {
	
	private Player player;
	
	public PacketHandler(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public abstract void handlePacket(GamePacket packet);

}
