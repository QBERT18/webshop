package de.webshop.dataTransferObjects;

public interface DataTransferObject {

    /**
     * @return true if no SQL constraints would be broken when handling the entity, false otherwise.
     * For example, null and empty checks.
     */
    boolean isValid();
}
