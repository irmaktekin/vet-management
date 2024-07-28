package dev.patika.vetmanagement.dto.request.vaccineAnimal;

import jakarta.validation.constraints.NotNull;

public class VacinneAnimalSaveRequest {
    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(@NotNull Long vaccineId) {
        this.vaccineId = vaccineId;
    }

    /*private String vaccineCode;
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
        }*/
    @NotNull
    private Long vaccineId;
}