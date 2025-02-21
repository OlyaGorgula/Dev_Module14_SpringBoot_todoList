package com.goit5.todo_list.auth.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FieldReservedBySystemConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute){
        if (attribute.booleanValue()) {
            return EnumYesNo.Y.name();
        } else {
            return EnumYesNo.N.name();
        }
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return EnumYesNo.Y.name().equals(dbData);
    }

    public enum EnumYesNo {
        Y,
        N
    }
}
