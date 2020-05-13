class Solution {
    public int singleNonDuplicate(int[] nums) {
        int lo = 0, len = nums.length, hi = len / 2;

        while (lo < hi){
            System.out.println("lo: " + lo + "  hi:" + hi);
            int mid = lo + ((hi - lo) >> 1);
            System.out.println("mid: " + mid);
            if (nums[2 * mid] == nums[2 * mid + 1]){
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return nums[2 * lo];

    }
}
