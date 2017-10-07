package cs181;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
 
public class Reducer1 extends Reducer<Text, Text, Text, Text> {

	/* TODO - Implement the reduce function. 
	 * 
	 * 
	 * Input :    Adjacency Matrix Format       ->	( j   ,   M  \t  i	\t value 
	 * 			  Vector Format					->	( j   ,   V  \t   value )
	 * 
	 * Output :   Key-Value Pairs               
	 * 			  Key ->   	i
	 * 			  Value -> 	M_ij * V_j  
	 */

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			
		
		double vVal = 0;
		ArrayList<String> myList = new ArrayList<String> ();
					
		// Loop through values, to add m_ij term to mList and save v_j to variable v_j
		
		for (Text val: values){
			// String[] valArray  = val.toString().split("\t");
			if ( val.charAt(0) == 'V'){
				// vector 
				vVal = Double.parseDouble(val.toString().substring(2));
			}
			else{
				// matrx
				myList.add(val.toString().substring(2)); // put (i \t value) into the list 	
			}
		}
			
		// Then Iterate through the terms in mList, to multiply each term by variable v_j.
		// Each output is a key-value pair  ( i  ,   m_ij * v_j)
		
		Text outputKey = new Text();
		Text outputValue = new Text();
		
		for ( String vecval : myList){
			String[] vecval_array = vecval.split("\t"); // split into [i, value]
			outputKey.set(vecval_array[0]); 
			double product = Double.parseDouble(vecval_array[1]) * vVal;
			outputValue.set( Double.toString(product));
			context.write(outputKey, outputValue );	
		}
		
	}

}
