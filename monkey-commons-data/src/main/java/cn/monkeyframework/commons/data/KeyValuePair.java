package cn.monkeyframework.commons.data;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Data
@ToString
public class KeyValuePair<K,V> implements Serializable {
    private K key;
    private V value;

    public KeyValuePair(K key, V value){
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }
    public KeyValuePair(){

    }
}
