package de.webshop.util;

import de.webshop.entities.AbstractDbEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Methods for easier use of the {@link AbstractDbEntity::deepCopy} method on collections.
 * Bulk methods are overloaded because type erasure means the utility cannot detect the runtime type of the collection
 * that was passed in, making it unable to create a new instance of it. The overloading helps with that.
 */
public class DeepCopyUtility {

    public static <O extends DeepCopy<O>> List<O> bulkDeepCopy(final List<O> listToCopy) {
        if (listToCopy == null) {
            return null;
        } else {
            return listToCopy.stream().map(DeepCopyUtility::nullSafeDeepCopy).collect(Collectors.toList());
        }
    }

    public static <O extends DeepCopy<O>> Set<O> bulkDeepCopy(final Set<O> setToCopy) {
        if (setToCopy == null) {
            return null;
        } else {
            return setToCopy.stream().map(DeepCopyUtility::nullSafeDeepCopy).collect(Collectors.toSet());
        }
    }

    public static <O extends DeepCopy<O>> O nullSafeDeepCopy(final O object) {
        return object != null ? object.deepCopy() : null;
    }
}
