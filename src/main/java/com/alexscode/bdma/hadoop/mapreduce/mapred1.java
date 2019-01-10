package com.alexscode.bdma.hadoop.mapreduce;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;


public class mapred1 {

    static class Mapper1 extends TableMapper<ImmutableBytesWritable, IntWritable> {
        private static final IntWritable one = new IntWritable(1);

        public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
            // get rowKey and convert it to string
            String inKey = new String(row.get());
            // set new key having only date
            String oKey = inKey.split("/")[0];
            // get sales column in byte format first and then convert it to
            // string (as it is stored as string from hbase shell)
            byte[] bnotes = value.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("notes"));
            String snotes = new String(bnotes);
            Integer notes = new Integer(snotes);
            // emit date and sales values
            context.write(new ImmutableBytesWritable(oKey.getBytes()), new IntWritable(notes));
        }


    }

    public static class Reducer1 extends TableReducer<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable> {

        public void reduce(ImmutableBytesWritable key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {


                int sum = 0;
                int compteur = 0;
                // loop through different sales vales and add it to sum
                for (IntWritable moyenne : values) {
                    Integer intmoyenne = new Integer(moyenne.toString());
                    sum += intmoyenne;
                    compteur++;
                }

                // create hbase put with rowkey as date

                Put insHBase = new Put(key.get());
                // insert sum value to hbase
                insHBase.add(Bytes.toBytes("cf1"), Bytes.toBytes("sum"), Bytes.toBytes(sum/compteur));
                // write data to Hbase table
                context.write(key, insHBase);

        }
    }
}