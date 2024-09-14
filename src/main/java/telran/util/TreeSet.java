package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.openqa.selenium.ScriptTimeoutException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements SortedSet<T> {
    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {
        Node<T> current = getLeastFrom(root);
        Node<T> prev;

        @Override
        public boolean hasNext() {

            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            prev = current;
            current = getNextCurrent(current);
            return prev.obj;
        }

        @Override
        public void remove() {
            if (prev == null) {
                throw new IllegalStateException();
            }
            removeNode(prev);
            prev = null;
        }

    }

    private Node<T> root;
    private Comparator<T> comparator;
    int size;
    private String printingSymbol = " ";
    private int symbolsPerLevel = 3;

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    public void setPrintingSymbol(String printingSymbol) {
        this.printingSymbol = printingSymbol;
    }

    public void setSymbolsPerLevel(int symbolsPerLevel) {
        this.symbolsPerLevel = symbolsPerLevel;
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            Node<T> node = new Node<>(obj);
            if (root == null) {
                addRoot(node);
            } else {
                addAfterParent(node);
            }
            size++;

        }
        return res;
    }

    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);
        if (comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;
    }

    private void addRoot(Node<T> node) {
        root = node;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node<T> node = getNode(pattern);
        if (node != null) {
            removeNode(node);
            res = true;
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
        return getNode(pattern) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    @Override
    public T get(Object pattern) {
        Node<T> node = getNode((T) pattern);

        return node == null ? null : node.obj;
    }

    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int compRes = 0;
        while (current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? parent : current;
    }

    private Node<T> getNode(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        if (res != null) {
            int compRes = comparator.compare(pattern, res.obj);
            res = compRes == 0 ? res : null;
        }
        return res;

    }

    private Node<T> getParent(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        int compRes = comparator.compare(pattern, res.obj);
        return compRes == 0 ? null : res;

    }

    private Node<T> getLeastFrom(Node<T> node) {
        if (node != null) {

            while (node.left != null) {
                node = node.left;
            }
        }
        return node;
    }

    private Node<T> getGreatestFrom(Node<T> node) {
        if (node != null) {

            while (node.right != null) {
                node = node.right;
            }
        }
        return node;
    }

    private Node<T> getGreaterParent(Node<T> node) {
        Node<T> parent = node.parent;
        while (parent != null && parent.right == node) {
            node = node.parent;
            parent = node.parent;
        }
        return parent;
    }

    private Node<T> getNextCurrent(Node<T> current) {
        return current.right != null ? getLeastFrom(current.right) : getGreaterParent(current);
    }

    private void removeNode(Node<T> node) {
        if (node.left != null && node.right != null) {
            removeJunction(node);
        } else {
            removeNonJunction(node);
        }

        size--;
    }

    private void removeJunction(Node<T> node) {
        Node<T> substitute = getGreatestFrom(node.left);
        node.obj = substitute.obj;
        removeNonJunction(substitute);

    }

    private void removeNonJunction(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> child = node.left != null ? node.left : node.right;
        if (parent == null) {
            root = child; // actual root removing
        } else if (node == parent.left) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        if (child != null) {
            child.parent = parent;
        }
        setNulls(node);

    }

    private void setNulls(Node<T> node) {
        node.obj = null;
        node.parent = node.left = node.right = null;

    }

    @Override
    public T first() {
        return root != null ? getLeastFrom(root).obj : null;
    }

    @Override
    public T last() {
        return root != null ? getGreatestFrom(root).obj : null;
    }

    @Override
    public T floor(T key) {
        Node<T> node = findCeilingFloor(key, 1);
        return node != null ? node.obj : null;
    }

    @Override
    public T ceiling(T key) {
        Node<T> node = findCeilingFloor(key, 0);
        return node != null ? node.obj : null;
    }

    @Override
    public SortedSet<T> subset(T keyFrom, T keyTo) {
        TreeSet<T> subSetSorted = new TreeSet<>(comparator);
        Node<T> current = findCeilingFloor(keyFrom, 0);
        while (current != null && comparator.compare(keyTo, current.obj) > 0) {
            if (comparator.compare(keyFrom, current.obj) <= 0) {
                subSetSorted.add(current.obj);
            }
            current = getNextCurrent(current);
        }
        return subSetSorted;
    }

    private Node<T> findCeilingFloor(T pattern, int key) {
        Node<T> ceiling = null;
        Node<T> floor = null;
        Node<T> node = getParentOrNode(pattern);
        if (node != null) {
            int compRes = comparator.compare(pattern, node.obj);
            if (compRes > 0) {
                floor = node;
                ceiling = getGreaterParent(node);
            } else if (compRes < 0) {
                floor = getLess(node);
                ceiling = node;
            } else {
                floor = ceiling = node;
            }
        }
        return key == 0 ? ceiling : floor;
    }

    private Node<T> getLess(Node<T> node) {
        Node<T> temp = node;
        if (temp.parent != null && Objects.equals(temp.parent.right, temp)) {
            temp = temp.parent;
        }
        return Objects.equals(node, temp) ? null : temp;
    }

    public void displayTreeRotated() {
        displayTreeRotated(root, 0);
    }

    public void displayTreeParentChild() {
        displayTreeParentChildren(root, 0);
    }

    public void displayTreeParentChildren(Node<T> root, int level) {
        if (root != null) {
            displayRootObject(root.obj, level);
            displayTreeParentChildren(root.left, level + 1);
            displayTreeParentChildren(root.right, level + 1);
        }
    }

    public int width() {

        return width(root);
    }

    private int width(Node<T> root) {
        int res = 0;
        if (root != null) {
            if (root.left == null && root.right == null) {
                res = 1;
            } else {
                res = width(root.left) + width(root.right);
            }
        }
        return res;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> root) {
        int res = 0;
        if(root != null) {
            int heightLeft = height(root.left);
            int heightRight = height(root.right);
            res = 1 + Math.max(heightLeft, heightRight);
        }
        return res;
    }

    public void inversion() {
        // TODO
        // reversing the nodes' placement with the same root and with
        // the same nodes
        // only left/right references should be swaped
            toInvert(root);
            comparator = comparator.reversed();
    }

    private void toInvert(Node<T> root) {
        if(root != null) {
        Node<T> temp = root.left;
        root.left = root.right;
        root.right = temp;
        toInvert(root.left);
        toInvert(root.right);
        }
    }

    private void displayTreeRotated(Node<T> root, int level) {
        if (root != null) {
            displayTreeRotated(root.right, level + 1);
            displayRootObject(root.obj, level);
            displayTreeRotated(root.left, level + 1);
        }
    }

    private void displayRootObject(T obj, int level) {
        System.out.printf("%s%s\n", printingSymbol.repeat(level * symbolsPerLevel), obj);
    }
}