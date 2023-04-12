package com.guardjo.simpleboard.admin.domain.converter;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RoleTypesConverter implements AttributeConverter<Set<RoleType>, String> {
    private final static String DELIMITER = ",";
    @Override
    public String convertToDatabaseColumn(Set<RoleType> attribute) {
        return attribute.stream()
                .map(RoleType::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<RoleType> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER))
                .map(RoleType::valueOf)
                .collect(Collectors.toSet());
    }
}
