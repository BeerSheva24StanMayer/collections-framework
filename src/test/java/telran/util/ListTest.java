package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;

    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }

    @Test
    void addListTest() {
        list.add(4, 4);
        assertEquals(array.length + 1, list.size());
    }

    @Test
    void removeListTest() {
        assertEquals(17, list.remove(7));
        assertEquals(3, list.remove(0));
        assertEquals(1, list.remove(2));
    }

    @Test
    void getListTest() {
        assertEquals(9, list.get(5));
    }

    @Test
    void indexOfListTest() {
        assertEquals(3, list.indexOf(1));
    }

    @Test
    void lastIndexOfTest() {
        assertEquals(6, list.lastIndexOf((Integer)100));
    }
}
