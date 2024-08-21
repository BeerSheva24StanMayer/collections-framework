package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = {3, -10, 20, 1, 10, 9, 100, 17};
    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }
    @Test
    void addTest() {
        collection.add(200);
        collection.add(17);
        assertEquals(array.length + 1, collection.size());
@Test
void sizeTest() {
    assertEquals(array.length, collection.size());
}
//TODO
//all collection tests
    }
