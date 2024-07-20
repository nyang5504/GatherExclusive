package com.yang.gatherexclusive.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PotluckItemDto {
    private Long id;
    @NotEmpty
    private String itemName;
//    private String itemDesc;
    @NotNull
    private Integer quantity;
}
