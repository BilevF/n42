package com.bilev.converter;

import com.bilev.model.Block;
import org.springframework.core.convert.converter.Converter;

public class BlockConverter implements Converter<String, Block.BlockType> {
    @Override
    public Block.BlockType convert(String source) {
        try {
            return Block.BlockType.valueOf(source);
        } catch(Exception e) {
            return Block.BlockType.NON;
        }
    }
}
