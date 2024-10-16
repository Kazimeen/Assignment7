package com.cybernetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Analyzes and calculates compatibility scores between organs and patients.
 */
public class OrganCompatibilityAnalyzer {
    private List<Organ> organs;
    private List<Patient> patients;

    /**
     * Constructs a new OrganCompatibilityAnalyzer instance.
     */
    public OrganCompatibilityAnalyzer() {
        organs = new ArrayList<>();
        patients = new ArrayList<>();
    }

    /**
     * Adds an organ to the analyzer.
     *
     * @param organ The organ to be added.
     */
    public void addOrgan(Organ organ) {
        organs.add(organ);
    }

    /**
     * Adds a patient to the analyzer.
     *
     * @param patient The patient to be added.
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    /**
     * Retrieves a list of organs compatible with the specified patient.
     *
     * @param patient The patient for whom compatibility is checked.
     * @return A list of compatible organs.
     */
    public List<Organ> getCompatibleOrgans(Patient patient) {
        return organs.stream()
                .filter(organ -> calculateCompatibilityScore(organ, patient) > 0)
                .collect(Collectors.toList());
    }

    /**
     * Calculates compatibility scores for all patients against all organs.
     *
     * @return A map where each patient is associated with a list of compatibility scores for each organ.
     */
    public Map<Patient, List<Double>> calculateCompatibilityScores() {
        return patients.stream()
                .collect(Collectors.toMap(
                        patient -> patient,
                        patient -> organs.stream()
                                .map(organ -> calculateCompatibilityScore(organ, patient))
                                .collect(Collectors.toList())
                ));
    }

    /**
     * Calculates the overall compatibility score between an organ and a patient.
     *
     * @param organ   The organ being evaluated.
     * @param patient The patient receiving the organ.
     * @return The compatibility score on a scale from 0 to 100.
     */
    public double calculateCompatibilityScore(Organ organ, Patient patient) {
        double bloodTypeScore = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());
        double weightScore = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());
        double hlaScore = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
        return (bloodTypeScore * 0.4) + (weightScore * 0.3) + (hlaScore * 0.3);
    }

    /**
     * Calculates the blood type compatibility between donor and recipient.
     *
     * @param donorType     Blood type of the donor organ.
     * @param recipientType Blood type of the recipient patient.
     * @return The blood type compatibility score.
     */
    private double calculateBloodTypeCompatibility(String donorType, String recipientType) {
        // Define compatibility rules
        if (donorType.equals(recipientType)) {
            return 100.0;
        }
        // Simple compatibility mapping
        switch (donorType) {
            case "O-":
                return 80.0;
            case "O+":
                if (recipientType.endsWith("+")) return 80.0;
                break;
            case "A-":
                if (recipientType.startsWith("A") || recipientType.equals("AB-")) return 80.0;
                break;
            case "A+":
                if (recipientType.startsWith("A") || recipientType.equals("AB+")) return 80.0;
                break;
            case "B-":
                if (recipientType.startsWith("B") || recipientType.equals("AB-")) return 80.0;
                break;
            case "B+":
                if (recipientType.startsWith("B") || recipientType.equals("AB+")) return 80.0;
                break;
            case "AB-":
                if (recipientType.equals("AB-") || recipientType.equals("AB+")) return 80.0;
                break;
            case "AB+":
                // AB+ can receive from all types
                return 80.0;
        }
        return 0.0;
    }

    /**
     * Calculates the weight compatibility between donor organ and recipient patient.
     *
     * @param organWeight   Weight of the organ in grams.
     * @param patientWeight Weight of the patient in kilograms.
     * @return The weight compatibility score.
     */
    private double calculateWeightCompatibility(int organWeight, int patientWeight) {
        int difference = Math.abs(organWeight - patientWeight * 10); // Convert kg to g for comparison
        if (difference > 300) {
            return 0.0;
        }
        // Linearly decrease score as difference increases
        return 100.0 - ((double) difference * (100.0 / 300.0));
    }

    /**
     * Calculates the HLA compatibility between donor and recipient.
     *
     * @param organHla   HLA type of the donor organ.
     * @param patientHla HLA type of the recipient patient.
     * @return The HLA compatibility score.
     */
    private double calculateHlaCompatibility(String organHla, String patientHla) {
        String[] organAlleles = organHla.split("-");
        String[] patientAlleles = patientHla.split("-");
        int matches = 0;
        for (String allele : organAlleles) {
            for (String pAllele : patientAlleles) {
                if (allele.equals(pAllele)) {
                    matches++;
                    break;
                }
            }
        }
        return ((double) matches / organAlleles.length) * 100.0;
    }
}
