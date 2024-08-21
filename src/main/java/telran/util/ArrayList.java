package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[] array;
    private int size;

    public ArrayList(int capacity) {
        array = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean add(T obj) {
        if (size == array.length) {
            reallocate();
        }
        array[size++] = obj;
        return true;
    }

    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public boolean remove(T pattern) {
        int index = indexOf(pattern);
        boolean removedExist = index >= 0;
        if (removedExist) {
            remove(index);
            size--;
        }
        return removedExist;
    }

    private void reduce() {
        array = Arrays.copyOf(array, array.length / 2);
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
        return indexOf(pattern) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerArrayList();
            
    }
    
    private class InnerArrayList implements Iterator<T>{
        private int current = 0;
        
        @Override
        public boolean hasNext() {
            return current < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next(){
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T)array[current++];
        }
    }


    @Override
    public void add(int index, T obj) {
        if (size == array.length) {
            reallocate();
        }
        if (index < size) {
            size++;
            System.arraycopy(array, index, array, index + 1, size - index - 1);
            array[index] = obj;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        T out = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            out = (T) array[index];
            System.arraycopy(array, index + 1, array, index, size - index);
            size--;
        }
        if (size < array.length / 2) {
            reduce();
        }
        return out;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;
        while (index < size && !(pattern.equals(array[index]))) {
            index++;
        }
        return index == size ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        int index = size - 1;
        while (0 < index && !(pattern.equals(array[index]))) {
            index--;
        }
        return index < 0 ? -1 : index;
    }

}

