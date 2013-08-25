package org.feather.net.codec;

import org.feather.net.packet.GamePacket;
import org.feather.net.packet.PacketType;
import org.feather.util.ISAACCipher;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;


public class PacketDecoder extends FrameDecoder {
	
	private int id = -1;
	
	private int length = -1;
	
	private ISAACCipher isaacCipher;
	
	public PacketDecoder(ISAACCipher isaacCipher) {
		this.isaacCipher = isaacCipher;
	}

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1,
			ChannelBuffer arg2) throws Exception {
		if (id == -1) {
			if (arg2.readableBytes() > 0) {
				id = (arg2.readUnsignedByte() - isaacCipher.getNextValue()) & 0xFF;//readByte() & 0xFF, I think it's the same as readUnsignedByte().
				length = PACKET_LENGTHS[id];
			} else
				return null;
		}
		if (length == -1) {
			if (arg2.readableBytes() >= length) {
				byte[] bytes = new byte[length];
				arg2.readBytes(bytes);
				ChannelBuffer buffer = ChannelBuffers.buffer(length);
				buffer.writeBytes(bytes);
				GamePacket packet = new GamePacket(id, PacketType.FIXED, buffer);
				id = length = -1;
				return packet;
			}
		}
		return null;
	}
	
	public static final int PACKET_LENGTHS[] = {
			0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 0, 8, 0, // 50
			0, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

}
