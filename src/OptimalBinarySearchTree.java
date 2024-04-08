package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Program: Project 2 implement Optimal Binary Search
 *
 * read input file named input.txt for array
 *
 * Author: Kalyn Stricklin
 */
public class OptimalBinarySearchTree {

    public static void main(String[] args) throws IOException {

        int n=0;  // num of elements

        List<Integer>probabilities = new ArrayList<>();

        try{
            BufferedReader reader= new BufferedReader(new FileReader("src/input.txt"));
            String line = reader.readLine();
            if(line != null){
                n = Integer.parseInt(line.trim());
                while((line = reader.readLine())!= null){
                    probabilities.add(Integer.parseInt(line.trim()));
                }
            }

            System.out.println("total elements: "+ n);
            System.out.println("probabilities: "+ probabilities);
            reader.close();

        }catch(IOException e){
            System.out.println("Error with reading file.");
        }

        SearchResult result = optimalBinarySearch(n, probabilities);

        try{
            FileWriter outFile = new FileWriter("kms0081.txt");
            outFile.write("Table:"+"\n") ;
            outputTable(result.getC(), n , outFile);

            outFile.write("Binary Tree: "+ "\n");

            outputTree(result.getR(), 1, n, 0, outFile);
            outFile.close();
        }catch(IOException e){
            System.out.println("Error with output file.");
        }


    }

    /**
     * Print out table
     *
     * @param dpTable
     * @param n
     * @param file
     * @throws IOException
     */
    public static void outputTable(int[][] dpTable, int n, FileWriter file) throws IOException {

        String header = " - " ;
        for(int i=1; i<=n; i++){
            header += String.format(" %02d ", i);
        }
        file.write(header+ "\n");
        for(int i=1; i <= n; i++){
            String row = String.format("%02d",i);
            for(int j=1; j <= n; j++){
//                file.write(String.format("%03d", dpTable[i][j]));
                row +=  String.format("%4d", dpTable[i][j]);
            }
            file.write(row + "\n");
        }

    }

    /**
     * method to output tree based on pseudocode given
     * @param roots
     * @param i
     * @param j
     * @param space
     */
    public static void outputTree(int roots[][], int i, int j, int space, FileWriter fileName) throws IOException{
        if(i <= j && i > 0 && j <= roots.length){
            String node = String.format("%"+( space+1)+ "d", roots[i][j]);
            fileName.write(node+"\n");

            int mid = roots[i][j];
            if(mid > 0){
                outputTree(roots, i, roots[i][j]-1, space+4, fileName);
                outputTree(roots, roots[i][j]+1, j, space+4, fileName);
            }

        }
    }


    /**
     * @param p
     * @param n
     * @return
     */
    public static SearchResult optimalBinarySearch(int n, List<Integer> p){

        int[][]C = new int[n+1][n+1];   //C = COST
        int[][]R = new int[n+1][n+1];  //R = ROOTS

        for(int i = 1; i <= n; i++){
            C[i][i-1] = 0;
            C[i][i] = p.get(i-1);
            R[i][i] = i;
            R[i][i-1] = i;
        }

        for(int diagonal= 1; diagonal <= n-1; diagonal++){
            for(int i = 1; i <= (n-diagonal); i++){
                int j = i + diagonal;

                int minCost = Integer.MAX_VALUE; //infinity
                int minRoot = 0;

                // calculate the minimum value
                for(int k = i; k <= j; k++){
                    int leftCost = (k>=i) ? C[i][k-1]: 0;
                    int rightCost = (k<j) ? C[k+1][j]: 0;
                    int value = leftCost + rightCost;

                    if(value <= minCost){
                        minCost = value;
                        minRoot = k;
                    }
                }
                R[i][j] = minRoot;

                // calc sum of probabilities
                int sum_p = 0;
                for(int l=i; l<=j; l++){
                    sum_p += p.get(l-1);
                }
                // set cost to the sum of probabilities and minimum value
                C[i][j] = sum_p + minCost;
            }
        }
        return new SearchResult(C,R);
    }
}
