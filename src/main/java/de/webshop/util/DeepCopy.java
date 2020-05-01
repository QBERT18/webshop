package de.webshop.util;

public interface DeepCopy<T> {

    /**
     * Creates a deep copy of this object.
     *
     * @return a deep copy of this object.
     */
    public abstract T deepCopy();
}
