class Leetcode342 {
    public boolean isPowerOfFour(int n) {
        if(n==1) return true;
        if(n<=0) return false;
        if(n%4==0)
        {if(((n&(n-1))==0)&& (n&0x55555555)!=0) return true;}
        return false;
    }
}
