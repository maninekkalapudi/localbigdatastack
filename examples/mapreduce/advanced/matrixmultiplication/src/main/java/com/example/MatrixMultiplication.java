package com.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Driver class for Matrix Multiplication.
 * Refactored for clarity in beginner sessions.
 */
public class MatrixMultiplication {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: MatrixMultiplication <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Matrix Multiplication");
        
        job.setJarByClass(MatrixMultiplication.class);
        
        // Set separated Mapper and Reducer
        job.setMapperClass(MatrixMapper.class);
        job.setReducerClass(MatrixReducer.class);
        
        /**
         * WHY NO COMBINER?
         * In this algorithm, the Mapper emits values from Matrix A and Matrix B separately.
         * To compute the result for cell C(i,k), we MUST have values from both A and B.
         * Since a Combiner only sees data from a single Mapper (and often a subset of its data),
         * it cannot perform the multiplication or the final sum.
         */
        // job.setCombinerClass( ... ); // Not applicable for this simple implementation

        // Intermediate Output (from Mapper)
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // Final Output (from Reducer)
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
