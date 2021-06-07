//Mark Dubin
//12/1/2020
//AoC Day 1: Report Repair

import java.util.*;
import java.io.*;

public class dayOne {
    public static void main(String[]args) throws IOException{
        int[] nums = new int[200];
        int i = 0, j, k, sum;
        Scanner scan = new Scanner(new File("dayOneInput.txt"));
        while(scan.hasNextInt()){
            nums[i] = scan.nextInt();
            i++;
        }
        //part 1: find two values that sum to 2020
        for(i = 0; i < nums.length; i++){
            for(j = i; j < nums.length; j++){
                sum = 0;
                sum = nums[i] + nums[j];
                if(sum == 2020){
                    System.out.println("Part One: " + nums[i] * nums[j]);
                    break;
                }
            }
        }
        //part two: find 3 values that sum to 2020
        for(i = 0; i < nums.length; i++){
            for(j = i; j < nums.length; j++){
                for(k = j; k < nums.length; k++){
                    sum = 0;
                    sum = nums[i] + nums[j] + nums[k];
                    if(sum == 2020){
                        System.out.println("Part two: " + nums[i] * nums[j] * nums[k]);
                        return;
                    }
                }
            }
        }
    }
}
