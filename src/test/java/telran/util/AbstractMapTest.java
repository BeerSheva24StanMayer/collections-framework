package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import telran.util.Map.Entry;

public abstract class AbstractMapTest {
    Map<Integer, Integer> map;
    Integer[] array = {3, -10, 20, 1, 10, 8, 100 , 17};

    void setUp() {
        Arrays.stream(array).forEach(n -> map.put(n, newValue(n)));
    }
    abstract <T> void runTest(T[] expected, T[] actual);

    private Integer newValue(Integer key) {
        return key + 1;
    }

    @Test
    void getTest () {
        assertEquals(newValue(array[5]), map.get(array[5]));
        assertEquals(newValue(array[7]), map.get(array[7]));
        assertNull(map.get(10000000));
    }

    @Test
    void putTest () {
        assertNull(map.put(array[0], newValue(array[0])));
        assertEquals(newValue(2000), map.put(2000, newValue(2000)));
        assertEquals(newValue(2000), map.get(2000));
        assertEquals(newValue(5000), map.put(5000, newValue(5000)));
        assertEquals(newValue(5000), map.get(5000));
    }

    @Test
    void containsKeyTest() {
        assertTrue(map.containsKey(array[4]));
        assertFalse(map.containsKey(500));
    }

    @Test
    void containsValueTest() {
        assertTrue(map.containsValue(newValue(array[0])));
        assertFalse(map.containsKey(newValue(1000)));
    }

    @Test
    void keySetTest() {
        Integer[] keys = map.keySet().stream().toArray(Integer[]::new);
        runTest(array, keys);
    }

    @Test
    void valuesTest() {
        Integer[] expected = Arrays.stream(array).map(this::newValue).toArray(Integer[]::new);
        Integer[] values = map.values().stream().toArray(Integer[]::new);

        runTest(expected, values);
    }

    @Test
    void sizeTest() {
        assertEquals(array.length, map.size());
    }

    @Test
    void isEmptyTest() {
        assertFalse(map.isEmpty());
    }
}