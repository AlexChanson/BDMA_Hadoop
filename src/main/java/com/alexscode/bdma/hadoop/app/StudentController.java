package com.alexscode.bdma.hadoop.app;

import bdma.bigdata.aiwsbu.Namespace;
import com.alexscode.bdma.hadoop.err.StudentNotFoundException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Name;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class StudentController {
    Configuration config = HBaseConfiguration.create();
    Connection connection;
    static HashMap<String, String> pmap = new HashMap();

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/aiwsbu/v1/students/{id}/transcripts/{program}", method = RequestMethod.GET)
    public Object task1(@PathVariable("id") String id, @PathVariable("program") String program) throws StudentNotFoundException {

        TableName studentsTableName = Namespace.getStudentTableName();
        try {
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
                    new RegexStringComparator("....\\/.."+id+"\\/."+pmap.get(program)+"...."));

            gradeScan.setFilter(gradeFilter);

            ResultScanner scanner = gradeTable.getScanner(gradeScan);

            List<HashMap<String, Object>> semester1 = new ArrayList<>();
            List<HashMap<String, Object>> semester2 = new ArrayList<>();

            for (Result result = scanner.next(); result != null; result = scanner.next()) {
                String key = Bytes.toString(result.getRow());
                String semester = key.substring(5,7);
                String course = key.substring(18);
                double grade = Double.parseDouble(new String(result.getValue("#".getBytes(), "G".getBytes())));
                System.out.printf("Found row : %s %s %s%n",semester, course, grade);
                HashMap<String, Object> note = new HashMap<>();
                note.put("Code", course);
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
            throw new StudentNotFoundException("no student " + id);
        }

    }
}
