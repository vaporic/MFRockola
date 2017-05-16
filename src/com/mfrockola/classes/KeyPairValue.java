package com.mfrockola.classes;

/**
 * Created by Angel C on 15/05/2017.
 */
public class KeyPairValue {
    String key;
    Object value;

    public KeyPairValue(String key, Object value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + ":" + value.toString();
    }
}
