package dev.patika.vetmanagement.dto.request.vaccineAnimal;

public class VacinneAnimalSaveRequest {
    private String vaccineCode;
    private String vaccineName;

    // Getters ve Setters
    public String getVaccineCode() {
        return vaccineCode;
    }

    public void setVaccineCode(String vaccineCode) {
        this.vaccineCode = vaccineCode;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }
}