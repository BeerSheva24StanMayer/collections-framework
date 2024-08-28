package telran.util;

import java.util.Iterator;
import java.util.Objects;

import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
    private static final float DEFAULT_FACTOR = 0.75f;
    List<T>[] hashTable;
    float factor;
    int size;

    private class HashSetIterator implements Iterator<T> {
        Iterator<T> currentIterator;
        Iterator<T> prevIterator;
        int indexIterator;

        private HashSetIterator() {
            this.indexIterator = 0;
            this.currentIterator = getNextElementToIterate();

        }

        private Iterator<T> getNextElementToIterate() {
            Iterator<T> iterator = null;
            while (indexIterator < hashTable.length && iterator == null) {
                List<T> element = hashTable[indexIterator++];
                if (element != null && !element.isEmpty()) {
                    iterator = element.iterator();
                }
            }
            return iterator;
        }

        @Override
        public boolean hasNext() {
            return currentIterator != null && currentIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            prevIterator = currentIterator;
            T element = currentIterator.next();
            if (!currentIterator.hasNext()) {
                currentIterator = getNextElementToIterate();
            }
            return element;
        }

        @Override
        public void remove() {
            if (prevIterator == null) {
                throw new IllegalStateException();
            }
            prevIterator.remove();
            prevIterator = null;
            size--;
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
        List<T>[] tempTable = new List[hashTable.length * 2];
        for (List<T> list : hashTable) {
            if (list != null) {
                list.forEach(obj -> addObjInHashTable(obj, tempTable));
                list.clear(); // ??? for testing if it doesn't work remove this statement
            }
        }
        hashTable = tempTable;

    }

    @Override
    public boolean remove(T pattern) {
        boolean res = contains(pattern);
        if (res) {
            hashTable[getIndex(pattern, hashTable.length)].remove(pattern);
            size--;
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
        List<T> elements = hashTable[getIndex((T) pattern, hashTable.length)];
        if (elements != null) {
            Iterator<T> iterator = elements.iterator();
            while (iterator.hasNext() && !Objects.equals(res, pattern)) {
                res = iterator.next();
            }
        }
        return res;
    }
}