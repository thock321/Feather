package org.feather.net.packet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class PacketQueue<T extends GamePacket> implements Queue<T>, Iterable<T>  {
	
	private static final int SIZE = 200;
	
	private GamePacket[] packets = new GamePacket[SIZE];
	
	private int amtOfE = 0;

	@Override
	public boolean add(T arg0) {
		if (amtOfE >= SIZE)
			return false;
		packets[amtOfE] = arg0;
		amtOfE++;
		return true;
		/*for (int i = 0; i < SIZE; i++) {
			if (packets[i] == null) {
				packets[i] = arg0;
				amtOfE++;
				return true;
			}
		}
		return false;*/
	}

	boolean tmp;
	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		tmp = false;
		for (T t : arg0) {
			if (add(t))
				tmp = true;
		}
		return tmp;
	}

	@Override
	public void clear() {
		for (int i = 0; i < SIZE; i++) {
			packets[i] = null;
		}
	}

	@Override
	public boolean contains(Object arg0) {
		for (GamePacket gp : packets) {
			if (gp.equals(arg0)) {
				return true;
			}
		}
		return false;
	}

	private int i;
	@Override
	public boolean containsAll(Collection<?> arg0) {
		i = 0;
		for (Object o : arg0) {
			if (contains(o))
				i++;
		}
		return i == arg0.size();
	}

	@Override
	public boolean isEmpty() {
		return amtOfE == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new PacketQueueIterator<T>(this);
	}

	/**
	 * Removes an element of the queue.  If the element is present, it is removed, and all elements after it are shifted forward to fill in the gap caused by 
	 * the removal.
	 * @param arg0 The element to be removed from the queue.
	 */
	@Override
	public boolean remove(Object arg0) {
		for (int i = 0; i < SIZE; i++) {
			if (packets[i].equals(arg0)) {
				for (; i < SIZE-1; i++) {
					packets[i] = packets[i+1];
					if (i == SIZE-2) {
						packets[SIZE-1] = null; 
					}
				}
				amtOfE--;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		tmp = false;
		for (Object o : arg0) {
			if (remove(o))
				tmp = true;
		}
		return tmp;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		tmp = false;
		for (int i = 0; i < SIZE; i++) {
			if (packets[i] == null)
				continue;
			if (!arg0.contains(packets[i])) {
				remove(packets[i]);
				tmp = true;
			}
		}
		return tmp;
	}

	@Override
	public int size() {
		return amtOfE;
	}

	@Override
	public Object[] toArray() {
		return packets;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> T[] toArray(T[] arg0) {
		return (T[]) Arrays.copyOf(toArray(), toArray().length, arg0.getClass());
	}
	
	private static class PacketQueueIterator<E extends GamePacket> implements Iterator<E> {
		
		private PacketQueue<E> packetQueue;
		
		private int currentIndex = -1;
		
		private PacketQueueIterator(PacketQueue<E> packetQueue) {
			this.packetQueue = packetQueue;
		}

		@Override
		public boolean hasNext() {
			return packetQueue.amtOfE > currentIndex;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			currentIndex++;
			return (E) packetQueue.packets[currentIndex];
		}

		@Override
		public void remove() {
			if (currentIndex == -1)
				throw new IllegalStateException();
			packetQueue.remove(packetQueue.packets[currentIndex]);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public T element() {
		return (T) packets[0];
	}

	@Override
	public boolean offer(T e) {
		return add(e);//just add it
	}

	@SuppressWarnings("unchecked")
	@Override
	public T peek() {
		if (packets[0] == null)
			return null;
		return (T) packets[0];
	}
	private T t;
	@Override
	public T poll() {
		t = peek();
		remove(t);
		return t;
	}

	@Override
	public T remove() {
		t = element();
		remove(t);
		return t;
	}

}
