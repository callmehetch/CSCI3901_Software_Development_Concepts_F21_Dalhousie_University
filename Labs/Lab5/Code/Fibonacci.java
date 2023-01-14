//package Assignment2;

import java.util.Scanner;

public class Fibonacci
{
    private static final int level=50;
    public static int fibo(int rec, int depth) throws MaximumRecursionDepth
    {
        //0 1 1 2 3 5 8 13 21 34
        depth++;

            if (depth > level)
            {
                throw new MaximumRecursionDepth("Depth has exceeded the maximum number of levels!", depth);
            }
            else {
                if (rec <= 1)
                {
                    return rec;
                }

                else
                {
                    //System.out.println("Max Level: " + level + " Current Depth: " + depth);
                    return fibo(rec - 1,depth  ) + fibo(rec - 2,depth);

                }
            }
    }

    public static void main (String args[])
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter a value for the Fibonnaci number, maximum number of elements /levels permitted is 50! ");
        int position=sc.nextInt();
        if(position<0)
        {
            System.out.println("Enter a non-negative number!");
        }
        //int depth=0;
        else {
            try {

                int result = fibo(position, 0);
                System.out.println("Fibonacci Number: " + result);
            } catch (MaximumRecursionDepth exp) {
                System.out.println("Error Message: " + exp.getMessage());
                System.out.println("Depth Reached: " + exp.getDepth());
            }
        }

    }

}

