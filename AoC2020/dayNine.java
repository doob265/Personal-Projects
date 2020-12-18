//Mark Dubin
//12/09/2020
//Aoc 2020 Day 9: Encoding Error

import java.util.*;
import java.io.*;
import java.math.*;

public class dayNine {
    //finds first invalid value in file, based on rules of problem
    public static BigInteger findInvalid(BigInteger[] nums){
        int i, j, k, low = 0, high = 24;
        boolean valid = false;
        BigInteger invalid = new BigInteger("-1");
        for(i = 0; i < nums.length; i++){
            //reset valid to false every time
            valid = false;
            for(j = low; j <= high; j++){
                for(k = low; k <= high; k++){
                    //if two separate values have been found that are equal, nums[high+1] is valid
                    if(j != k && nums[j].add(nums[k]).equals(nums[high + 1])){
                        //System.out.println(nums[j] + " + " + nums[k] + " = " + nums[j].add(nums[k]));
                        valid = true;
                        k = high;
                        j = high;
                    }
                }
            }
            //if valid is still false, invalid value has been found, return it
            if(!valid){
                System.out.println(nums[high+1]);
                invalid = nums[high+1];
                return invalid;
            }
            //if haven't returned, increment low and high and repeat
            low++;
            high++;
        }
        //should never be reached
        return invalid;
    }
    public static void main(String[] args) throws IOException{
        boolean valid = false;
        String temp;
        BigInteger[] nums = new BigInteger[1000];
        BigInteger invalid, sum, min, max;
        int i = 0, j, k, low = 0, high = 1;
        //get every num from file, need BigInteger to store larger ones
        Scanner s = new Scanner(new File("dayNineInput.txt"));
        while(s.hasNextBigInteger()){
            nums[i] = s.nextBigInteger();
            i++;
        }
        //for(i = 0; i < nums.length; i++) System.out.println(nums[i] + " " + i);
        //part 1: findInvalid function finds first invalid value in file
        invalid = findInvalid(nums);
        if(invalid.equals(new BigInteger("-1"))) return;
        //print out first invalid value, which is part 1's output
        System.out.println("Part one: " + invalid);

        //part 2
        //will keep running until program ends
        while(true){
            sum = new BigInteger("0");
            //System.out.println(low + " " + high);
            //will get sum of first ith items in list, with i increasing by 1 after each while loop iteration
            for(i = low; i < high; i++){
                sum = sum.add(nums[i]);
            }
            //System.out.println(sum);
            //if sum equals invalid, correct subset of nums has been found
            if(sum.equals(invalid)){
                min = new BigInteger("0");
                max = new BigInteger("999999999999999999999999999999");
                //find min and max value in subset
                for(j = low; j < high; j++){
                    if(nums[j].compareTo(min) > 0) min = nums[j];
                    if(nums[j].compareTo(max) < 0) max = nums[j];
                }
                //System.out.println(min + " " + max);
                //print addition of low and high and terminate
                System.out.println("Part two: " + min.add(max));
                return;
            }
            //If correct subset has been found, increase high. If high is at end of list, increase low and reset high.
            high++;
            if(high == 1000){
                low++;
                high = low + 1;
            }
        }
    }
}
