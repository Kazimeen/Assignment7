package com.cybernetic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Entry point for the Organ Management System application.
 */
public class Main {

    /**
     * Main method to execute the Organ Management System functionalities.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Manually assigning HLA types for demonstration
        List<Organ> organs = Arrays.asList(
                new Organ("H1", "Heart", "A+", 300, "1-2-3-4-5-6"),
                new Organ("K1", "Kidney", "B-", 150, "2-3-4-5-6-7"),
                new Organ("L1", "Liver", "O+", 1500, "1-2-3-4-5-6")
        );

        List<Patient> patients = Arrays.asList(
                new Patient("P1", "John Doe", "A+", 70, "1-2-3-4-5-6"),
                new Patient("P2", "Jane Smith", "B-", 65, "2-3-4-5-6-7"),
                new Patient("P3", "Bob Johnson", "O+", 80, "1-2-3-4-5-6")
        );

        OrganManagementSystem system = new OrganManagementSystem(organs, patients);
        OrganCompatibilityAnalyzer analyzer = new OrganCompatibilityAnalyzer();
        organs.forEach(analyzer::addOrgan);
        patients.forEach(analyzer::addPatient);

        // Output as per assignment requirements
        System.out.println("\nAvailable Organs:");
        for (int i = 0; i < organs.size(); i++) {
            Organ o = organs.get(i);
            System.out.println((i + 1) + ". " + o.getName() + " (" + o.getBloodType() + ", " + o.getWeight() + "g)");
        }

        System.out.println("\nPatients:");
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (" + p.getBloodType() + ", " + p.getWeight() + "kg)");
        }

        System.out.println("\nUnique Blood Types: " + system.getUniqueBloodTypes());

        System.out.println("\nPatients Grouped by Blood Type:");
        system.groupPatientsByBloodType().forEach((bloodType, patientList) ->
                System.out.println(bloodType + ": " + patientList.stream().map(Patient::getName).collect(Collectors.toList())));

        System.out.println("\nOrgans Sorted by Weight:");
        List<Organ> sortedOrgans = system.sortOrgansByWeight();
        for (int i = 0; i < sortedOrgans.size(); i++) {
            Organ o = sortedOrgans.get(i);
            System.out.println((i + 1) + ". " + o.getName() + " (" + o.getBloodType() + ", " + o.getWeight() + "g)");
        }

        System.out.println("\nCompatibility Scores:");
        Map<Patient, List<Double>> scores = analyzer.calculateCompatibilityScores();
        for (Patient patient : patients) {
            List<Double> scoreList = scores.get(patient);
            for (int i = 0; i < organs.size(); i++) {
                System.out.println(patient.getName() + " - " + organs.get(i).getName() + ": " + String.format("%.0f", scoreList.get(i)));
            }
        }

        // Print Top 3 Compatible Organs for all patients
        System.out.println("\nTop Three Compatible Organs for Each Patient:");
        for (Patient patient : patients) {
            System.out.println("\nTop 3 Compatible Organs for " + patient.getName() + ":");
            List<Organ> topOrgans = system.getTopCompatibleOrgans(patient, 3, analyzer);
            for (int i = 0; i < topOrgans.size(); i++) {
                Organ organ = topOrgans.get(i);
                double score = analyzer.calculateCompatibilityScore(organ, patient);
                System.out.println((i + 1) + ". " + organ.getName() + " (" + organ.getBloodType() + ", " + organ.getWeight() + "g) - Score: " + String.format("%.0f", score));
            }
        }
    }
}
