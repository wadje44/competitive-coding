class Solution {

    public int findJudge(int N, int[][] trust) {
        if(N == 1) return 1;
        int count[] = new int[N], max = 0, judge=-1;
        boolean notJudge[] = new boolean[N];
        
        for(int i=0; i<trust.length; i++) {
            notJudge[trust[i][0]-1] = true;
            if(!notJudge[trust[i][1]-1]) {
                count[trust[i][1]-1]++;
                if(count[trust[i][1]-1] > max) {
                    max= count[trust[i][1]-1];
                    judge = trust[i][1];
                }
            }
        }
        if(!notJudge[judge-1] && max == N-1) return judge;
        return -1;
    }
}
