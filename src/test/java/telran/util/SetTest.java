package telran.util;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public abstract class SetTest extends CollectionTest {
    Set<Integer> set;

    @Override
    void setUp() {
        super.setUp();
        set = (Set<Integer>) collection;

    }

    @Test
    @Override
    void addExistingTest() {
        assertFalse(set.add(17));

    }
 
    @Test
    void getPatternTest() {
        assertEquals(-10, set.get(-10));
        assertNull(set.get(1000000));
    }

    @Test
    @Override
    void iteratorTest() {
        Integer[] expected = array.clone();
        Integer[] actual = new Integer[collection.size()];
        int index = 0;
        Iterator<Integer> iterator = collection.iterator();

        while (iterator.hasNext()) {
            actual[index++] = iterator.next();
        }

        Arrays.sort(expected);
        Arrays.sort(actual);

        assertArrayEquals(expected, actual);
        assertFalse(iterator.hasNext());
    }
    
}