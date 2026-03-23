class Solution {
    public int distanceTraveled(int mainTank, int additionalTank) {
        int add=0;
        int main=mainTank;
        while(main>4)
        {main-=5;
        if(additionalTank>0) {additionalTank--;main++;add++;}

        }
        return(mainTank+add)*10;

    }
}
