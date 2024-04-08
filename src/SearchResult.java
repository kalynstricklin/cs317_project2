package src;

/**
 *
 */
 class SearchResult{
    int[][] C;
    int[][] R;

    SearchResult(int[][]C, int[][]R){
        this.C =C;
        this.R=R;
    }

     public int[][] getC() {
         return C;
     }

     public int[][] getR() {
         return R;
     }

}