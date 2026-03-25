class Solution {
    public int missingNumber(int[] nums) {
        int len=nums.length,sum=0;
        int totalsum=len*(len+1)/2;
        for(int num:nums)
        {
            sum+=num;
        }
        return totalsum-sum;
    }
}
