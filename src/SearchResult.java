package src;

 class SearchResult{
    int[][] S;
    int[][] R;

    SearchResult(int[][]S, int[][]R){
        this.S =S;
        this.R=R;
    }

     public int[][] getS() {
         return S;
     }

     public int[][] getR() {
         return R;
     }

}