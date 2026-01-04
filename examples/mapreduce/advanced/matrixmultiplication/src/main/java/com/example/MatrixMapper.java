package com.example;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Matrix Multiplication Mapper.
 * 
 * Input format expected: matrixName,row,col,value (e.g., A,0,0,10)
 * 
 * For an element A(i,j), we need it for every cell C(i,k) in the result matrix.
 * For an element B(j,k), we need it for every cell C(i,k) in the result matrix.
 */
public class MatrixMapper extends Mapper<Object, Text, Text, Text> {

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Parse the input line: matrixName,row,col,value
        String[] tokens = value.toString().split(",");
        String matrix = tokens[0];
        int row = Integer.parseInt(tokens[1]);
        int col = Integer.parseInt(tokens[2]);
        int val = Integer.parseInt(tokens[3]);

        // Logic for Matrix A
        if (matrix.equals("A")) {
            // For each element A(i,j), it contributes to all C(i, k) where k is a column in B
            for (int k = 0; k < 5; k++) { 
                // Key: result cell coordinates (row, k)
                // Value: identifier and column index in A (which matches row index in B), and the value
                context.write(new Text(row + "," + k), new Text("A," + col + "," + val));
            }
        } 
        // Logic for Matrix B
        else if (matrix.equals("B")) {
            // For each element B(j,k), it contributes to all C(i, k) where i is a row in A
            for (int i = 0; i < 5; i++) { 
                // Key: result cell coordinates (i, col)
                // Value: identifier and row index in B (which matches column index in A), and the value
                context.write(new Text(i + "," + col), new Text("B," + row + "," + val));
            }
        }
    }
}
