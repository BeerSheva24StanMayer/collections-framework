package telran.util;

public abstract class ListTest extends CollectionTest{
    List<Integer> list;
    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }
    //TODOs
    //specific test for list
    //where list is the reference to collection being filled in the 
    //method setup of CollectionList
}
