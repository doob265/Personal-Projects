//Mark Dubin
//12/10/2020
//AoC 2020 Day 10: Adapter Array
//No current solution for part 2...

import java.util.*;
import java.io.*;
import java.math.*;

public class dayTen {
    //attempt at a recursive solution for part 2, would take way too long to complete...
    public static int rec(int[] vals, int index){
        int i, j = 0, val;
        int[] indices;
        //System.out.println(index + " " + vals.length);
        if(index >= vals.length-1) return 1;
        else if(index + 4 > vals.length){
            val = (index + 4) - vals.length;
            //System.out.println(val);
            switch(val){
                case 1:
                indices = new int[2];
                for(i = index + 1; i < index + 3; i++){
                    if(vals[i] - vals[index] <= 3){
                        indices[j] = i;
                        j++;
                    }
                }
                if(indices[0] == 0) return 0;
                if(indices[1] == 0) return rec(vals, indices[0]);
                return rec(vals, indices[0]) + rec(vals, indices[1]);
                case 2:
                indices = new int[1];
                for(i = index + 1; i < index + 2; i++){
                    if(vals[i] - vals[index] <= 3){
                        indices[j] = i;
                        j++;
                    }
                }
                if(indices[0] == 0) return 0;
                return rec(vals, indices[0]);
                case 3:
                return 1;
            }
        }
        else{
            indices = new int[3];
            for(i = index + 1; i < index + 4; i++){
                if(vals[i] - vals[index] <= 3){
                    indices[j] = i;
                    j++;
                }
            }
            if(indices[0] == 0) return 0;
            if(indices[1] == 0) return rec(vals, indices[0]);
            if(indices[2] == 0) return rec(vals, indices[0]) + rec(vals, indices[1]);
            return rec(vals, indices[0]) + rec(vals, indices[1]) + rec(vals, indices[2]);
        }
        //for(int ind : indices) System.out.print(ind + " ");
        //System.out.println();
        //if(indices[0] == 0) return 0;
        //if(indices[1] == 0) return rec(vals, indices[0]);
        //if(indices[2] == 0) return rec(vals, indices[0]) + rec(vals, indices[1]);
        //return rec(vals, indices[0]) + rec(vals, indices[1]) + rec(vals, indices[2]);
        //return 1;
        return 0;
    }
    public static void main(String[] args) throws IOException{
        int i = 1, max = 0, diffOne = 0, diffThree = 0, sub, j;
        BigInteger ans = new BigInteger("1");
        BigInteger count, pow;
        int[] vals = new int[100];
        String p;
        //get all ints from file, keeping track of largest
        Scanner s = new Scanner(new File("dayTenInput.txt"));
        while(s.hasNextInt()){
            vals[i] = s.nextInt();
            if(vals[i] > max) max = vals[i];
            i++;
        }
        //final value should be 3 greater than maximum value in file
        vals[i] = max + 3;
        //sort array of values into numerical order
        Arrays.sort(vals);
        //part one
        //for each pair of values, get their difference, determine whether diff is 1 or 3
        for(i = 1; i < vals.length; i++){
            sub = vals[i]-vals[i-1];
            //System.out.println(vals[i] + " - " + vals[i-1] + " = " + sub);
            if(vals[i] - vals[i-1] == 1) diffOne++;
            else if(vals[i] - vals[i-1] == 3) diffThree++;
        }
        //System.out.println(diffOne + " " + diffThree);
        //print requested output
        System.out.println(diffOne * diffThree);

        /*attempt at part 2, doesn't work
        //part two
        //ans = rec(vals, 0);
        for(i = 0; i < vals.length; i++){
            count = new BigInteger("0");
            for(j = i; j < i + 3; j++){
                if(j < vals.length){
                    if(vals[j] - vals[i] <= 3) count = count.add(new BigInteger("1"));
                }
                //System.out.println(ans + " " + count);
                //ans = ans.add(count);
                p = Integer.toString(i);
                pow = new BigInteger(p);
                ans = ans.multiply(pow);
                //ans = ans.multiply(new BigInteger("3").modPow(pow, new BigInteger("100")));
            }
        }*/
        //System.out.println(ans);
    }
}
