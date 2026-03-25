class Leetcode476 {
    public int findComplement(int num) {
        int cmp=0;
        int temp=num;
        while(temp>0)
        {cmp=(cmp<<1)|1; temp>>=1;
        }
        return num^cmp;
    }
}
