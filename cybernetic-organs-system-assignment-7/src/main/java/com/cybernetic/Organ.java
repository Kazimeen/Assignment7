package com.cybernetic;

import lombok.Data;

/**
 * Represents an organ available for transplantation.
 */
@Data
public class Organ {
    private String id;
    private String name;
    private String bloodType;
    private int weight; // in grams
    private String hlaType;

    /**
     * Constructs a new Organ instance.
     *
     * @param id        Unique identifier for the organ.
     * @param name      Name of the organ (e.g., Heart, Kidney).
     * @param bloodType Blood type of the organ.
     * @param weight    Weight of the organ in grams.
     * @param hlaType   HLA type of the organ.
     */
    public Organ(String id, String name, String bloodType, int weight, String hlaType) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
        this.weight = weight;
        this.hlaType = hlaType;
    }
}
