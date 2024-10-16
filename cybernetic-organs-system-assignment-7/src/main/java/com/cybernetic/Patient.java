package com.cybernetic;

import lombok.Data;

/**
 * Represents a patient in need of an organ transplant.
 */
@Data
public class Patient {
    private String id;
    private String name;
    private String bloodType;
    private int weight; // in kilograms
    private String hlaType;

    /**
     * Constructs a new Patient instance.
     *
     * @param id        Unique identifier for the patient.
     * @param name      Name of the patient.
     * @param bloodType Blood type of the patient.
     * @param weight    Weight of the patient in kilograms.
     * @param hlaType   HLA type of the patient.
     */
    public Patient(String id, String name, String bloodType, int weight, String hlaType) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
        this.weight = weight;
        this.hlaType = hlaType;
    }
}
