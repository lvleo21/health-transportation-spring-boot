package com.pbd.project.domain.enums;

public enum PassengerTransition {

    ONLY_GO("SI", "Somente Ida"),
    ONLY_BACK("SV", "Somente Volta"),
    ROUND_TRIP("IV", "Ida e Volta");

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
