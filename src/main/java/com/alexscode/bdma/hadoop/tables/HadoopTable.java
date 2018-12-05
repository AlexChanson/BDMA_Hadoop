package com.alexscode.bdma.hadoop.tables;

import java.util.List;

public abstract class HadoopTable {
    String namesapce;
    String name;
    List<String> columnFamilies;

    public abstract boolean build();
    public abstract boolean fill();
}
