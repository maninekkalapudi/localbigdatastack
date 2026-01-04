package com.example;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * The Mapper class.
 * It processes one line of text at a time, splits it into words, 
 * and emits a pair (word, 1) for each word found.
 * 
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 * - KEYIN: Input key type (Object/LongWritable, usually representing file offset)
 * - VALUEIN: Input value type (Text, representing a line of text)
 * - KEYOUT: Output key type (Text, the word extracted)
 * - VALUEOUT: Output value type (IntWritable, the count of 1)
 */
public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    /**
     * The map method is called for every line in the input file.
     */
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Convert the Text line to a Java String
        String line = value.toString();
        
        // Use StringTokenizer to split the line into individual words (tokens)
        StringTokenizer itr = new StringTokenizer(line);
        
        while (itr.hasMoreTokens()) {
            // Set the word text
            word.set(itr.nextToken());
            
            // Write (emit) the pair (word, 1) to the intermediate context
            // Hadoop will collect these and group them by key before sending to the reducer
            context.write(word, one);
        }
    }
}
