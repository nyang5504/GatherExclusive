package com.yang.gatherexclusive.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "potluck_item")
public class PotluckItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
//    private String itemDescription;
    private Integer quantity;
}
