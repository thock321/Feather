package org.feather.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * A class that creates packets.
 * @author Thock321
 *
 */
public class GamePacketCreator {

	public static int[] BIT_MASK = new int[32];

	static {
		for (int i = 0; i < BIT_MASK.length; i++)
			BIT_MASK[i] = (1 << i) - 1;
	}

	private int id;

	private PacketType packetType;
	
	private ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

	private int bitPosition;

	public GamePacketCreator(int id, PacketType type) {
		this.id = id;
		this.packetType = type;
	}
	
	public GamePacketCreator(int id) {
		this(id, PacketType.FIXED);
	}
	
	public GamePacketCreator() {
		this(-1);
	}

	public GamePacketCreator writeByte(byte b) {
		buffer.writeByte(b);
		return this;
	}

	public GamePacketCreator writeBytes(byte[] b) {
		buffer.writeBytes(b);
		return this;
	}
	
	public GamePacketCreator writeBytes(ChannelBuffer buffer) {
		this.buffer.writeBytes(buffer);
		return this;
	}
	
	public GamePacketCreator writeShort(short s) {
		buffer.writeShort(s);
		return this;
	}

	public GamePacketCreator writeInt(int i) {
		buffer.writeInt(i);
		return this;
	}

	public GamePacketCreator writeLong(long l) {
		buffer.writeLong(l);
		return this;
	}
	

	public GamePacket convertIntoPacket() {
		return new GamePacket(id, packetType, buffer.duplicate());
	}

	public GamePacketCreator writeString(String string) {
		buffer.writeBytes(string.getBytes());
		buffer.writeByte(10);
		return this;
	}

	public GamePacketCreator writeShortA(int val) {
		buffer.writeByte((val >> 8));
		buffer.writeByte((val + 128));
		return this;
	}

	public GamePacketCreator writeLEShortA(int val) {
		buffer.writeByte((val + 128));
		buffer.writeByte((val >> 8));
		return this;
	}

	public boolean empty() {
		return buffer.writerIndex() == 0;
	}

	public GamePacketCreator startBitAccess() {
		bitPosition = buffer.writerIndex() * 8;
		return this;
	}

	public GamePacketCreator finishBitAccess() {
		buffer.writerIndex((bitPosition + 7) / 8);
		return this;
	}

	public GamePacketCreator writeBits(int amt, int value) {
		if (!buffer.hasArray())
			throw new UnsupportedOperationException();
		buffer.ensureWritableBytes((int) ((bitPosition + 7) / 8 + (Math.ceil(amt / 8D) + 1)));
		byte[] buffer = this.buffer.array();
		int pos = bitPosition >> 3;
		int offset = 8 - (bitPosition & 7);
		bitPosition += amt;
		boolean firstLoop = true;
		while (amt > offset) {
			buffer[pos] &= ~BIT_MASK[offset];
			buffer[pos++] |=  (value >> (amt - offset) & BIT_MASK[offset]);
			amt -= offset;
			if (firstLoop) {
				offset = 8;
				firstLoop = false;
			}
		}
		if (amt == offset) {
			buffer[pos] &= ~BIT_MASK[offset];
			buffer[pos] |= value & BIT_MASK[offset];
			amt -= offset;
		} else {
			buffer[pos] &= ~(BIT_MASK[amt] << (offset - amt));
			buffer[pos] |= (value & BIT_MASK[amt]) << (offset - amt);
		}
		return this;
	}

	public GamePacketCreator transferRemainingBytes(ChannelBuffer buf) {
		buffer.writeBytes(buf);
		return this;
	}

	public GamePacketCreator writeCByte(int val) {
		writeByte((byte)(-val));
		return this;
	}

	public GamePacketCreator writeLEShort(int val) {
		buffer.writeByte((val));
		buffer.writeByte((val >> 8));
		return this;
	}

	public GamePacketCreator writeV1Int(int val) {
		buffer.writeByte((val >> 8));
		buffer.writeByte(val);
		buffer.writeByte((val >> 24));
		buffer.writeByte((val >> 16));
		return this;
	}

	public GamePacketCreator writeV2Int(int val) {
		buffer.writeByte((val >> 16));
		buffer.writeByte((val >> 24));
		buffer.writeByte(val);
		buffer.writeByte((val >> 8));
		return this;
	}

	public GamePacketCreator writeLEInt(int val) {
		buffer.writeByte((val));
		buffer.writeByte((val >> 8));
		buffer.writeByte((val >> 16));
		buffer.writeByte((val >> 24));
		return this;
	}

	public GamePacketCreator writeBytes(byte[] data, int offset, int length) {
		buffer.writeBytes(data, offset, length);
		return this;
	}

	public GamePacketCreator writeAByte(byte val) {
		buffer.writeByte((val + 128));
		return this;
	}

	public GamePacketCreator writeCByte(byte val) {
		buffer.writeByte((-val));
		return this;
	}

	public GamePacketCreator writeSByte(byte val) {
		buffer.writeByte((128 - val));
		return this;
	}

	public GamePacketCreator writeReverse(byte[] is, int offset, int length) {
		for (int i = (offset + length - 1); i >= offset; i--)
			buffer.writeByte(is[i]);
		return this;
	}

	public GamePacketCreator writeAReverse(byte[] is, int offset, int length) {
		for (int i = (offset + length - 1); i >= offset; i--)
			writeAByte(is[i]);
		return this;
	}

	public GamePacketCreator write3Byte(int val) {
		buffer.writeByte((val >> 16));
		buffer.writeByte((val >> 8));
		buffer.writeByte(val);
		return this;
	}

	public GamePacketCreator writeSmart(int val) {
		if (val >= 128)
			writeShort((short)(val + 32768));
		else
			writeByte((byte)val);
		return this;
	}

	public GamePacketCreator writeSignedSmart(int val) {
		if (val >= 128)
			writeShort((short)(val + 49152));
		else
			writeByte((byte)(val + 64));
		return this;
	}

}
