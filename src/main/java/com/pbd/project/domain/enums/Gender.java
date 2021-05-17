package com.pbd.project.domain.enums;

public enum Gender {

    M("M", "Masculino"),
    F("F", "Feminino"),
    O("O", "Outro");

    private String initials;
    private String description;

    Gender(String initials, String description) {
        this.initials = initials;
        this.description = description;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
