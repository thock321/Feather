package org.feather.net.packet.impl;

import org.feather.net.packet.GamePacket;
import org.feather.net.packet.PacketHandler;
import org.feather.game.GameWorld;
import org.feather.game.model.player.Player;


public class TradePlayerPacketHandler extends PacketHandler {

	public TradePlayerPacketHandler(Player player) {
		super(player);
	}

	@Override
	public void handlePacket(GamePacket packet) {
		int tradeeIndex = packet.getId() == 128 ? packet.readUnsignedShort() : packet.readLEShort();
		if (getPlayer().getIndex() == tradeeIndex)
			return;
		GameWorld.getWorld().getTradeHandler().sendTrade(getPlayer(), GameWorld.getWorld().getPlayers().get(tradeeIndex));
	}

}
