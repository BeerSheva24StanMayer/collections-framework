package telran.util;

import java.util.Iterator;
import java.util.Objects;

@SuppressWarnings("unchecked")
public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;

    protected abstract Set<K> getEmptyKeySet();

    @Override
    public V get(Object key) {
        Entry<K, V> pattern = new Entry<>((K) key, null);
        Entry<K, V> entry = set.get(pattern);
        V res = null;
        if (entry != null) {
            res = entry.getValue();
        }
        return res;
    }

    @Override
    public V put(K key, V value) {
        V res = null;
        if (!containsKey(key)) {
            set.add(new Entry<>((K) key, value));
            res = value;
        }
        return res;
    }

    @Override
    public boolean containsKey(Object key) {
        return set.contains(new Entry<>((K) key, null));
    }

    @Override
    public boolean containsValue(Object value) {
        return set.stream().anyMatch(n -> Objects.equals(value, n.getValue()));
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        set.forEach(n -> keySet.add(n.getKey()));
        return keySet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> array = new ArrayList<>();
        set.forEach(n -> array.add(n.getValue()));
        return array;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

}