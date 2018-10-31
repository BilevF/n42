package com.bilev.converter;

import com.bilev.model.enums.OptionSelectedType;
import org.springframework.core.convert.converter.Converter;

public class OptionSelectedTypeConverter implements Converter<String, OptionSelectedType> {
    @Override
    public OptionSelectedType convert(String source) {
        try {
            return OptionSelectedType.valueOf(source);
        } catch(Exception e) {
            return OptionSelectedType.NON; // or SortEnum.asc
        }
    }
}