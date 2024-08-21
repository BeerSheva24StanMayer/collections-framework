package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
// import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = {3, -10, 20, 1, 10, 9, 100, 17};
    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }

    @Test
    void addTest() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));
        assertEquals(array.length + 2, collection.size());
    }
@Test
void removeTest() {
    assertTrue(collection.add(10));
    assertFalse(collection.remove(101));
}

@Test
void isEmptyTest() {
    assertFalse(collection.isEmpty());
    collectionReset();
    assertTrue(collection.isEmpty());
}

private void collectionReset() {
    while (!collection.isEmpty()) {
        collection.remove(collection.iterator().next());
    }

}

@Test
void containTest() {
    assertTrue(collection.contains(10));
    assertFalse(collection.contains(101));
}

@Test
void sizeTest() {
    assertEquals(array.length, collection.size());
}

@Test
void streamTest() {
    Stream<Integer> stream = collection.stream();
    assertEquals(array.length, stream.count());
}
@Test
void parallelStreamTest() {
    Stream<Integer> stream = collection.parallelStream();
    assertEquals(array.length, stream.count());
}
@Test
void iteratorTest() {
    int count = 0;
    Iterator<Integer> iterator = collection.iterator();
    while(iterator.hasNext()) {
        assertEquals(array[count++], iterator.next());
    }
}

    }