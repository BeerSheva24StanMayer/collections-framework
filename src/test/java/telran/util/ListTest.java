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

        assertEquals(9, list.remove(5));
    }

    @Test
    void getListTest() {
        assertEquals(9, list.get(5));
    }

    @Test
    void indexOfListTest() {
        assertEquals(5, list.indexOf((Integer)9));
    }

    @Test
    void lastIndexOfTest() {
        assertEquals(6, list.lastIndexOf((Integer)100));
    }
}
