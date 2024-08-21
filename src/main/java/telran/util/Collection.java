package telran.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Collection<T> extends Iterable<T> {
    boolean add(T obj);
    boolean remove(T pattern);
    int size();
    boolean isEmpty();
    boolean contains(T pattern);
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
    default Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
