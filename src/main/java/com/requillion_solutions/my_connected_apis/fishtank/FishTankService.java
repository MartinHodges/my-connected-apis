package com.requillion_solutions.my_connected_apis.fishtank;

import com.requillion_solutions.my_connected_apis.fish.FishEntity;
import com.requillion_solutions.my_connected_apis.exceptions.ResourceNotFoundException;
import com.requillion_solutions.my_connected_apis.fish.FishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FishTankService {

    private final FishService fishService;
    private final FishTankRepo fishTankRepo;

    public FishTankEntity getFishTank(UUID id) {

        FishTankEntity fishTank = fishTankRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fish tank not found"));
        return fishTank;
    }

    public Set<FishTankEntity> getAllFishTanks() {

        Set<FishTankEntity> tanks = fishTankRepo.findAll();

        return tanks;
    }

    public FishTankEntity createFishTank(FishTankPostDTO dto) {

        FishTankEntity fishTank = new FishTankEntity();

        fishTank.setName(dto.getName());

        fishTank = fishTankRepo.save(fishTank);

        return fishTank;
    }

    public FishTankEntity updateFishTank(UUID id, FishTankPostDTO dto) {

        FishTankEntity fishTank = fishTankRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fish tank not found"));

        fishTank.setName(dto.getName());

        fishTank = fishTankRepo.save(fishTank);

        return fishTank;
    }

    public void deleteFishTank(UUID id) {

        Optional<FishTankEntity> fishTankFound = fishTankRepo.findById(id);

        if (fishTankFound.isPresent()) {
            FishTankEntity fishTank = fishTankFound.get();
            fishTank.removeAllFishes();
            fishTankRepo.deleteById(id);
        }
    }

    public FishTankEntity addFishToTank(UUID fishId, UUID tankId) {

        FishTankEntity fishTank = fishTankRepo.findById(tankId)
                .orElseThrow(() -> new ResourceNotFoundException("Fish tank not found"));

        FishEntity fish = fishService.getFish(fishId);

        fishTank.addFish(fish);
        fishTank = fishTankRepo.save(fishTank);

        return fishTank;
    }

    public FishTankEntity removeFishFromTank(UUID fishId, UUID tankId) {

        FishTankEntity fishTank = fishTankRepo.findById(tankId)
                .orElseThrow(() -> new ResourceNotFoundException("Fish tank not found"));

        FishEntity fish = fishService.getFish(fishId);

        fishTank.removeFish(fish);
        fishTank = fishTankRepo.save(fishTank);

        return fishTank;
    }
}
