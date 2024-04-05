package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Program: Project 2 implement Optimal Binary Search
 *
 *read input file named input.txt for array
 *
 * Author: Kalyn Stricklin
 */
public class main {

    public static void main(String[] args) throws IOException {

        int n=0;
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

        SearchResult result = optimalBinarySearch(probabilities);

        try{
            FileWriter outFile = new FileWriter("kms0081.txt");
            outFile.write("Table:"+"\n") ;
            outputTable(result.getR(),n, outFile);

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
     * @param table
     * @param n
     * @param file
     * @throws IOException
     */
    public static void outputTable(int[][] table, int n, FileWriter file) throws IOException {
        for(int i=1; i <= n; i++){
            file.write(String.format("%3d", i));
            for(int j=0; j <= n; j++){
                file.write(String.format("%5d", table[i][j]));
            }
            file.write("\n");
        }

    }

    /**
     * method to output tree based on psuedocode given
     * @param roots
     * @param i
     * @param j
     * @param space
     */
    public static void outputTree(int roots[][], int i, int j, int space, FileWriter fileName) throws IOException{
        if(i <= j && i > 0 && j < roots.length){
            int mid = roots[i][j];
            if(mid>0){
                fileName.write(" ".repeat(space) + mid + "\n");
                outputTree(roots, i, roots[i][j]-1, space+4, fileName);

                outputTree(roots, roots[i][j]+1, j, space+4, fileName);
            }

        }
    }

    /**
     * @param p
     * @return
     */
    public static SearchResult optimalBinarySearch(List<Integer> p){
        int n = p.size();

        int[][]S = new int[n+1][n+1];   //dynamic programming values
        int[][]R = new int[n+1][n+1];  //dynamic programming roots

        for(int i = 1; i <= n; i++){
            S[i][i-1] = 0;
        }
        for(int i=0; i<=n; i++){
            for(int j=0; j<=n; j++) {
                System.out.println(S[i][j]);
            }
        }

        for(int i = 1; i < n ; i++){
            S[i][i] = p.get(i-1);
            R[i][i] = i;
        }

        for(int d = 1; d <= (n-1); d++){
            for(int i = 1; i <= (n-d); i++){
                int j = i + d;
                int sum_p = 0;
                int min_val = Integer.MAX_VALUE;
                int min_root = 0;

                for(int k = i; k <= j-1; k++){
                    System.out.println("Sum_p: " + sum_p);
                    sum_p += p.get(k-1);
                    int value = S[i][k-1] + S[k+1][j];
                    System.out.println("value: "+ value);
                    if(value < min_val){
                        min_val = value;
                        min_root = k;
                    }
                }
                S[i][j] = sum_p + min_val;
                R[i][j] = min_root;

            }
        }
        return new SearchResult(S,R);
    }

}
