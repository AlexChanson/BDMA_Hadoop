package com.alexscode.bdma.hadoop.app;

import com.alexscode.bdma.hadoop.beans.TableCount;
import com.alexscode.bdma.hadoop.err.StudentNotFoundException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.bind.annotation.*;

@RestController
public class CounterController {


    @RequestMapping(value = "/aiwsbu/v1/students/{id}/transcripts/{year}", method = RequestMethod.GET)
    public Object task1(@PathVariable("id") Long id, @PathVariable("year") int year) throws StudentNotFoundException{
        HashMap<String, Object> test = new HashMap<>();
        if (id != 42)
            throw new StudentNotFoundException("no student " + id);
        test.put("sucess", 1);
        test.put("id", id);
        test.put("year", year);
        return test;
    }

    @GetMapping("/hbase")
    public TableCount hbase() throws IOException {
        int res = 0;

        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);
        // Create table
        Admin admin = connection.getAdmin();
        try {

            TableName tableName = TableName.valueOf("test");/*
            HTableDescriptor htd = new HTableDescriptor(tableName);
            HColumnDescriptor hcd = new HColumnDescriptor("data");
            htd.addFamily(hcd);
            admin.createTable(htd);
            HTableDescriptor[] tables = admin.listTables();
            if (tables.length != 1 && Bytes.equals(tableName.getName(), tables[0].getTableName().getName())) {
                throw new IOException("Failed create of table");
            }*/
            // Run some operations -- three puts, a get, and a scan -- against
            // the table.
            Table table = connection.getTable(tableName);
            try {
                for (int i = 1; i <= 3; i++) {
                    byte[] row = Bytes.toBytes("row" + i);
                    Put put = new Put(row);
                    byte[] columnFamily = Bytes.toBytes("data");
                    byte[] qualifier = Bytes.toBytes(String.valueOf(i));
                    byte[] value = Bytes.toBytes("value" + i);
                    put.addImmutable(columnFamily, qualifier, value);
                    table.put(put);
                }
                Get get = new Get(Bytes.toBytes("row1"));
                Result result = table.get(get);
                System.out.println("Get: " + result);
                Scan scan = new Scan();
                ResultScanner scanner = table.getScanner(scan);
                try {
                    for (Result scannerResult : scanner) {
                        System.out.println("Scan: " + scannerResult);
                        res++;
                    }
                } finally {
                    scanner.close();
                }
                // Disable then drop the table
                //admin.disableTable(tableName);
                //admin.deleteTable(tableName);
            } finally {
                table.close();
            }
        } finally {
            admin.close();
        }
        return new TableCount(res);
    }
}
