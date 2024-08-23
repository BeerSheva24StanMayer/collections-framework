package telran.util;


import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;


public class LinkedList<T> implements List<T> {
    private static class Node<T> {
        T obj;
        Node<T> next;
        Node<T> prev;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class LinkedListIterator implements Iterator<T> {
        int current = 0;
        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
           return getNode(current++).obj;
        }

    }

    Node<T> head;
    Node<T> tail;
    int size = 0;

    private void checkIndex(int index, boolean sizeInclusive) {
        int limit = sizeInclusive ? size : size - 1;
        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private Node<T> getNode(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void addNode(Node<T> node, int index) {
        if (index == 0) {
            addHead(node);
        } else if (index == size) {
            addTail(node);
        } else {
            addMiddle(node, index);
        }
        size++;
    }

    private void addMiddle(Node<T> nodeToInsert, int index) {
        Node<T> nodeBefore = getNode(index);
        Node<T> nodeAfter = nodeBefore.prev;
        nodeToInsert.next = nodeBefore;
        nodeToInsert.prev = nodeAfter;
        nodeBefore.prev = nodeToInsert;
        nodeAfter.next = nodeToInsert;

    }

    private void addTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    private void addHead(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public boolean add(T obj) {
        Node<T> node = new Node<>(obj);
        addNode(node, size);
        return true;
    }

    @Override
    public boolean remove(T pattern) {
        boolean flag = true;
        int index = indexOf(pattern);
        if(index < 0) {
            flag = false;
        }
        else {
            remove(index);
        }
        return flag;
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
        return indexOf(pattern) > -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        Node<T> node = new Node<>(obj);
        addNode(node, index);
    }

    @Override
    public T remove(int index) {
        checkIndex(index, true);
        Node<T> node = getNode(index);
        if(index == 0) {
            removeHead();
        }
        else if(index == size - 1) {
            removeTail();
        }
        else {
            removeMiddle(node);
        }
        size--;
        return node.obj;

    }

    private void removeHead() {
            if(head == tail) {
                head = tail = null;
            }
            else {
                head = head.next;
            }
    }

    private void removeMiddle(Node<T> node) {
    
        Node<T> nodePrev = node.prev; 
        Node<T> nodeNext = node.next;
        nodePrev.next = nodeNext;
        nodeNext.prev = nodePrev;
    }

    private void removeTail() {
        tail = tail.prev;
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNode(index).obj;
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;
        Node<T> current = head;
        while (index < size && !Objects.equals(pattern, current.obj)) {
            current = current.next;
            index++;
        }
        return index  == size ? -1 : index;
    }

 
    @Override
    public int lastIndexOf(T pattern) {
        int index = size - 1;
        Node<T> current = tail;
        while (0 <= index && !Objects.equals(pattern, current.obj)) {
            current = current.prev;
            index--;
        }
        return index < 0 ? -1 : index;
    }
}