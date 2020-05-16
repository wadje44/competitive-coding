class Solution {
    public int maxSubarraySumCircular(int[] A) {
        if(A.length==1) return A[0];
        int i=1, max=A[0], curr=A[0], min=A[1], sum=A[0];
        for(;i<A.length;i++) {
            curr = Math.max(curr+A[i], A[i]);
            max = Math.max(curr, max);
            sum += A[i];
        }
        curr = A[1];
        for(i=2; i<A.length-1; i++) {
            curr = Math.min(curr+A[i], A[i]);
            min = Math.min(curr, min);
        }
        return Math.max(max, sum-min);
    }
}
