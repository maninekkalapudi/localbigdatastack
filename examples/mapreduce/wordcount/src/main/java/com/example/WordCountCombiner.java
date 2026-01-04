package com.example;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * The Combiner class.
 * Think of this as a "Local Reducer" that runs on the same machine as the Mapper.
 * 
 * WHY USE IT?
 * If a Mapper finds the word "Hadoop" 100 times, instead of sending ("Hadoop", 1) 
 * across the network 100 times, the Combiner sums them locally and sends 
 * ("Hadoop", 100) once.
 */
public class WordCountCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    /**
     * The logic here is usually the same as the Reducer for summation tasks.
     */
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        result.set(sum);
        
        // This output goes to the Shuffling phase, then to the actual Reducer.
        context.write(key, result);
    }
}
