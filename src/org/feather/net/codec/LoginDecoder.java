package org.feather.net.codec;


import java.security.SecureRandom;
import java.util.logging.Logger;

import org.feather.net.packet.GamePacketCreator;
import org.feather.util.ISAACCipher;
import org.feather.game.GameWorld;
import org.feather.game.model.player.Player;
import org.feather.game.model.player.PlayerDetails;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class LoginDecoder extends FrameDecoder {
	
	private static final Logger logger = Logger.getLogger(LoginDecoder.class.getName());
	
	private enum State {
		
		PLAYER_CONNECTED,
		LOGGING_PLAYER_IN;
		
	}
	
	private State currentState = State.PLAYER_CONNECTED;

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1,
			ChannelBuffer arg2) throws Exception {
		if (!arg1.isConnected())
			return null;
		switch (currentState) {
		case PLAYER_CONNECTED:
			if (arg2.readableBytes() < 2)
				return null;
			int loginReq = arg2.readUnsignedByte();
			if (loginReq != 14) {
				logger.info("Invalid login request: " + loginReq);
				arg1.close();
				return null;
			}
			arg2.readUnsignedByte();
			arg1.write(new GamePacketCreator().writeLong(0).writeByte((byte) 0).writeLong(new SecureRandom().nextLong()).convertIntoPacket());
			currentState = State.LOGGING_PLAYER_IN;
			break;
		case LOGGING_PLAYER_IN:
			if (arg2.readableBytes() < 2)
				return null;
			int loginType = arg2.readByte();
			if (loginType != 16 && loginType != 18) {
				logger.info("Invalid login type: " + loginType);
				arg1.close();
			}
			int blockLength = arg2.readUnsignedByte();
			int encryptedLoginLength = blockLength - (36 + 1 + 1 + 2);
			if (encryptedLoginLength < 1) {
				logger.info("Encrypted login length is less than 1.");
				arg1.close();
				return null;
			}
			arg2.readUnsignedByte();//magic
			arg2.readUnsignedShort();//client revision
			arg2.readByte();//mem option
			for (int i = 0; i < 9; i++)
				arg2.readInt();
			arg2.readByte();//rsa block length
			int securePayloadId = arg2.readByte();
			if (securePayloadId!= 10) {
				logger.info("Invalid secure payload id: " + securePayloadId);
				arg1.close();
				return null;
			}
			long clientHalf = arg2.readLong();
			long serverHalf = arg2.readLong();
			int[] isaacSeed = { (int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf };
			ISAACCipher in = new ISAACCipher(isaacSeed);
			for (int i = 0; i < isaacSeed.length; i++)
				isaacSeed[i] += 50;
			ISAACCipher out = new ISAACCipher(isaacSeed);
			arg2.readInt();//uid
			String user = getString(arg2);
			String pass = getString(arg2);
			return finishLogin(user, pass, arg1, in, out);
		
		}
		return null;
	}
	
	public static Player finishLogin(String user, String pass, Channel channel, ISAACCipher in, ISAACCipher out) {
		int returnCode = 0;
		if (!user.matches("[A-Za-z0-9 ]+") || user.length() < 3)
			returnCode = 4;
		if (user.length() > 12)
			returnCode = 8;
		if (GameWorld.getWorld().worldFull())
			returnCode = 7;
		channel.getPipeline().remove("decoder");
		channel.getPipeline().addLast("decoder", new PacketDecoder(in));
		Player player = new Player(new PlayerDetails(user, pass), channel, out);
		if (GameWorld.getWorld().loggedIn(player))
			returnCode = 5;
		if (returnCode == 0)
			return player;
		else {
			sendReturnCode(channel, returnCode);
			return null;
		}
	}
	
	public static void sendReturnCode(final Channel channel, final int code) {
		channel.write(new GamePacketCreator().writeByte((byte) code).convertIntoPacket()).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(final ChannelFuture arg0) throws Exception {
				arg0.getChannel().close();
			}
		});
	}
	
	public String getString(ChannelBuffer buffer) {
		StringBuilder bldr = new StringBuilder();
		byte b;
		while (buffer.readable() && (b = buffer.readByte()) != 10)
			bldr.append((char) b);
		return bldr.toString();
	}

}
