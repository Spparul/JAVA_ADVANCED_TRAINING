class Solution {
    public int fib(int n) {
        int first=0, second=1;
        if(n==0) return 0;
        int third=first+second;
        for(int i=2;i<=n;i++)
        {
            third=first+second;
            first=second;
            second=third;
        }
        return third;
    }
}
