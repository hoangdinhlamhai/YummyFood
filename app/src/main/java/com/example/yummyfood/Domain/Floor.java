package com.example.yummyfood.Domain;

import java.util.List;

public class Floor {
    private String floorName;
    private List<Table> tableList;

    public Floor(String floorName, List<Table> tableList) {
        this.floorName = floorName;
        this.tableList = tableList;
    }

    public String getFloorName() {
        return floorName;
    }

    public List<Table> getTableList() {
        return tableList;
    }
}
