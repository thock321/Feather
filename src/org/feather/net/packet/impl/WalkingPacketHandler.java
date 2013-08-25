package org.feather.net.packet.impl;

import org.feather.net.packet.GamePacket;
import org.feather.net.packet.PacketHandler;
import org.feather.game.model.Location;
import org.feather.game.model.player.Player;


public class WalkingPacketHandler extends PacketHandler {

	public WalkingPacketHandler(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handlePacket(GamePacket packet) {
		if (getPlayer().teleporting())
			return;
		int size = packet.getLength();
		if (packet.getId() == 248)
			size -= 14;
		getPlayer().getMovementQueue().reset();
		getPlayer().resetInteractingWith();
		int steps = (size - 5) / 2;
		int[][] path = new int[2][steps];
		int firstX = packet.readLEAShort();
		for (int i = 0; i < steps; i++) {
			path[0][i] = packet.readByte();
			path[1][i] = packet.readByte();
		}
		int firstY = packet.readLEShort();
		packet.readCByte();
		getPlayer().getMovementQueue().addWalkingPoint(new Location(firstX, firstY, getPlayer().getLocation().getH()));
		for (int i = 0; i < steps; i++) {
			path[0][i] += firstX;
			path[0][i] += firstY;
			getPlayer().getMovementQueue().addWalkingPoint(new Location(path[0][i], path[1][i], getPlayer().getLocation().getH()));
		}
		getPlayer().getMovementQueue().finish();
	}

}
