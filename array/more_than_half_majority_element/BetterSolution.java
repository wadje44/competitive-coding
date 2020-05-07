class BetterSolution {
    public int majorityElement(int[] nums) {
        
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return majorityElement(nums, 0);
    }
    private int majorityElement(int[] nums, int start){
        int count = 1;
        int num = nums[start];
        for(int i = start+1;i<nums.length;i++){
            if(num == nums[i]) count++;
            else count--;
            if(count == 0) return majorityElement(nums,i+1);
        }
        return num;
    }
}
