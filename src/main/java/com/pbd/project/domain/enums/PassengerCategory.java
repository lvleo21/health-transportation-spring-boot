package com.pbd.project.domain.enums;

public enum PassengerCategory {
    PACIENTE("PACIENTE", "Paciente"),
    ACOMPANHENTE("ACOMPANHANTE", "Acompanhante");

    private String initials;
    private String description;

    PassengerCategory(String initials, String description) {
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
