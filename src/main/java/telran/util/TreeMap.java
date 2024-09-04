package telran.util;

public class TreeMap<K, V> extends AbstractMap<K, V> {

    @Override
    protected Set<K> getEmptyKeySet() {
        return new TreeSet<>();
    }
    public TreeMap() {
        set = new TreeSet<>();
    }

}