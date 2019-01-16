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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Name;
import java.io.IOException;
import java.util.HashMap;

@RestController
public class StudentController {
    Configuration config = HBaseConfiguration.create();
    Connection connection;

    {
        try {
            connection = ConnectionFactory.createConnection(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/aiwsbu/v1/students/{id}/transcripts/{year}", method = RequestMethod.GET)
    public Object task1(@PathVariable("id") String id, @PathVariable("year") int year) throws StudentNotFoundException {

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
                    new RegexStringComparator(year + "\\/.."+id+"\\/......."));

            gradeScan.setFilter(gradeFilter);

            ResultScanner scanner = gradeTable.getScanner(gradeScan);

            for (Result result = scanner.next(); result != null; result = scanner.next())
                System.out.println("Found row : " + result);

            scanner.close();

            HashMap<String, Object> student = new HashMap<>();
            student.put("Name", new String(infoEtu.getValue("#".getBytes(), "P".getBytes())) + " " + new String(infoEtu.getValue("#".getBytes(), "F".getBytes())));
            student.put("Email", new String(infoEtu.getValue("C".getBytes(), "E".getBytes())));
            student.put("Program", new String(infoEtu.getValue("#".getBytes(), "P".getBytes())));


            return student;
        } catch (IOException e) {
            throw new StudentNotFoundException("no student " + id);
        }

    }
}
