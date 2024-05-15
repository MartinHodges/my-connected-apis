package com.requillion_solutions.my_connected_apis.fishtank;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FishTankPostDTO {

    @NotEmpty
    String name;
}
