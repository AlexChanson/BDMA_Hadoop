package com.alexscode.bdma.hadoop.tables;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class Students extends HadoopTable {
    public Students() {
        namesapce = "21500078";
        name = "E";
        columnFamilies = new ArrayList<>();
        columnFamilies.add("A");
    }

    @Override
    public boolean build() {
        return false;
    }

    @Override
    public boolean fill() {
        return false;
    }
}
