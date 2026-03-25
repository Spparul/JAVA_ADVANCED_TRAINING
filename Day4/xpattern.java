import java.util.*;
public class Main
{
	public static void main(String[] args) {
		Scanner scn=new Scanner(System.in);
		int n=scn.nextInt();
		int st=n;
		int sp=0;
		for(int i=1;i<=n;i++)
		{
		    for(int j=1;j<=st;j++)
		    {
		        if(i==j||i+j==n+1)
		        System.out.print("* ");
		        else
		        System.out.print("  ");
		    }
		    System.out.println();
		}
	}
}
