package org.feather.game.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Thock321
 *
 * @param <T> The entity
 */
public class EntityList<T extends Entity> implements Iterable<T>, List<T> {
	
	private Entity[] entities;
	
	private int size = 0;
	
	private int amtOfE = 0;
	
	public EntityList(int size) {
		if (size < 1)
			throw new IllegalArgumentException();
		this.size = size;
		entities = new Entity[size];
	}

	@Override
	public boolean add(T arg0) {
		for (int i = 1; i < size; i++) {
			if (entities[i] == null) {
				entities[i] = arg0;
				if (entities[i].getIndex() == -1) {
					entities[i].setIndex(i);
				}
				amtOfE++;
				return true;
			}
		}
		return false;
	}
	
	boolean tmp;
	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		tmp = false;
		for (T t : arg0) {
			if (add(t)) {
				tmp = true;
			}
		}
		return tmp;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			entities[i] = null;
		}
	}

	@Override
	public boolean contains(Object arg0) {
		for (Entity ae : entities) {
			if (ae.equals(arg0))
				return true;
		}
		return false;
	}

	int cntAmt = 0;
	@Override
	public boolean containsAll(Collection<?> arg0) {
		for (Object o : arg0) {
			if (contains(o))
				cntAmt++;
		}
		return cntAmt == arg0.size();
	}

	@Override
	public boolean isEmpty() {
		for (Entity ae : entities) {
			if (ae != null)
				return false;
		}
		return true;
	}

	@Override
	public boolean remove(Object arg0) {
		for (int i = 0; i < size; i++) {
			if (entities[i] != null && entities[i].equals(arg0)) {
				amtOfE--;
				entities[i] = null;
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
		for (int i = 0; i < size; i++) {
			if (entities[i] == null)
				continue;
			if (!arg0.contains(entities[i])) {
				entities[i] = null;
				amtOfE--;
				tmp = true;
			}
		}
		return tmp;
	}

	@Override
	public int size() {
		return amtOfE;
	}
	
	public int length() {
		return size;
	}

	@Override
	public Object[] toArray() {
		return entities;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> T[] toArray(T[] arg0) {
		return (T[]) Arrays.copyOf(toArray(), toArray().length, arg0.getClass());
	}
	
	private class EntityIterator<E extends Entity> implements Iterator<E> {
		
		private int currentIndex = 0;
		
		private int previousIndex = -1;

		@Override
		public boolean hasNext() {
			for (int i = currentIndex; i < entities.length; i++) {
				if (entities[i] != null) {
					currentIndex = i;
					return true;
				}
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			E entity = null;
			for (int i = currentIndex; i < entities.length; i++) {
				if (entities[i] != null) {
					entity = (E) entities[i];
					currentIndex = i;
					break;
				}
			}
			if (entity == null)
				throw new NoSuchElementException();
			previousIndex = currentIndex;
			currentIndex++;
			return entity;
		}

		@Override
		public void remove() {
			if (previousIndex == -1)
				throw new IllegalStateException();
			EntityList.this.remove(entities[previousIndex]);
			previousIndex = -1;
		}

		
	}

	@Override
	public Iterator<T> iterator() {
		return new EntityIterator<T>();
	}

	@Override
	public void add(int arg0, T arg1) {
		entities[arg0] = arg1;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int indexOf(Object arg0) {
		for (int i = 0; i < size; i++) {
			if (entities[i] == arg0)
				return i;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object arg0) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (entities[i] == arg0)
				index = i;
		}
		return index;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(int arg0) {
		@SuppressWarnings("unchecked")
		T t = (T) entities[arg0];
		entities[arg0] = null;
		return t;
	}

	@Override
	public T set(int arg0, T arg1) {
		entities[arg0] = arg1;
		return arg1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> subList(int arg0, int arg1) {
		List<T> list = new EntityList<T>((arg1 - arg0) + 1);
		for (int i = arg0; i < arg1 + 1; i++) {
			list.add((T) entities[i]);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int arg0) {
		if (entities[arg0] != null)
			return (T) entities[arg0];
		else
			return null;
	}
	
}
