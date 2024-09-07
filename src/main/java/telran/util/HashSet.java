package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
    private static final float DEFAULT_FACTOR = 0.75f;
    List<T>[] hashTable;
    float factor;
    int size;
    private class HashSetIterator implements Iterator<T> {
       Iterator<T> iterator;
		Iterator<T> prevIterator;
		int iteratorIndex;

		HashSetIterator() {
			iteratorIndex = 0;
			iterator = getIterator(0);
			setIteratorIndex();
		}

		private Iterator<T> getIterator(int index) {
			List<T> list = hashTable[index];
			return list == null ? null : list.iterator();
		}

		@Override
		public boolean hasNext() {

			return iterator != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			prevIterator = iterator;
			T res = iterator.next();
			setIteratorIndex();
			return res;
		}

		private void setIteratorIndex() {
            int limit = hashTable.length - 1;
			while (iteratorIndex < limit && (iterator == null || !iterator.hasNext())) {
                iterator = getIterator(++iteratorIndex);
			}
            if (iteratorIndex == limit && (hashTable[iteratorIndex] == null || !iterator.hasNext())) {
				iterator = null;
			}
			
		}
		@Override
		public void remove() {
			if(prevIterator == null) {
				throw new IllegalStateException();
			}
			prevIterator.remove();
			size--;
			prevIterator = null;
		}

    }

    public HashSet(int hashTableLength, float factor) {
        hashTable = new List[hashTableLength];
        this.factor = factor;
    }

    public HashSet() {
        this(DEFAULT_HASH_TABLE_LENGTH, DEFAULT_FACTOR);
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            if (size >= hashTable.length * factor) {
                hashTableReallocation();
            }

            addObjInHashTable(obj, hashTable);
            size++;
        }
        return res;

    }

    private void addObjInHashTable(T obj, List<T>[] table) {
        int index = getIndex(obj, table.length);
        List<T> list = table[index];
        if (list == null) {
            list = new ArrayList<>(3);
            table[index] = list;
        }
        list.add(obj);
    }

    private int getIndex(T obj, int length) {
        int hashCode = obj.hashCode();
        return Math.abs(hashCode % length);
    }

    private void hashTableReallocation() {
       List<T> []tempTable = new List[hashTable.length * 2];
       for(List<T> list: hashTable) {
        if(list != null) {
            list.forEach(obj -> addObjInHashTable(obj, tempTable));
            list.clear(); 
        }
       }
       hashTable = tempTable;

    }

    @Override
    public boolean remove(T pattern) {
        boolean res = contains(pattern);
		if (res) {
			int index = getIndex(pattern, hashTable.length);
			hashTable[index].remove(pattern);
			size--;
            if(hashTable[index].isEmpty()) {
                hashTable[index] = null;
            }
		}
		return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
       return size == 0;
    }

    @Override
    public boolean contains(T pattern) {
        int index = getIndex(pattern, hashTable.length);
        List<T> list = hashTable[index];
        return list != null && list.contains(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T res = null;
        T tpattern = (T) pattern;
		if (contains(tpattern)) {
			int index = getIndex(tpattern, hashTable.length);
			List<T> list = hashTable[index];
			int indexInList = list.indexOf(tpattern);
			res = list.get(indexInList);

		}
		return res;
    }

}