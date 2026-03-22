class Leetcode7 {
    public int reverse(int x) {
        int flag=1;
        if(x<0) flag=-1;
        int rev=0;
        int n=x;
        if(flag==-1)n=x-(2*x);
        while(n>0)
        {
            if(rev>2147483647/10 ) return 0;
            rev=(rev*10)+(n%10); n/=10;
        }
         return (rev*flag);
    }
}
