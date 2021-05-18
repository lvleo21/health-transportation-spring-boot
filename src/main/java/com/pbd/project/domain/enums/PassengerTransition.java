package com.pbd.project.domain.enums;

public enum PassengerTransition {

    SI("SI", "Somente Ida"),
    SV("SV", "Somente Volta"),
    IV("IV", "Ida e Volta");

    private String initials;
    private String description;

    PassengerTransition(String initials, String description) {
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
