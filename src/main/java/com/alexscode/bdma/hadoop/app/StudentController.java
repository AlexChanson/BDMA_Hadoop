package com.alexscode.bdma.hadoop.app;

import bdma.bigdata.aiwsbu.Namespace;
import com.alexscode.bdma.hadoop.err.CustomNotFoundException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class StudentController {
    Configuration config = HBaseConfiguration.create();
    Connection connection;
    static HashMap<String, String> pmap = new HashMap();
    HashMap<String, String> uemap = new HashMap<>();

    static {
        pmap.put("L1", "(01|02)");
        pmap.put("L2", "(03|04)");
        pmap.put("L3", "(05|06)");
        pmap.put("M1", "(07|08)");
        pmap.put("M2", "(09|10)");
    }

    {
        try {
            connection = ConnectionFactory.createConnection(config);

            //Fetch UE names, this is a very small table no point not to have it in memory
            HTable ueTable = new HTable(config, Namespace.getCourseTableName());
            Scan ueScan = new Scan();
            ueScan.addColumn("#".getBytes(), "N".getBytes());
            ResultScanner scanner = ueTable.getScanner(ueScan);
            for (Result result = scanner.next(); result != null; result = scanner.next()){
                String key = Bytes.toString(result.getRow());
                String cleanKey = key.split("/")[0] + "/" + (9999 - Integer.parseInt(key.split("/")[1]));
                String name = new String(result.getValue("#".getBytes(), "N".getBytes()));
                //System.out.println(cleanKey + "->" + name);
                uemap.put(cleanKey, name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/Aiwsbu/v1/students/{id}/transcripts/{program}", method = RequestMethod.GET)
    public Object task1(@PathVariable("id") String id, @PathVariable("program") String program) throws CustomNotFoundException {

        TableName studentsTableName = Namespace.getStudentTableName();
        try {
            //Fetch info on student
            HTable studentTable = new HTable(config, studentsTableName);

            Get getStudent = new Get(id.getBytes());
            getStudent.addColumn("#".getBytes(), "P".getBytes());
            getStudent.addColumn("#".getBytes(), "F".getBytes());
            getStudent.addColumn("#".getBytes(), "L".getBytes());
            getStudent.addColumn("C".getBytes(), "E".getBytes());

            Result infoEtu = studentTable.get(getStudent);

            HTable gradeTable = new HTable(config, Namespace.getGradeTableName());
            Scan gradeScan = new Scan();
            gradeScan.addColumn("#".getBytes(), "G".getBytes());

            RowFilter gradeFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                    new RegexStringComparator("....\\/"+pmap.get(program)+id+"\\/......."));

            gradeScan.setFilter(gradeFilter);

            ResultScanner scanner = gradeTable.getScanner(gradeScan);

            List<HashMap<String, Object>> semester1 = new ArrayList<>();
            List<HashMap<String, Object>> semester2 = new ArrayList<>();

            for (Result result = scanner.next(); result != null; result = scanner.next()) {
                String key = Bytes.toString(result.getRow());
                String semester = key.substring(5,7);
                String course = key.substring(18);
                String year = key.substring(0,4);
                double grade = Double.parseDouble(new String(result.getValue("#".getBytes(), "G".getBytes())));
                //System.out.printf("Found row : %s %s %s %s%n",semester, course, grade, year);
                HashMap<String, Object> note = new HashMap<>();
                note.put("Code", course);
                note.put("Name", uemap.getOrDefault(course+"/"+year, "Unknown"));
                note.put("Grade", grade/100);
                if (semester.equals("01") || semester.equals("03") || semester.equals("05") || semester.equals("07") || semester.equals("09"))
                    semester1.add(note);
                else
                    semester2.add(note);
            }

            scanner.close();

            HashMap<String, Object> student = new HashMap<>();
            student.put("Name", new String(infoEtu.getValue("#".getBytes(), "F".getBytes())) + " " + new String(infoEtu.getValue("#".getBytes(), "L".getBytes())));
            student.put("Email", new String(infoEtu.getValue("C".getBytes(), "E".getBytes())));
            student.put("Program", new String(infoEtu.getValue("#".getBytes(), "P".getBytes())));
            student.put("First", semester1);
            student.put("Second", semester2);


            return student;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomNotFoundException("No student " + id + " was found !");
        }

    }

    @RequestMapping(value = "/Aiwsbu/v1/rates/{semester}", method = RequestMethod.GET)
    public Object task2(@PathVariable("semester") int semester) throws CustomNotFoundException {
        try {
            HTable resTable = new HTable(config, "21402752Q2".getBytes());
            List<HashMap<String, Object>> rates = new ArrayList<>();
            Scan resultScan = new Scan();
            resultScan.addColumn("#".getBytes(), "S".getBytes());
            String sem = semester > 9 ? String.valueOf(semester) : "0" + semester;
            RowFilter resultFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(sem + "/...."));
            resultScan.setFilter(resultFilter);
            ResultScanner scanner = resTable.getScanner(resultScan);
            for (Result result = scanner.next(); result != null; result = scanner.next()){
                String key = Bytes.toString(result.getRow());
                HashMap<String, Object> map = new HashMap<>();
                map.put("Year", Integer.parseInt(key.split("/")[1]));
                map.put("Rate", Double.parseDouble(new String(result.getValue("#".getBytes(), "S".getBytes()))));
                rates.add(map);
            }
            if (rates.size() == 0)
                throw new CustomNotFoundException("Error 404 was caused by semester " + semester + " not existent in the database !");
            else
                return rates;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/Aiwsbu/v1/courses/{id}/rates", method = RequestMethod.GET)
    public Object task3(@PathVariable("id") String courseId) throws CustomNotFoundException{
        try {
            HTable resTable = new HTable(config, "21402752Q3".getBytes());
            List<HashMap<String, Object>> rates = new ArrayList<>();
            Scan resultScan = new Scan();
            resultScan.addColumn("#".getBytes(), "S".getBytes());
            RowFilter resultFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(".*/"+courseId));
            resultScan.setFilter(resultFilter);
            ResultScanner scanner = resTable.getScanner(resultScan);
            for (Result result = scanner.next(); result != null; result = scanner.next()){
                String key = Bytes.toString(result.getRow());
                HashMap<String, Object> map = new HashMap<>();
                map.put("Name", key.split("/")[0]);
                map.put("Rate", Double.parseDouble(new String(result.getValue("#".getBytes(), "G".getBytes()))));
                rates.add(map);
            }
            if (rates.size() == 0)
                throw new CustomNotFoundException("Error 404 was caused by course " + courseId + " not existent in the database !");
            else
                return rates;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
