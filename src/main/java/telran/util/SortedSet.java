package telran.util;

public interface SortedSet<T> extends Set<T>{
    T first();
    T last();
    T floor(T key);
    T ceiling(T key);
    SortedSet<T> subset(T keyFrom, T keyTo);
}
