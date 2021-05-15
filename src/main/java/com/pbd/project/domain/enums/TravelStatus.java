package com.pbd.project.domain.enums;

public enum TravelStatus {

    EM_TRANSITO("EM_TRANSITO", "Em trânsito"),
    AGUARDANDO("AGUARDANDO", "Aguardando"),
    CONCLUIDO("CONCLUIDO", "Concluído");


    private String name;
    private String description;

    TravelStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
