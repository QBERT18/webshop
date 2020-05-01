package de.webshop.entities;

/**
 * Defines the base methods all DB entities should implement.
 *
 * @param <T> the extending class (used in the deepCopy method return parameter)
 */
public abstract class AbstractDbEntity<T extends AbstractDbEntity<?>> {

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    /**
     * Creates a deep copy of this object.
     *
     * @return a deep copy of this object.
     */
    public abstract T deepCopy();
}
