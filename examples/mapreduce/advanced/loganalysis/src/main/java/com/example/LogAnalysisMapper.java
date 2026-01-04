package com.example;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * LogAnalysis Mapper.
 * This class parses log lines to extract the IP address and emits (IP, 1).
 */
public class LogAnalysisMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text ipAddress = new Text();
    
    // Regular expression to match the IP address at the beginning of a log line
    private static final Pattern ipPattern = Pattern.compile("^([0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}).*");

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        Matcher matcher = ipPattern.matcher(line);
        
        // If the line starts with an IP address, extract it
        if (matcher.find()) {
            ipAddress.set(matcher.group(1));
            // Emit the IP address with a count of 1
            context.write(ipAddress, one);
        }
    }
}
