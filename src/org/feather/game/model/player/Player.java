package org.feather.game.model.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import org.feather.net.packet.GamePacket;
import org.feather.net.packet.PacketQueue;
import org.feather.net.packet.PacketSender;
import org.feather.util.ISAACCipher;
import org.feather.game.Configuration;
import org.feather.game.listeners.PlayerListener.DefaultPlayerListener;
import org.feather.game.model.ActiveEntity;
import org.feather.game.model.item.ItemHolder;
import org.jboss.netty.channel.Channel;


public class Player extends ActiveEntity {
	
	{
		addListener(new DefaultPlayerListener() {

			@Override
			public void login() {
				active = true;
			}

			@Override
			public void logout() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public Player(PlayerDetails details, Channel channel, ISAACCipher encryptor) {
		super(Configuration.START_LOCATION);
		this.details = details;
		this.channel = channel;
		this.encryptor = encryptor;
	}
	
	private List<Player> localPlayers = new ArrayList<Player>();
	
	public List<Player> getLocalPlayers() {
		return Collections.unmodifiableList(localPlayers);
	}
	
	public void removeLocalPlayer(Player player) {
		localPlayers.remove(player);
	}
	
	public void addLocalPlayer(Player player) {
		localPlayers.add(player);
	}
	
	private boolean active;
	
	public boolean isActive() {
		return active;
	}
	
	private PlayerDetails details;
	
	public PlayerDetails details() {
		return details;
	}
	
	public void setDetails(PlayerDetails details) {
		this.details = details;
	}
	
	private ISAACCipher encryptor;
	
	public ISAACCipher getEncryotr() {
		return encryptor;
	}
	
	private Channel channel;
	
	public Channel getChannel() {
		return channel;
	}
	
	private PacketSender sendPacket = new PacketSender(this);
	
	public PacketSender getPacketSender() {
		return sendPacket;
	}
	
	private ItemHolder inventory = new ItemHolder(28);
	
	public ItemHolder getInventory() {
		return inventory;
	}

	public void setInventory(ItemHolder inventory) {
		this.inventory = inventory;
	}
	
	private ItemHolder equipment = new ItemHolder(14);
	
	public ItemHolder getEquipment() {
		return equipment;
	}

	public void setEquipment(ItemHolder equipment) {
		this.equipment = equipment;
	}
	
	private Queue<GamePacket> packetQueue = new PacketQueue<GamePacket>();
	
	private GamePacket tmpPak;
	public void sendPacket(GamePacket packet) {
		synchronized (packetQueue) {
			if (!active) {
				packetQueue.add(packet);
			} else {
				if (!packetQueue.isEmpty()) {
					while ((tmpPak = packetQueue.poll()) != null) {
						channel.write(tmpPak);
					}
				}
				channel.write(packet);
			}
		}
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

}
