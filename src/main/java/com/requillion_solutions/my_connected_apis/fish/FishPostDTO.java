package com.requillion_solutions.my_connected_apis.fish;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FishPostDTO {

    @NotEmpty
    String type;
}
