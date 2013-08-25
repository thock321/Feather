package org.feather.net.packet;

import org.feather.game.model.player.Player;

public class PacketSender {
	
	private Player player;
	
	public PacketSender(Player player) {
		this.player = player;
	}
	
	public PacketSender sendMessage(String message) {
		player.sendPacket(new GamePacketCreator(253, PacketType.VARIABLE).writeString(message).convertIntoPacket());
		return this;
	}
	
	public PacketSender updateSkills() {
		for (int i = 0; i < 21; i++) {
			updateSkill(i);
		}
		return this;
	}
	
	public PacketSender updateSkill(int skill) {
		player.sendPacket(new GamePacketCreator(134, PacketType.FIXED).writeByte((byte)skill).writeV1Int(1/*exp*/).writeByte((byte)1/*skilllevel*/).
				convertIntoPacket());
		return this;
	}
	
	public PacketSender sendDetails() {
		player.sendPacket(new GamePacketCreator(249, PacketType.FIXED).writeAByte((byte)1).writeLEShortA(1/*index*/).convertIntoPacket());
		player.sendPacket(new GamePacketCreator(107, PacketType.FIXED).convertIntoPacket());
		return this;
	}
	
	public PacketSender updateSidebarInterfaces() {
		for (int i = 0; i < SIDEBAR_ICONS_AND_INTERFACES[0].length; i++) {
			updateSidebarInterface(SIDEBAR_ICONS_AND_INTERFACES[0][i], SIDEBAR_ICONS_AND_INTERFACES[1][i]);
		}
		return this;
	}
	
	private static final int[][] SIDEBAR_ICONS_AND_INTERFACES = new int[][]{new int[]{1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 0}, 
		new int[]{3917, 638, 3213, 1644, 5608, 1151, 5065, 5715, 2449, 4445, 147, 6299, 2423},};
	
	public PacketSender updateSidebarInterface(int icon, int interfaceId) {
		player.sendPacket(new GamePacketCreator(71, PacketType.FIXED).writeShort((short) interfaceId).writeAByte((byte)icon).convertIntoPacket());
		return this;
	}
	
	public PacketSender updateEnergy() {
		player.sendPacket(new GamePacketCreator(110, PacketType.FIXED).writeByte((byte) 100 /*run energy*/).convertIntoPacket());
		return this;
	}
	
	public PacketSender sendDialogueAnimation(int interfaceId, int animId) {
		player.sendPacket(new GamePacketCreator(200, PacketType.FIXED).writeShort((short) animId).writeShort((short) interfaceId).convertIntoPacket());
		
        return this;
    }
	
	public PacketSender sendChatInterface(int id) {
		player.sendPacket(new GamePacketCreator(164, PacketType.FIXED).writeLEShort(id).convertIntoPacket());
		return this;
	}
     
    public PacketSender sendPlayerDialogueHead(int interfaceId) {
    	player.sendPacket(new GamePacketCreator(185, PacketType.FIXED).writeLEShortA(interfaceId).convertIntoPacket());
        return this;
    }
     
    public PacketSender sendNPCDialogueHead(int npcId, int interfaceId) {
    	player.sendPacket(new GamePacketCreator(75, PacketType.FIXED).writeLEShortA(npcId).writeLEShortA(interfaceId).convertIntoPacket());
        return this;
    }
    
    public PacketSender sendString(int id, String string) {
    	player.sendPacket(new GamePacketCreator(126, PacketType.VARIABLE_SHORT).writeString(string).writeShortA(id).convertIntoPacket());
    	return this;
    }
    
    public PacketSender sendMapRegion() {
    	player.sendPacket(new GamePacketCreator(73, PacketType.FIXED).writeShortA(player.getLocation().getRegionX() + 6).
    			writeShort((short) (player.getLocation().getRegionY() + 6)).convertIntoPacket());
    	return this;
    }

}
