package com.bilev.converter;

import com.bilev.dto.BasicOptionDto;
import org.springframework.core.convert.converter.Converter;

public class OptionSelectedTypeConverter implements Converter<String, BasicOptionDto.SelectedOptionType> {
    @Override
    public BasicOptionDto.SelectedOptionType convert(String source) {
        try {
            return BasicOptionDto.SelectedOptionType.valueOf(source);
        } catch(Exception e) {
            return BasicOptionDto.SelectedOptionType.NON; // or SortEnum.asc
        }
    }
}