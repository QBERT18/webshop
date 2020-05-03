package de.webshop.constants.converter;

import de.webshop.constants.ProductCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Handles the conversion of enum values from {@link ProductCategory} into DB-friendly values.
 */

@Converter(autoApply = true)
public class ProductCategoryConverter implements AttributeConverter<ProductCategory, String> {

    @Override
    public String convertToDatabaseColumn(ProductCategory attribute) {
        if (attribute == null) {
            throw new IllegalStateException("Tried to convert null productsCategory");
        } else {
            return attribute.getDbCode();
        }
    }

    @Override
    public ProductCategory convertToEntityAttribute(String dbData) {
        return Arrays.stream(ProductCategory.values()).filter(productCategory -> productCategory.getDbCode().equals(dbData))
                .findFirst().orElseThrow(IllegalStateException::new);
    }
}
