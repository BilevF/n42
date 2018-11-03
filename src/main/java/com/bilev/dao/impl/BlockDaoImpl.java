package com.bilev.dao.impl;

import com.bilev.dao.api.BlockDao;
import com.bilev.model.Block;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("blockDao")
public class BlockDaoImpl extends AbstractDao<Integer, Block> implements BlockDao {
    @Override
    public Block getBlockByType(Block.BlockType blockType) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("type", blockType.name()));
        return (Block) criteria.uniqueResult();
    }
}
