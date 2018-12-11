package bdma.bigdata.aiwsbu.util;

import bdma.bigdata.aiwsbu.Namespace;
import bdma.bigdata.aiwsbu.Tables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class CreateSchema {

    private final TreeMap<String, List<String>> schemaMap = new TreeMap<String, List<String>>();

    private CreateSchema() {
        add(Tables.getCourseTable(), Tables.getCourseFamilies());
        add(Tables.getGradeTable(), Tables.getGradeFamilies());
        add(Tables.getInstructorTable(), Tables.getInstructorFamilies());
        add(Tables.getStudentTable(), Tables.getStudentFamilies());
    }

    public static void main(String[] args) throws IOException {
        CreateSchema schema = new CreateSchema();
        schema.run();
    }

    private void add(String table, String families) {
        List<String> columnFamilies = Arrays.asList(families.split(" "));
        this.schemaMap.put(table, columnFamilies);
    }

    private void run() throws IOException {
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);
        try (Admin admin = connection.getAdmin()) {
            try {
                admin.createNamespace(NamespaceDescriptor.create(Namespace.get()).build());
            } catch (Exception ignored) {
            }
            for (String table : schemaMap.keySet()) {
                TableName tableName = Tables.getTableName(table);
                try {
                    admin.disableTable(tableName);
                    admin.deleteTable(tableName);
                } catch (Exception ignored) {
                }
                HTableDescriptor htd = new HTableDescriptor(tableName);
                for (String familyName : schemaMap.get(table)) {
                    htd.addFamily(new HColumnDescriptor(familyName));
                }
                try {
                    admin.createTable(htd);
                    System.out.println("Tables " + tableName + " created.");
                } catch (Exception e) {
                    throw new IOException("Failed to run table " + tableName);
                }
            }
        }
    }
}
