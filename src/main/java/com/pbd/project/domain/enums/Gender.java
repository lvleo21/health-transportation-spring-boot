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
    }
