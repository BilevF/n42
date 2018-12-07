package com.bilev.tools;

import java.util.ArrayList;
import java.util.List;

public interface Creator<Dto, BasicDto, Entity> {

    int listSize = 5;

    Dto getDto(int id);

    BasicDto getBasicDto(int id);

    Entity getEntity(int id);

    default List<Dto> getDtoList() {
        List<Dto> res = new ArrayList<>();
        for (int i = 0; i < listSize; ++i)
            res.add(getDto(i));
        return res;
    }

    default List<BasicDto> getBasicDtoList() {
        List<BasicDto> res = new ArrayList<>();
        for (int i = 0; i < listSize; ++i)
            res.add(getBasicDto(i));
        return res;
    }

    default List<Entity> getEntityList() {
        List<Entity> res = new ArrayList<>();
        for (int i = 0; i < listSize; ++i)
            res.add(getEntity(i));
        return res;
    }
}
