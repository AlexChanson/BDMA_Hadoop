package bdma.bigdata.aiwsbu.util;

import bdma.bigdata.aiwsbu.Tables;
import bdma.bigdata.aiwsbu.util.random.Course;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class InsertRows {

    // Range of course serial IDs
    private final int courseMax = 100;
    private final int courseMin = 1;
    // Range of semesters
    private final int semesterMax = 10;
    private final int semesterMin = 1;
    // Range of student serial IDs
    private final int studentMax = 200;
    private final int studentMin = 1;
    // Range of years
    private final int yearMax = 2018;
    private final int yearMin = 2001;

    public static void main(String[] args) throws IOException {
        InsertRows insertRows = new InsertRows();
        insertRows.run();
    }

    public void run() throws IOException {
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);
        insertCourses(connection);
        insertStudents(connection);
        insertInstructors(connection);
        insertGrades(connection);
    }

    private void insertCourses(Connection connection) throws IOException {
        try (Table table = connection.getTable(Tables.getCourseTableName())) {
            char[] types = {'A', 'B'};
            for (int semester = semesterMin; semester <= semesterMax; ++semester) {
                for (char type: types) {
                    for (int serial = courseMin; serial <= courseMax; ++serial) {
                        if (type == 'B') {
                            serial *= 5; // A workaround to generate 100 courses w.r.t. 500 choices
                        }
                        for (int year = yearMin; year <= yearMax; ++year) {
                            Course course = new Course(semester, type, serial, year);
                            Put put = new Put(Bytes.toBytes(course.getRowKey()));
                            put.addImmutable(Bytes.toBytes("#"), Bytes.toBytes("N"), Bytes.toBytes(course.generateName()));
                            int n = 0;
                            for (String instructor : course.generateInstructors()) {
                                put.addImmutable(Bytes.toBytes("I"), Bytes.toBytes(++n), Bytes.toBytes(instructor));
                            }
                            table.put(put);
                        }
                    }
                }
            }
        }
    }

    // TODO
    private void insertGrades(Connection connection) throws IOException {

    }

    // TODO
    private void insertInstructors(Connection connection) throws IOException {

    }

    // TODO
    private void insertStudents(Connection connection) throws IOException {

    }
}
