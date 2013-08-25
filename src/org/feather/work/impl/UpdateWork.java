package org.feather.work.impl;

import org.feather.net.packet.GamePacket;
import org.feather.net.packet.GamePacketCreator;
import org.feather.net.packet.PacketType;
import org.feather.work.Work;
import org.feather.work.WorkUnit;
import org.feather.work.WorkUnitType;
import org.feather.game.GameWorld;
import org.feather.game.model.ActiveEntity;
import org.feather.game.model.UpdateFlags;
import org.feather.game.model.UpdateFlags.UpdateFlag;
import org.feather.game.model.player.Player;


public class UpdateWork extends WorkUnit {

	public UpdateWork(final GameWorld world) {
		super(WorkUnitType.MULTIPLE_CONSECUTIVE, new Work() {

			@Override
			public void carryOutWork() {
				for (ActiveEntity activeEntity : world.getActiveEntities()) {
					activeEntity.getMovementQueue().processMovement();
				}
				for (ActiveEntity activeEntity : world.getActiveEntities()) {
					activeEntity.process();
				}
			}
			
		}, new Work() {
			GamePacketCreator gamePacket, updateBlock;
			@Override
			public void carryOutWork() {
				updateBlock = new GamePacketCreator();
				for (Player player : world.getPlayers()) {
					if (player.mapRegionChanged())
						player.getPacketSender().sendMapRegion();
					gamePacket = new GamePacketCreator(81, PacketType.VARIABLE_SHORT);
					gamePacket.startBitAccess();
					//update movement
					if (player.teleporting() || player.mapRegionChanged()) {
						gamePacket.writeBits(1, 1);
						gamePacket.writeBits(2, 3);
						gamePacket.writeBits(2, player.getLocation().getH());
						gamePacket.writeBits(1, player.teleporting() ? 1 : 0);
						gamePacket.writeBits(1, player.getUpdateFlags().updateNeeded() ? 1 : 0);
						gamePacket.writeBits(7, player.getPreviousLocation().getLocalY());
						gamePacket.writeBits(7, player.getPreviousLocation().getLocalX());
					} else {
						if (player.getMovementQueue().getWalkDirection() == -1) {
							if (player.getUpdateFlags().updateNeeded()) {
								gamePacket.writeBits(1, 1);
								gamePacket.writeBits(2, 0);
							} else
								gamePacket.writeBits(1, 0);
						} else if (player.getMovementQueue().getRunDirection() == -1) {
							gamePacket.writeBits(1, 1);
							gamePacket.writeBits(2, 1);
							gamePacket.writeBits(3, player.getMovementQueue().getWalkDirection());
							gamePacket.writeBits(1, player.getUpdateFlags().updateNeeded() ? 1 : 0);
						} else {
							gamePacket.writeBits(1, 1);
							gamePacket.writeBits(2, 1);
							gamePacket.writeBits(3, player.getMovementQueue().getWalkDirection());
							gamePacket.writeBits(3, player.getMovementQueue().getRunDirection());
							gamePacket.writeBits(1, player.getUpdateFlags().updateNeeded() ? 1 : 0);
						}
					}
					//update this player update(p, p, updateBlock, false, false);
					updatePlayer(player, player, updateBlock, false, false);
					gamePacket.writeBits(8, player.getLocalPlayers().size());
					for (Player localPlayer : player.getLocalPlayers()) {
						if (localPlayer != null && world.loggedIn(localPlayer) && !localPlayer.teleporting() && 
								player.getLocation().isWithinDistance(localPlayer.getLocation()) && localPlayer.isActive()) {
							
						}
					}
				}
			}
			
		});
	}
	
