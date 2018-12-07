package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.Block;

public interface BlockDao {
    Block getBlockByType(Block.BlockType blockType) throws UnableToFindException;
}
