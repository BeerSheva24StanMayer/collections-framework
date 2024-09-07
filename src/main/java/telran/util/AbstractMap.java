package telran.util;

import java.util.Objects;

@SuppressWarnings("unchecked")
public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;
    protected abstract Set<K> getEmptyKeySet();
    @Override
    public V get(Object key) {
        
        Entry<K, V> entry = getEntry(key);
       V res = null;
       if (entry != null) {
        res = entry.getValue();
       }
       return res;
    }
    private Entry<K, V> getEntry(Object key) {
        Entry<K, V> pattern = getPattern(key);
       Entry<K,V> entry = set.get(pattern);
        return entry;
    }
    private Entry<K, V> getPattern(Object key) {
        return new Entry<>((K)key, null);
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> entry = getEntry(key);
        V res = null;
        if (entry != null) {
            res = entry.getValue();
            entry.setValue(value);
        } else {
            set.add(new Entry<K, V>(key, value));
        }
        return res;
    }

    @Override
    public boolean containsKey(Object key) {
        Entry<K, V> entry = getEntry(key);
        return entry != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return set.stream().anyMatch(e -> Objects.equals(e.getValue(),value));
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        set.stream().map(Entry::getKey).forEach(keySet::add);
        return keySet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
       return set;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> collection = new ArrayList<>(set.size());
        set.stream().map(Entry::getValue).forEach(collection::add);
        return collection;
    }

    @Override
    public int size() {
       return set.size();
    }

    @Override
    public boolean isEmpty() {
       return set.isEmpty();
    }
    @Override
    public V remove (K key) {
        Entry<K, V> entry = getEntry(key);
        V res = null;
        if (entry != null) {
            set.remove(entry);
            res = entry.getValue();
        }
        return res;
    }

}