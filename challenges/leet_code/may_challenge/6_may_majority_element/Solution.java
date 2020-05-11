import java.util.*; 

class Solution {
    public int majorityElement(int[] nums) {

        Arrays.sort(nums); 
        int i, n = nums.length;
        for(i=0; i< n; i++) {
            if(nums[i] == nums[i+ n/2 + n%2 -1]) {
                break;
            }
        }
        return nums[i];
    }
}
