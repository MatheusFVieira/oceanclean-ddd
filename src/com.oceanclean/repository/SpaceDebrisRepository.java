package com.oceanclean.repository;

import com.oceanclean.domain.SpaceDebris;
import java.util.List;

public interface SpaceDebrisRepository {
    void save(SpaceDebris debris);
    List<SpaceDebris> findAll();
}