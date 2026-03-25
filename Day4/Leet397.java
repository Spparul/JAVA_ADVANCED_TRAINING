class Solution {
    public int integerReplacement(int num) {
       if(num==1) return 0;
       long n=num;
        int step=0;
        while(n!=1)
        {if(n%2==0) n=n/2;
            else if(n%4==1||n==3) n=n-1;
            else n=n+1;
            step++;}
        return step;
    }
}
