package com.example;

import java.io.IOException;
import java.util.HashMap;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Matrix Multiplication Reducer.
 * 
 * It receives all elements from A and B that contribute to a single cell C(i,k) 
 * in the result matrix.
 */
public class MatrixReducer extends Reducer<Text, Text, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Maps to store index-value pairs for row i of A and column k of B
        HashMap<Integer, Integer> hashA = new HashMap<>();
        HashMap<Integer, Integer> hashB = new HashMap<>();

        // Group values by their source matrix
        for (Text val : values) {
            String[] tokens = val.toString().split(",");
            if (tokens[0].equals("A")) {
                // hashA maps column index -> value
                hashA.put(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            } else {
                // hashB maps row index -> value
                hashB.put(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            }
        }

        // Calculate the dot product for cell C(i,k)
        int sum = 0;
        for (int j = 0; j < 5; j++) { 
            // Result is sum( A(i,j) * B(j,k) ) for j = 0 to dimension
            if (hashA.containsKey(j) && hashB.containsKey(j)) {
                sum += hashA.get(j) * hashB.get(j);
            }
        }
        
        // Emit the final coordinates and the calculated value
        context.write(key, new IntWritable(sum));
    }
}
