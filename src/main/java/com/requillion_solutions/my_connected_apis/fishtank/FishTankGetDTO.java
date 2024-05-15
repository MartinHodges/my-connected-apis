package com.requillion_solutions.my_connected_apis.fishtank;

import com.requillion_solutions.my_connected_apis.fish.FishGetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class FishTankGetDTO {

    private String id;
    private String name;
    private List<FishGetDTO> fishes;

    static public FishTankGetDTO getDTO(FishTankEntity tank) {
        FishTankGetDTO dto = new FishTankGetDTO(
                tank.getId().toString(),
                tank.getName(),
                FishGetDTO.getDTO(tank.getFishes()));
        return dto;
    }

    static public List<FishTankGetDTO> getDTO(Set<FishTankEntity> tanks) {
        List<FishTankGetDTO> dtos = tanks.stream()
                .map(FishTankGetDTO::getDTO)
                .toList();
        return dtos;
    }
}
