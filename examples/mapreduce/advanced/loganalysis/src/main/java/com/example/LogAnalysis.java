package com.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Driver class for Log Analysis MapReduce job.
 * Beginner-friendly version with separated components.
 */
public class LogAnalysis {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: LogAnalysis <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Log Analysis Example");
        
        job.setJarByClass(LogAnalysis.class);
        
        // Set the Mapper, Combiner and Reducer classes
        job.setMapperClass(LogAnalysisMapper.class);
        job.setCombinerClass(LogAnalysisCombiner.class);
        job.setReducerClass(LogAnalysisReducer.class);
        
        // Define Output Key/Value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}