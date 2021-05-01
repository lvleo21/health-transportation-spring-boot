package com.pbd.project.domain.enums;

public enum Status {
    Ativo(true, "Ativo"),
    Inativo(false, "Inativo");

    private Boolean initial;
    private String statusName;

    Status(Boolean initial, String statusName) {
        this.initial = initial;
        this.statusName = statusName;
    }


    public Boolean getInitial() {
        return initial;
    }

    public void setInitial(Boolean initial) {
        this.initial = initial;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
