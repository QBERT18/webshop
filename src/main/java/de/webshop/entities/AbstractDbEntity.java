package de.webshop.entities;

import de.webshop.util.DeepCopy;

/**
 * Defines the base methods all DB entities should implement.
 *
 * @param <T> the extending class (used in the deepCopy method return parameter)
 */
public abstract class AbstractDbEntity<T extends AbstractDbEntity<?>> implements DeepCopy<T> {

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
