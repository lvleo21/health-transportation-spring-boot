package com.pbd.project.domain.views;


import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name="LocationsPerNeighborhood")
public class LocationsPerNeighborhood {


    @Column(name="health_center_id")
    private Integer health_center_id;

    @Id
    @Column(name="neighborhood")
    public String neighborhood;

    @Column(name="count")
    public Integer count;

    public Integer getHealth_center_id() {
        return health_center_id;
    }

    public void setHealth_center_id(Integer health_center_id) {
        this.health_center_id = health_center_id;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "LocationsPerNeighborhood{" +
                "health_center_id=" + health_center_id +
                ", neighborhood='" + neighborhood + '\'' +
                ", count=" + count +
                '}';
    }
}
