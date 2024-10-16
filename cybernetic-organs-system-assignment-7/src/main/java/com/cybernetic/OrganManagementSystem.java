package com.cybernetic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages organs and patients, providing functionalities such as grouping and sorting.
 */
public class OrganManagementSystem {
    private List<Organ> organs;
    private List<Patient> patients;

    /**
     * Constructs a new OrganManagementSystem instance.
     *
     * @param organs   List of available organs.
     * @param patients List of patients in need of organs.
     */
    public OrganManagementSystem(List<Organ> organs, List<Patient> patients) {
        this.organs = organs;
        this.patients = patients;
    }

    /**
     * Retrieves a set of unique blood types present in both organs and patients.
     *
     * @return A set of unique blood types.
     */
    public Set<String> getUniqueBloodTypes() {
        Set<String> organBloodTypes = organs.stream()
                .map(Organ::getBloodType)
                .collect(Collectors.toSet());
        Set<String> patientBloodTypes = patients.stream()
                .map(Patient::getBloodType)
                .collect(Collectors.toSet());
        organBloodTypes.addAll(patientBloodTypes);
        return organBloodTypes;
    }

    /**
     * Groups patients by their blood type.
     *
     * @return A map where each blood type is associated with a list of patients having that blood type.
     */
    public Map<String, List<Patient>> groupPatientsByBloodType() {
        return patients.stream()
                .collect(Collectors.groupingBy(Patient::getBloodType));
    }

    /**
     * Sorts the organs in ascending order based on their weight.
     *
     * @return A list of organs sorted by weight.
     */
    public List<Organ> sortOrgansByWeight() {
        List<Organ> sortedOrgans = new ArrayList<>(organs);
        sortedOrgans.sort(Comparator.comparingInt(Organ::getWeight));
        return sortedOrgans;
    }

    /**
     * Retrieves the top N compatible organs for a specified patient.
     *
     * @param patient  The patient for whom compatible organs are to be found.
     * @param n        The number of top compatible organs to retrieve.
     * @param analyzer The OrganCompatibilityAnalyzer instance used for calculating scores.
     * @return A list of top N compatible organs sorted by compatibility score in descending order.
     */
    public List<Organ> getTopCompatibleOrgans(Patient patient, int n, OrganCompatibilityAnalyzer analyzer) {
        return organs.stream()
                .sorted((o1, o2) -> Double.compare(
                        analyzer.calculateCompatibilityScore(o2, patient),
                        analyzer.calculateCompatibilityScore(o1, patient)))
                .limit(n)
                .collect(Collectors.toList());
    }
}
