package org.feather.game.model.player.update;

import org.feather.game.GameWorld;
import org.feather.game.model.UpdateFlags;
import org.feather.game.model.player.Player;
import org.feather.net.packet.GamePacketCreator;
import org.feather.net.packet.PacketType;

import java.util.List;

/**
 * 
 * @author Thock321
 *
 */
public class PlayerUpdateTask extends UpdateTask {
	
	private Player player;
	
	protected PlayerUpdateTask(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
        GamePacketCreator gamePacket, updateBlock = new GamePacketCreator();
        gamePacket = new GamePacketCreator(81, PacketType.VARIABLE_SHORT);
        gamePacket.startBitAccess();
        updatePlayerMovement(player, gamePacket, false);
        updatePlayer(player, updateBlock, false, false);
        final List<Player> localPlayers = player.getLocalPlayers();
        gamePacket.writeBits(8, localPlayers.size());
        for (Player localPlayer : localPlayers) {
            if (localPlayer != null && GameWorld.getWorld().loggedIn(localPlayer) && !localPlayer.teleporting() && player.getLocation().isWithinDistance(localPlayer.getLocation()) &&
                    localPlayer.isActive()) {
                updatePlayerMovement(localPlayer, gamePacket, true);
                if (localPlayer.getUpdateFlags().updateNeeded()) {
                    updatePlayer(localPlayer, updateBlock, false, true);
                }
            } else {
                player.getLocalPlayers().remove(localPlayer);
                gamePacket.writeBits(1, 1);
                gamePacket.writeBits(2, 3);
            }
        }
        for (Player otherPlayer : GameWorld.getWorld().getRegionHandler().getSurroundingPlayers(player)) {
            if (player.getLocalPlayers().size() > 255)
                break;
            if (otherPlayer.equals(player) || player.getLocalPlayers().contains(otherPlayer))
                continue;
            player.getLocalPlayers().add(otherPlayer);
            addPlayer(player, otherPlayer, gamePacket);
            updatePlayer(otherPlayer, updateBlock, true, true);
        }
        if (!updateBlock.empty()) {
            gamePacket.writeBits(11, 2047);
            gamePacket.finishBitAccess();
            gamePacket.writeBytes(updateBlock.convertIntoPacket().getBuffer());
        } else {
            gamePacket.finishBitAccess();
        }
        player.sendPacket(gamePacket.convertIntoPacket());
	}

    private void addPlayer(Player p, Player otherPlayer, GamePacketCreator packet) {
        packet.writeBits(11, otherPlayer.getIndex());
        packet.writeBits(1, 1);
        packet.writeBits(1, 1);
        int diffX = otherPlayer.getLocation().getX() - p.getLocation().getX();
        int diffY = otherPlayer.getLocation().getY() - p.getLocation().getY();
        packet.writeBits(5, diffY);
        packet.writeBits(5, diffX);
        p.getUpdateFlags().setUpdateNeeded(UpdateFlags.UpdateFlag.APPEARANCE);
        otherPlayer.getUpdateFlags().setUpdateNeeded(UpdateFlags.UpdateFlag.APPEARANCE);
    }

    private void updatePlayerMovement(Player toUpdate, GamePacketCreator packet, boolean otherPlayer) {
        if (otherPlayer) {
            if (player.getMovementQueue().getWalkDirection() == -1) {
                if (player.getUpdateFlags().updateNeeded()) {
                    packet.writeBits(1, 1);
                    packet.writeBits(2, 0);
                } else
                    packet.writeBits(1, 0);
            } else if (player.getMovementQueue().getRunDirection() == -1) {
                packet.writeBits(1, 1);
                packet.writeBits(2, 1);
                packet.writeBits(3, player.getMovementQueue().getWalkDirection());
                packet.writeBits(1, player.getUpdateFlags().updateNeeded() ? 1 : 0);
            } else {
                packet.writeBits(1, 1);
                packet.writeBits(2, 2);
                packet.writeBits(3, player.getMovementQueue().getWalkDirection());
                packet.writeBits(3, player.getMovementQueue().getRunDirection());
                packet.writeBits(1, player.getUpdateFlags().updateNeeded() ? 1 : 0);
            }
        } else {
            if (toUpdate.teleporting() || toUpdate.mapRegionChanged()) {
                packet.writeBits(1, 1);
                packet.writeBits(2, 3);
                packet.writeBits(2, toUpdate.getLocation().getH());
                packet.writeBits(1, toUpdate.teleporting() ? 1 : 0);
                packet.writeBits(1, toUpdate.getUpdateFlags().updateNeeded() ? 1 : 0);
                packet.writeBits(7, toUpdate.getPreviousLocation().getLocalY());
                packet.writeBits(7, toUpdate.getPreviousLocation().getLocalX());
            } else {
                if (player.getMovementQueue().getWalkDirection() == -1) {
                    if (player.getUpdateFlags().updateNeeded()) {
                        packet.writeBits(1, 1);
                        packet.writeBits(2, 0);
                    } else
                        packet.writeBits(1, 0);
                } else if (player.getMovementQueue().getRunDirection() == -1) {
                    packet.writeBits(1, 1);
                    packet.writeBits(2, 1);
                    packet.writeBits(3, player.getMovementQueue().getWalkDirection());
                    packet.writeBits(1, player.getUpdateFlags().updateNeeded() ? 1 : 0);
                } else {
                    packet.writeBits(1, 1);
                    packet.writeBits(2, 1);
                    packet.writeBits(3, player.getMovementQueue().getWalkDirection());
                    packet.writeBits(3, player.getMovementQueue().getRunDirection());
                    packet.writeBits(1, player.getUpdateFlags().updateNeeded() ? 1 : 0);
                }
            }
        }
    }

    private void updatePlayer(Player player, GamePacketCreator gamePacket, boolean forcedAppearanceUpdate, boolean chatUpdate) {
        //TODO
    }

}
