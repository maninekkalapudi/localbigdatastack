package com.example;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * The Reducer class.
 * It receives a key (word) and a list of values (counts of 1), 
 * sums the values, and emits the final result (word, total_count).
 * 
 * Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 * - KEYIN: Input key type (Text, the word from Mapper)
 * - VALUEIN: Input value type (IntWritable, the 1s emitted by Mapper)
 * - KEYOUT: Output key type (Text, the word)
 * - VALUEOUT: Output value type (IntWritable, the final sum)
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    /**
     * The reduce method is called for every unique key (word) produced by the mappers.
     * Hadoop ensures that all 1s for the same word are sent to the same reducer call.
     */
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
            throws IOException, InterruptedException {
        int sum = 0;
        
        // Iterate through all the values (the 1s) associated with this key
        for (IntWritable val : values) {
            sum += val.get();
        }
        
        // Set the final sum for this word
        result.set(sum);
        
        // Emit the final result to HDFS
        context.write(key, result);
    }
}
