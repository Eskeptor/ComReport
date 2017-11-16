package ex6;

/* Interface BTIteratorIF */
public interface BTIteratorIF <K extends Comparable, V> {
    public boolean item(K key, V value);
}
