package telran.util;

public class SortedSetTest extends SetTest{
    SortedSet<Integer> sortedSet;
    @Override
    void setUp(){
        super.setUp();
        sortedSet = (SortedSet<Integer>) collection;

    }
}
