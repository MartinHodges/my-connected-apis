package com.requillion_solutions.my_connected_apis.fishtank;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface FishTankRepo extends CrudRepository<FishTankEntity, UUID> {

    public Set<FishTankEntity> findAll();
}
