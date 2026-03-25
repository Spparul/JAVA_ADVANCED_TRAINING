class Leetcode1470 {
    public int[] shuffle(int[] nums, int n) {
        int[] arr=new int[2*n];
        int ind=0;
        for(int i=0;i<n;i++)
        {arr[ind++]=nums[i];
        arr[ind++]=nums[i+n];
        }
        return arr;
    }
}
