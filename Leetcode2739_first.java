class Solution {
    public int distanceTraveled(int mainTank, int additionalTank) {
        int cont=0;int dist=0;
        while(mainTank>0)
        {if(cont==4){
            cont=-1;
            if(additionalTank>0){
                mainTank++;additionalTank--;
                }
            }
            dist+=10;
            mainTank--;
            cont++;

        }
        return dist;

    }
}
