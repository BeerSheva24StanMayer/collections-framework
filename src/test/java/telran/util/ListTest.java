package telran.util;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

abstract public class ListTest extends CollectionTest{
    List<Integer> list;
    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
        
    }
    @Test
   void removeIndexTest(){
       Integer[] expected = {-10, 20, 1,  8, 100 };
        assertEquals(10, (int)list.remove(4));
        assertEquals(3, (int)list.remove(0));
        assertEquals(17, (int)list.remove(list.size() - 1));
        runTest(expected);
       
        wrongIndicesTest(() -> list.remove(list.size()));
        wrongIndicesTest(() -> list.remove(-1));
   }
   @Test
   void addIndexTest() {
    Integer[] expected = {-3, null, 3, -10, 20, 1, -100, 10, 8, 100 , 17, 17};
    list.add(4, -100);
    list.add(0, -3);
    list.add(list.size(), 17);
    list.add(1, null);
    runTest(expected);
    wrongIndicesTest(() -> list.add(list.size() + 1, 1 ));
    wrongIndicesTest(() -> list.add(-1, 1));
   }
   @Test 
   void indexOfTest() {
    setUpIndexOfMethods();
    assertEquals(list.size() - 2, list.indexOf(17));
    assertEquals(1, list.indexOf(null));
    assertEquals(-1, list.indexOf(10000000));
   }
   private void setUpIndexOfMethods() {
    list.add(17);
    list.add(1, null);
    list.add(2, null);
}
@Test 
   void lastIndexOfTest() {
    setUpIndexOfMethods();
    assertEquals(list.size() - 1, list.lastIndexOf(17));
    assertEquals(2, list.lastIndexOf(null));
    assertEquals(-1, list.lastIndexOf(10000000));
   }
   @Test
   void getTest() {
        assertEquals(3, list.get(0));
        assertEquals(10, list.get(4));
        assertEquals(17, list.get(list.size() - 1));
        wrongIndicesTest(() -> list.get(list.size()));
        wrongIndicesTest(() -> list.get(-1));
   }
   private void wrongIndicesTest(Executable method) {
    assertThrowsExactly(IndexOutOfBoundsException.class, method);
   }
}