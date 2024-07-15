package com.yang.gatherexclusive.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "potluck")
public class Potluck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String itemDescription;
    private Integer quantity;
    //eventId
}
