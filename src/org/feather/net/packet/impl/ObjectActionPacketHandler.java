package org.feather.net.packet.impl;

import org.feather.net.packet.GamePacket;
import org.feather.net.packet.PacketHandler;
import org.feather.game.model.player.Player;


public class ObjectActionPacketHandler extends PacketHandler {

	public ObjectActionPacketHandler(Player player) {
		super(player);
	}
	
	private enum ClickAction {
		FIRST(132),
		SECOND(252),
		THIRD(70),
		FOURTH(234),
		FIFTH(228);
		
		private int packetId;
		
		private ClickAction(int packetId) {
			this.packetId = packetId;
		}
		
		private static ClickAction getClickAction(int packetId) {
			for (ClickAction clickAction : ClickAction.values()) {
				if (clickAction.packetId == packetId)
					return clickAction;
			}
			return null;
		}
	}

	@Override
	public void handlePacket(GamePacket packet) {
		if (ClickAction.getClickAction(packet.getId()) == null)
			return;
		ClickAction clickAction = ClickAction.getClickAction(packet.getId());
		switch (clickAction) {
		case FIRST:
			break;
		case SECOND:
			break;
		case THIRD:
			break;
		case FOURTH:
			break;
		case FIFTH:
			break;
		}
	}

}
