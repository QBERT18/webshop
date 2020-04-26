package de.webshop.constants.converter;

import de.webshop.constants.UserPermission;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Handles the conversion of enum values from {@link UserPermission} into DB-friendly values.
 */
@Converter(autoApply = true)
public class UserPermissionConverter implements AttributeConverter<UserPermission, String> {

    @Override
    public String convertToDatabaseColumn(UserPermission attribute) {
        if (attribute == null) {
            throw new IllegalStateException("Tried to convert null userPermission");
        } else {
            return attribute.getDbCode();
        }
    }

    @Override
    public UserPermission convertToEntityAttribute(String dbData) {
        return Arrays.stream(UserPermission.values()).filter(userPermission -> userPermission.getDbCode().equals(dbData))
                .findFirst().orElseThrow(IllegalStateException::new);
    }
}
