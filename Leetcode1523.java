class Leetcode1523 {
    public int countOdds(int low, int high) {
        int cnt=0;
        if(low%2==0 &&high%2==0) return (high-low)/2;
        
        return 1+(high-low)/2;
    }
}
