package com.bilev.converter;

import com.bilev.dto.SelectedOptionType;
import org.springframework.core.convert.converter.Converter;

public class OptionSelectedTypeConverter implements Converter<String, SelectedOptionType> {
    @Override
    public SelectedOptionType convert(String source) {
        try {
            return SelectedOptionType.valueOf(source);
        } catch(Exception e) {
            return SelectedOptionType.NON; // or SortEnum.asc
        }
    }
}