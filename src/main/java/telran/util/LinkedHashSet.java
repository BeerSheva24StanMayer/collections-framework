package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {
    private final LinkedList<T> list = new LinkedList<>();
    HashMap<T, Node<T>> map = new HashMap<>();

private class LinkedListIterator implements Iterator<T> {
    private Iterator<T> iterator = list.iterator();
    private T current = null;

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        
        current = iterator.next();
        return current;

    }

    @Override
    public void remove() {
        iterator.remove();
        map.remove(current);
    }
}

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            Node<T> node = new Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
            res = true;
        }
        return res;
    }


    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node<T> node = map.get(pattern);
        if (contains(pattern)) {
            map.remove(pattern);
            list.removeNode(node);;
            res = true;
        }
        return res;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(T pattern) {
        return map != null && map.containsKey(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public T get(Object pattern) {
        Node<T> node = map.get(pattern);
        return node != null ? node.obj : null;
    }

}