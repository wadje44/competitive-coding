public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int low=1, high=n, mid = (low/2 +high/2);
        while(low < high) {
            if(isBadVersion(mid))
                high = mid;
            else
                low = mid+1;
            mid = (low/2+high/2);
        }
        return low;
    }
}
