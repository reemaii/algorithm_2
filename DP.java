

import java.util.Scanner;

public class DP
{
    final static int MAXIMUM = Integer.MAX_VALUE;//represents the maximum positive integer value that can be represented in 32 bits
    static String[] splitedSentece; // to access it for printing
    
	// length[][] represents lengths of different words in input sequence.
	// l_length represent length[]'s length.
	// line_width is  maximum number of characters that can fit in a line (given by the user).
    static int solveTextFormatting(int lengths[], int l_length, int line_width)
    {	
        // 1 extra space is used in all created arrays (to avoid ArrayIndexOutOfBoundsException)
    
    	
    	// costOfLine[i][j] will have cost of a line which has words from i to j.
        int costOfLine[][]= new int[l_length+1][l_length+1];
        
        // extras[i][j] will have number of extra spaces if words from i to j are put in a single line.
        int extras[][] = new int[l_length+1][l_length+1];
    
     
        // minimumCost[i] will have total cost of optimal arrangement of words from 1 to i.
        int minimumCost[] = new int[l_length+1];
     
        // printSolution[] is used to print the optimal arrangement solution.
        int printSolution[] =new int[l_length+1];
     
        // calculate extra spaces in a single line.
        for (int i = 1; i <= l_length; i++)
        {
            extras[i][i] = line_width - lengths[i-1];
            for (int j = i+1; j <= l_length ; j++)
            extras[i][j] = extras[i][j-1] - lengths[j-1] - 1;
            //The value extra[i][j] indicates extra spaces if words from word number i to j are placed in a single line.
        }
         
        // Calculate line cost corresponding to the above calculated extra spaces.
        for (int i = 1; i <= l_length; i++)
        {
            for (int j = i; j <= l_length; j++)
            {
                if (extras[i][j] < 0) // negative > cannot be placed in a single line
                	costOfLine[i][j] = MAXIMUM;
                else if (j == l_length && extras[i][j] >= 0) // last word > cost = 0
                	costOfLine[i][j] = 0;
                else 
                	costOfLine[i][j] = extras[i][j]*extras[i][j]*extras[i][j]; // else calculate cost as given cost function
            }
        }
         
        // Now, calculate minimum cost and find minimum cost arrangement corresponding to the above calculated cost of line.
        minimumCost[0] = 0;
        for (int j = 1; j <= l_length; j++)
        {
        	minimumCost[j] = MAXIMUM;
            for (int i = 1; i <= j; i++)
            {
                if (minimumCost[i-1] != MAXIMUM && costOfLine[i][j] != MAXIMUM &&
                   (minimumCost[i-1] + costOfLine[i][j] < minimumCost[j]))
                {
                	minimumCost[j] = minimumCost[i-1] + costOfLine[i][j];
                    printSolution[j] = i; 
                }
            }
            
        }
        
        printTextFormatting(printSolution,l_length);
 
	    return minimumCost[l_length]; //return the optimal cost i.e miniumum cost
	  } 
	  
	    static int printTextFormatting(int p[], int n)
	    {
	        int j;
	        if (p[n] == 1) 
	        j = 1;
	        else 
	        j = printTextFormatting(p, p[n]-1) + 1;
	        
	        System.out.print("Line number" + " " + j + ": ");
	        if (  p[n] != n ) 
	        	 System.out.println(splitedSentece[ p[n]-1] + " " + splitedSentece[n-1]);
	        else 
	            System.out.println(splitedSentece[n-1]);
	        return j;
	    }
	     
    public static void main(String args[])
    { 
    	  
  	      
    	Scanner reader = new Scanner (System.in);
    	
        System.out.println("Enter the required senctence to solve it:");
        String sentence = reader.nextLine();
        splitedSentece = sentence.split("\\s"); //split the sentence based on white space (each word)
       
        int l[] = new int[splitedSentece.length];
        for ( int i = 0; i < l.length; i++) {
        	l[i] = splitedSentece[i].length(); } //copy the size of each string
        int n = l.length;
        System.out.println("Enter the line width:");
        int M = reader.nextInt();
        
        int minumum_cost = solveTextFormatting(l, n, M);
        System.out.println();
        System.out.println("The minumum cost is of arranging \""+ sentence + "\" is: " + minumum_cost);
    }
}
 