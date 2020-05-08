class Solution {
    public boolean checkStraightLine(int[][] coordinates) {
        int i, n = coordinates.length;
        float  m = 0;
        if(coordinates[1][0] != coordinates[0][0])
            m = (coordinates[1][1] - coordinates[0][1]) / (coordinates[1][0] - coordinates[0][0]);
        for(i=2; i<n; i++) {
            if(m*(coordinates[i][0] - coordinates[0][0]) != (coordinates[i][1] - coordinates[0][1]))
                return false;
        }
        return true;
    }
}
