package org.feather.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * A single packet.
 * @author Thock321
 *
 */
public class GamePacket {
	
	private int id;
	
	private PacketType packetType;
	
	private ChannelBuffer buffer;
	
	/**
	 * Creates a new packet.
	 * @param id The packet's id.
	 * @param packetType The packet's type.
	 * @param buffer2 The packet's buffer.
	 */
	public GamePacket(int id, PacketType packetType, ChannelBuffer buffer2) {
		this.id = id;
		this.packetType = packetType;
		this.buffer = buffer2;
	}
	
	/**
	 * Gets the packet's id.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the packet's type.
	 * @return The type.
	 */
	public PacketType getPacketType() {
		return packetType;
	}
	
	/**
	 * Gets the packet's buffer.
	 * @return The buffer.
	 */
	public ChannelBuffer getBuffer() {
		return buffer;
	}
	
	/**
	 * reads the packet's buffer's length.
	 * @return The length.
	 */
	public int getLength() {
		return buffer.capacity();
	}
	
	/**
	 * reads a byte.
	 * @return The byte read.
	 */
	public byte readByte() {
		return buffer.readByte();
	}
	
	/**
	 * reads multiple bytes.
	 * @param bytes The bytes to read.
	 */
	public void readBytes(byte[] bytes) {
		buffer.readBytes(bytes);
	}
	
	/**
	 * reads an unsigned byte.
	 * @return The unsigned byte.
	 */
	public int readUnsignedByte() {
		return readByte() & 0xFF;
	}
	
	/**
	 * reads a type C byte.
	 * @return The type C byte.
	 */
	public byte readCByte() {
		return (byte) -readByte();
	}
	
	/**
	 * reads a type S byte.
	 * @return The type S byte.
	 */
	public byte readSByte() {
		return (byte) (128 - readByte());
	}
	
	/**
	 * reads a V3 byte.
	 * @return The V3 byte.
	 */
	public int readV3Byte() {
		return ((readByte() << 16) & 0xFF) | ((readByte() << 8) & 0xFF) | (readByte() & 0xFF);
	}
	
	/**
	 * reads a type A byte.
	 * @return The type A byte.
	 */
	public byte readAByte() {
		return (byte) (readByte() - 128);
	}
	
	/**
	 * reads a short.
	 * @return The short.
	 */
	public short readShort() {
		return buffer.readShort();
	}
	
	/**
	 * reads an unsigned short.
	 * @return The unsigned short.
	 */
	public int readUnsignedShort() {
		int tmp = 0;
		tmp |= readUnsignedByte() << 8;
		tmp |= readUnsignedByte();
		return tmp;
	}
	
	/**
	 * reads a type A unsigned short.
	 * @return The type A unsigned short.
	 */
	public int readUnsignedShortA() {
		int tmp = 0;
		tmp |= readUnsignedByte() << 8;
		tmp |= (readByte() - 128) & 0xFF;
		return tmp;
	}

	/**
	 * reads a little endian short.
	 * @return The little endian short.
	 */
	public short readLEShort() {
		int tmp = readUnsignedByte() | (readUnsignedByte() << 8);
		if (tmp > 32767)
			tmp -= 0x10000;
		return (short) tmp;
	}
	
	/**
	 * reads a little endian type A short.
	 * @return The little endian type A short.
	 */
	public short readLEAShort() {
		int tmp = (readByte() - 128 & 0xFF) | (readUnsignedByte() << 8);
		if (tmp > 32767)
			tmp -= 0x10000;
		return (short) tmp;
	}
	
	/**
	 * reads a type A short.
	 * @return The type A short.
	 */
	public short readAShort() {
		int tmp = (readUnsignedByte() << 8) | (readByte() - 128 & 0xFF);
		if (tmp > 32767)
			tmp -= 0x10000;
		return (short) tmp;
	}
	
	/**
	 * reads an integer.
	 * @return The integer.
	 */
	public int readInt() {
		return buffer.readInt();
	}
	
	public int readV1Int() {
		return ((readByte() << 24) & 0xFF) | ((readByte() << 16) & 0xFF) | ((readByte() << 8) & 0xFF) | (readByte() & 0xFF);
	}
	
	public int readV2Int() {
		return ((readUnsignedByte() << 24) & 0xFF) | ((readUnsignedByte() << 16) & 0xFF) | ((readUnsignedByte() << 8) & 0xFF) | (readUnsignedByte() & 0xFF);
	}
	
	public long readLong() {
		return buffer.readLong();
	}
	
	public String readString() {
		StringBuilder sb = new StringBuilder();
		byte b = 0;
		while ((b = readByte()) != 10) {
			sb.append((char)b);
		}
		return sb.toString();
	}

}
