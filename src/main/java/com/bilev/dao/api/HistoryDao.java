package com.bilev.dao.api;

import com.bilev.model.History;

import java.util.List;

public interface HistoryDao extends AbstractDao<Integer, History> {

    List<History> getUserHistory(int userId);

}