	private static void updatePlayer(Player player, Player otherPlayer, GamePacketCreator packet, boolean forceAppearance, boolean chat) {
		if (!player.getUpdateFlags().updateNeeded() && !forceAppearance)
			return;
		if (player.getUpdateFlags().updateNeeded()) {
			synchronized (player) {
				if (/*player has cache update block &&*/ otherPlayer != player && !forceAppearance && chat) {
					//packet.writeByte(other player get cahce update block.getpayload.flip)
					return;
				}
				GamePacketCreator block = new GamePacketCreator();
				int bitMask = 0;
				UpdateFlags updateFlags = player.getUpdateFlags();
				if (updateFlags.getUpdateNeeded(UpdateFlag.GRAPHICS)) {
					bitMask |= 0x100;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.ANIMATION)) {
					bitMask |= 0x8;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.FORCED_CHAT)) {
					bitMask |= 0x4;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.CHAT)) {
					bitMask |= 0x80;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.FACE_ENTITY)) {
					bitMask |= 0x1;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.APPEARANCE)) {
					bitMask |= 0x10;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.FACE_COORDINATE)) {
					bitMask |= 0x2;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.HIT)) {
					bitMask |= 0x20;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.HIT_2)) {
					bitMask |= 0x200;
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.FORCE_MOVEMENT)) {
					bitMask |= 0x400;
				}
				if (bitMask >= 0x100) {
					bitMask |= 0x40;
					block.writeByte((byte)(bitMask & 0xFF));
					block.writeByte((byte)(bitMask >> 8));
				} else {
					block.writeByte((byte)bitMask);
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.GRAPHICS)) {
					if (block != null && player.getCurrentGraphic() != null) {
						block.writeLEShort(player.getCurrentGraphic().getId());
						block.writeInt(player.getCurrentGraphic().getH() * 65536);
					}
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.APPEARANCE)) {
					/*Appearance app = otherPlayer.getAppearance();
					Container eq = otherPlayer.getEquipment();

					PacketBuilder playerProps = new PacketBuilder();
					playerProps.put((byte) app.getGender()); // gender
					playerProps.put((byte) 0); // skull icon

					for (int i = 0; i < 4; i++) {
						if (eq.isSlotUsed(i)) {
							playerProps.putShort((short) 0x200 + eq.get(i).getId());
						} else {
							playerProps.put((byte) 0);
						}
					}
					if (eq.isSlotUsed(Equipment.SLOT_CHEST)) {
						playerProps.putShort((short) 0x200 + eq.get(Equipment.SLOT_CHEST).getId());
					} else {
						playerProps.putShort((short) 0x100 + app.getChest()); // chest
					}
					if (eq.isSlotUsed(Equipment.SLOT_SHIELD)) {
						playerProps.putShort((short) 0x200 + eq.get(Equipment.SLOT_SHIELD).getId());
					} else {
						playerProps.put((byte) 0);
					}
					Item chest = eq.get(Equipment.SLOT_CHEST);
					if (chest != null) {
						if (!Equipment.is(EquipmentType.PLATEBODY, chest)) {
							playerProps.putShort((short) 0x100 + app.getArms());
						} else {
							playerProps.putShort((short) 0x200 + chest.getId());
						}
					} else {
						playerProps.putShort((short) 0x100 + app.getArms());
					}
					if (eq.isSlotUsed(Equipment.SLOT_BOTTOMS)) {
						playerProps.putShort((short) 0x200 + eq.get(Equipment.SLOT_BOTTOMS).getId());
					} else {
						playerProps.putShort((short) 0x100 + app.getLegs());
					}
					Item helm = eq.get(Equipment.SLOT_HELM);
					if (helm != null) {
						if (!Equipment.is(EquipmentType.FULL_HELM, helm) && !Equipment.is(EquipmentType.FULL_MASK, helm)) {
							playerProps.putShort((short) 0x100 + app.getHead());
						} else {
							playerProps.put((byte) 0);
						}
					} else {
						playerProps.putShort((short) 0x100 + app.getHead());
					}
					if (eq.isSlotUsed(Equipment.SLOT_GLOVES)) {
						playerProps.putShort((short) 0x200 + eq.get(Equipment.SLOT_GLOVES).getId());
					} else {
						playerProps.putShort((short) 0x100 + app.getHands());
					}
					if (eq.isSlotUsed(Equipment.SLOT_BOOTS)) {
						playerProps.putShort((short) 0x200 + eq.get(Equipment.SLOT_BOOTS).getId());
					} else {
						playerProps.putShort((short) 0x100 + app.getFeet());
					}
					boolean fullHelm = false;
					boolean hat = false;
					if (helm != null) {
						hat = Equipment.is(EquipmentType.HAT, helm);
						fullHelm = !Equipment.is(EquipmentType.FULL_HELM, helm);
					}
					if ((!hat && fullHelm) || app.getGender() == 1) {
						playerProps.put((byte) 0);
					} else {
						playerProps.putShort((short) 0x100 + app.getBeard());
					}

					playerProps.put((byte) app.getHairColour()); // hairc
					playerProps.put((byte) app.getTorsoColour()); // torsoc
					playerProps.put((byte) app.getLegColour()); // legc
					playerProps.put((byte) app.getFeetColour()); // feetc
					playerProps.put((byte) app.getSkinColour()); // skinc
					int stand = otherPlayer.getStandAnimation().getId();
					if (player.getRegion().getPlayers().size() > 30 || otherPlayer.getRegion().getPlayers().size() > 30) {
						stand = 999;
					}
					playerProps.putShort((short) stand); // stand
					playerProps.putShort((short) otherPlayer.getWalkAnimation().getId()); // stand
																							// turn
																							// 0x337

					playerProps.putShort((short) otherPlayer.getWalkAnimation().getId()); // walk
					playerProps.putShort((short) otherPlayer.getWalkAnimation().getId()); // turn
																							// 180
																							// 0x334
					playerProps.putShort((short) otherPlayer.getWalkAnimation().getId()); // turn
																							// 90
																							// cw
																							// 0x335
					playerProps.putShort((short) otherPlayer.getWalkAnimation().getId()); // turn
																							// 90
																							// ccw
																							// 0x336
					playerProps.putShort((short) otherPlayer.getRunAnimation().getId()); // run
																							// 0x338

					playerProps.putLong(otherPlayer.getNameAsLong()); // player name
					playerProps.put((byte) otherPlayer.getSkills().getCombatLevel()); // combat
																						// level
					playerProps.putShort(0); // (skill-level instead of combat-level)
												// otherPlayer.getSkills().getTotalLevel());
												// // total level

					Packet propsPacket = playerProps.toPacket();

					packet.putByteC(propsPacket.getLength());
					packet.put(propsPacket.getPayload());*/
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.FACE_ENTITY)) {
					block.writeLEShort(player.getInteractingWith() == null ? -1 : !player.getInteractingWith().isPlayer() ? 
							player.getInteractingWith().getIndex() : player.getInteractingWith().getIndex() + 32768);
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.FACE_COORDINATE)) {
					if (player.getFaceLocation() == null) {
						block.writeLEShortA(0);
						block.writeLEShort(0);
					} else {
						block.writeLEShortA(player.getFaceLocation().getX() * 2 + 1);
						block.writeLEShort(player.getFaceLocation().getY() * 2 + 1);
					}
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.HIT)) {
					//block.writeByte((byte) otherPlayer.getDamage().getHitDamage1());
					//block.writeAByte(otherPlayer.getDamage().getHitType1());
					//block.writeCByte(player.getPlayerSkills().getLevel(3));
					//block.writeByte((byte) player.getPlayerSkills().getLevelForExperience(3));
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.HIT_2)) {
					//block.writeByte((byte) otherPlayer.getDamage().getHitDamage2());
					//block.writeSByte((byte) otherPlayer.getDamage().getHitType2());
					//block.writeByte((byte) player.getPlayerSkills().getLevel(3));
					//block.writeCByte(player.getPlayerSkills().getLevelForExperience(3));
				}
				if (updateFlags.getUpdateNeeded(UpdateFlag.FORCE_MOVEMENT)) {
					/*packet.writeCByte((byte) (player.getLocation().getLocalX() + otherPlayer.getForceWalk()[0])); // first
					// x
					// to
					// go
					// to
packet.writeSByte((byte) (player.getLocation().getLocalY() + otherPlayer.getForceWalk()[1])); // first
					// y
					// to
					// go
					// to
packet.writeSByte((byte) (player.getLocation().getLocalX() + otherPlayer.getForceWalk()[2])); // second
					// x
					// to
					// go
					// to
packet.writeCByte((byte) (player.getLocation().getLocalY() + otherPlayer.getForceWalk()[3])); // second
					// y
					// to
					// go
					// to
packet.writeShort(otherPlayer.getForceWalk()[4]); // speed going to
//location
packet.writeAShort(otherPlayer.getForceWalk()[5]); // speed returning to
//original location
packet.writeByte((byte) otherPlayer.getForceWalk()[6]); // direction&*/
				}
				GamePacket blockPacket = block.convertIntoPacket();
				packet.writeBytes(blockPacket.getBuffer());
			}
		}
	}

}
