package com.oceanclean.repository;

import com.oceanclean.domain.SpaceDebris;
import java.util.ArrayList;
import java.util.List;

public class InMemorySpaceDebrisRepository implements SpaceDebrisRepository {
    private final List<SpaceDebris> database = new ArrayList<>();

    @Override
    public void save(SpaceDebris debris) {
        database.add(debris);
    }

    @Override
    public List<SpaceDebris> findAll() {
        return new ArrayList<>(database);
    }
}