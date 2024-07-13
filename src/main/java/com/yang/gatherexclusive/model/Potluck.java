package com.yang.gatherexclusive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "potluck")
public class Potluck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String itemName;
    private String itemDescription;
    private Integer quantity;
    //eventId
}
