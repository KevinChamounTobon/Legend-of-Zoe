/**
 * Classe générique pour représenter une paire d'éléments
 *
 * ATTENTION : VOUS NE DEVEZ PAS MODIFIER CETTE CLASSE
 */
public final class Paire<K, V> {

    private K key;
    private V value;

    public Paire(K key, V value) {

        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

}