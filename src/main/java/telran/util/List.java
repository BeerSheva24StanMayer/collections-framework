package telran.util;

public interface List<T> extends Collection<T>{
    void add(int index, T obj);
    T remove(int index);
    T get(int index);
    int indexOf(T pattern);
    int lastIndexOf(T pattern);
    @Override
    default boolean remove(T pattern) {
      boolean res = false;
      int index = indexOf(pattern);
      if (index >= 0) {
        res = true;
        remove(index);
      }
      return res;
    }
    @Override
    default boolean contains(T pattern) {
       return indexOf(pattern) >= 0;
    }
    default void checkIndex(int index, boolean sizeInclusive) {
        int size = size();
        int limit = sizeInclusive ? size : size - 1;
        if (index < 0 || index > limit) {
         throw new IndexOutOfBoundsException(index);
        }
     }
}
