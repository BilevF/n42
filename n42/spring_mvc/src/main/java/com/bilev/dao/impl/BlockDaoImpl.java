package com.bilev.dao.impl;

import com.bilev.dao.api.BlockDao;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.Block;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("blockDao")
public class BlockDaoImpl extends AbstractDaoImpl<Integer, Block> implements BlockDao {
    @Override
    public Block getBlockByType(Block.BlockType blockType) throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("blockType", blockType));
            return (Block) criteria.uniqueResult();

        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }
}
