package com.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * The Driver class.
 * It configures the MapReduce job, specifies the Mapper, Reducer, 
 * Input/Output paths, and submits the job to the cluster.
 */
public class WordCount {

    public static void main(String[] args) throws Exception {
        // Basic check for arguments
        if (args.length != 2) {
            System.err.println("Usage: WordCount <input path> <output path>");
            System.exit(-1);
        }

        // Initialize Hadoop configuration
        Configuration conf = new Configuration();
        
        // Create a new Job instance
        Job job = Job.getInstance(conf, "Word Count Example");
        
        // Specify the JAR file that contains the driver, mapper, and reducer
        job.setJarByClass(WordCount.class);
        
        // Set the Mapper, Combiner, and Reducer classes
        job.setMapperClass(WordCountMapper.class);
        job.setCombinerClass(WordCountCombiner.class);
        job.setReducerClass(WordCountReducer.class);
        
        // Set the output key and value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        // Set HDFS input and output paths from command line arguments
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        // Submit the job and wait for it to complete
        // The 'true' argument tells it to print progress to the console
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}