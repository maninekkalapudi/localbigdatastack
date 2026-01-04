package com.example;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * LogAnalysis Reducer.
 * Sums the occurrences of each IP address.
 */
public class LogAnalysisReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    
    private IntWritable result = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        
        // Accumulate counts for the current IP key
        for (IntWritable val : values) {
            sum += val.get();
        }
        
        result.set(sum);
        // Write the IP and its total hit count to HDFS
        context.write(key, result);
    }
}
