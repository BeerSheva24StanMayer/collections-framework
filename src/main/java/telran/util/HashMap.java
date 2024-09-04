package telran.util;

public class HashMap<K, V> extends AbstractMap<K, V> {

    @Override
    protected Set<K> getEmptyKeySet() {
        return new HashSet<>();
    }
    public HashMap() {
        set = new HashSet<>();
    }

}