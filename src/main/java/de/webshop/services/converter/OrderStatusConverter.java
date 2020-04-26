package de.webshop.services.converter;

import de.webshop.constants.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Handles the conversion of enum values from {@link OrderStatus} into DB-friendly values.
 */
@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus attribute) {
        if (attribute == null) {
            throw new IllegalStateException("Tried to convert null orderStatus");
        } else {
            return attribute.getDbCode();
        }
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        return Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.getDbCode().equals(dbData))
                .findFirst().orElseThrow(IllegalStateException::new);
    }
}
