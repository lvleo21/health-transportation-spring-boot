package com.pbd.project.domain.enums;

public enum TravelStatus {

    EM_TRANSITO("EM_TRANSITO", "Em trânsito"),
    AGUARDANDO("AGUARDANDO", "Aguardando"),
    CONCLUIDO("CONCLUIDO", "Concluído");


    private String statusNameInDatabase;
    private String statusName;

    TravelStatus(String statusNameInDatabase, String statusName) {
        this.statusNameInDatabase = statusNameInDatabase;
        this.statusName = statusName;
    }
}
