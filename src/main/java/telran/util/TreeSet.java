package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements Set<T> {
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
        Node<T> currentNode;
        Node<T> prevNode;
        private boolean flNext = false;

        TreeSetIterator() {
            if (root != null) {
                currentNode = getLeftNode(root);
            }

        }

        private Node<T> getLeftNode(Node<T> node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        private void setNextNode() {
            currentNode = currentNode.right == null ? getFirstRight(currentNode)
                    : getLeftNode(currentNode.right);
        }

        private Node<T> getFirstRight(Node<T> node) {
            Node<T> temp = node;
            while (temp.parent != null && temp.parent.right == temp) {
                temp = temp.parent;
            }
            return temp.parent;
        }

        @Override
        public boolean hasNext() {
            flNext = true;
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            prevNode = currentNode;
            T result = currentNode.obj;
            setNextNode();
            return result;

        }

        @Override
        public void remove() {
            if (!flNext) {
                throw new IllegalStateException();
            }
            T temp = prevNode.obj;
            // if (prevNode.right != null || prevNode.left != null) {
            // // if (prevNode.right != null || prevNode.left != null) {
            // currentNode = prevNode;
            // TreeSet.this.remove(prevNode.obj);
            // } else {
            // TreeSet.this.remove(prevNode.obj);
            // }
            TreeSet.this.remove(prevNode.obj);
            if (!Objects.equals(temp, prevNode.obj)) {
                currentNode = prevNode;
            } else {
                prevNode = null;
            }
            flNext = false;
        }
    }

    private Node<T> root;
    private Comparator<T> comparator;
    int size;

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
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
        boolean success = false;
        Node<T> element = getNode(pattern);
        if (element != null) {
            success = true;
            if (element.right == null && element.left == null) {
                removeLastElement(element);
            } else {
                removeJuncElement(element);
            }
            size--;
        }
        return success;
    }

    private void removeLastElement(Node<T> lastNode) {
        if (!Objects.equals(lastNode, root)) {
            if (lastNode.parent.right == null) {
                System.out.println(lastNode.obj);
            }
            if (Objects.equals(lastNode, lastNode.parent.right)) {
                lastNode.parent.right = null;
            } else if (Objects.equals(lastNode, lastNode.parent.left)) {
                lastNode.parent.left = null;
            }
            lastNode.parent = null;
            lastNode = null;
        } else {
            root = lastNode = null;
        }

    }

    private void removeJuncElement(Node<T> juncNode) {
        Node<T> substNode = getSubstNode(juncNode);
        if (substNode.left == null && substNode.right == null) {
            juncNode.obj = substNode.obj;
            removeLastElement(substNode);
        } else {
            if (juncNode.parent != null) {
                makeNetworkingNonRoot(juncNode, substNode);
            } else {
                makeNetworkingRoot(juncNode, substNode);
            }
        }
        substNode = null;
    }

    private void makeNetworkingNonRoot(Node<T> junc, Node<T> subst) {
        if (Objects.equals(subst, junc.right)) {
            subst.parent = junc.parent;
            subst.parent.right = subst;
        } else if (Objects.equals(subst, junc.left)) {
            junc.obj = subst.obj;
            junc.left = subst.left;
            subst.left.parent = junc;
        } else {
            subst.left.parent = subst.parent;
            subst.parent.right = subst.left;
            subst.left.parent = junc;
        }
    }

    private void makeNetworkingRoot(Node<T> junc, Node<T> subst) {
        if (Objects.equals(subst, junc.left)) {
            junc.obj = subst.obj;
            junc.left = subst.left;
            subst.left.parent = junc;
        } else if (!Objects.equals(subst.parent, junc)) {
            subst.left.parent = subst.parent;
            subst.parent.right = subst.left;
            subst.left.parent = junc;
        }
        if (Objects.equals(subst, junc.right)) {
            junc = subst;
            subst.parent = null;
            root = subst;
        }
    }

    private Node<T> getSubstNode(Node<T> juncNode) {
        Node<T> nextNode;
        if (juncNode.left != null) {
            nextNode = juncNode.left;
            while (nextNode.right != null) {
                nextNode = nextNode.right;
            }
        } else {
            nextNode = juncNode.right;
        }
        return nextNode;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T pattern) {
        return getNode(pattern) != null ? true : false;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    @Override
    public T get(Object pattern) {
        Node<T> node = getNode((T) pattern);
        return node != null ? node.obj : null;
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

    @Override
    public int size() {
        return size;
    }

}