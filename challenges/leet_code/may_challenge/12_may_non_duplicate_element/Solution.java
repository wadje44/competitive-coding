class Solution {
    
    public int modifiedBinarySearch(int[] nums, int l, int h) {
        if(l<=h) {
            int mid = (l+h)/2;
            if(mid==0) {
                if(nums[mid] != nums[mid+1]) return nums[mid];
                return modifiedBinarySearch(nums, mid+2, h);
            }
            else if(mid==(nums.length-1)){
                if(nums[mid] != nums[mid-1]) return nums[mid];
                return modifiedBinarySearch(nums, l, mid-2);
            }
            else if((nums[mid] != nums[mid-1]) && (nums[mid] != nums[mid+1])){
                return nums[mid];
            }
            if(nums[mid] == nums[mid+1]) {
                if(mid % 2 == 0) {
                    return modifiedBinarySearch(nums, mid+2, h);
                }
                return modifiedBinarySearch(nums, l, mid-1);
            }
            if(mid % 2 == 0) {
                return modifiedBinarySearch(nums, l, mid-2);
            }
            return modifiedBinarySearch(nums, mid+1, h);
        }
        return 0;
    }
    public int singleNonDuplicate(int[] nums) {
        if(nums.length == 1) return nums[0];
        return modifiedBinarySearch(nums, 0, nums.length-1);
    }
}
