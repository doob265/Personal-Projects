//Mark Dubin   
//12/5/2020
//AoC Day 5: Binary Boarding

import java.util.*;
import java.io.*;

public class dayFive {
    public static void main(String[] args) throws IOException{
        String input, row, col;
        int r, c, seat, max = 0, low = 0, high = 127, med, i, index = 0, temp = 0, tem = 0;
        ArrayList<Integer> passes = new ArrayList<Integer>();
        Scanner s = new Scanner(new File("dayFiveInput.txt"));
        index = 0;
        while(s.hasNextLine()){
            input = s.nextLine();
            //given that first 7 chars are for row
            row = input.substring(0, 7);
            //System.out.println(row);
            //128 possible rows
            low = 0;
            high = 127;
            //continually calculate the middle, adjust low/high based on char being F/B
            for(i = 0; i < 7; i++){
                med = (high - low) / 2;
                //System.out.println("m " + med);
                if(row.charAt(i) == 'F'){
                    high -= med;
                    high--;
                } else{
                    low += med;
                    low++;
                }
                //System.out.println(low + ", " + high);
            }
            //low and high are now same, save final row
            r = low;
            //very similar process to how row is calculated
            col = input.substring(7, input.length());
            low = 0;
            high = 7;
            //System.out.println(col);
            for(i = 0; i < 3; i++){
                med = (high - low) / 2;
                if(col.charAt(i) == 'L'){
                    high -= med;
                    high--;
                } else{
                    low += med;
                    low++;
                }
                //System.out.println(low + ", " + high);
            }
            //low and high are now same
            c = low;
            //given formula for seat ID
            seat = (r * 8) + c; 
            //if(seat == 0) System.out.println(seat);
            //System.out.println(seat);
            //add seat to arraylist of seats
            passes.add(seat);
            //part one: finding maximum valued seat ID
            if(seat > max) max = seat;
        }
        //Part 2: Finding our seat
        //sort passes in numerical order
        Collections.sort(passes);
        /*for(int pass : passes){
            System.out.println(pass);
        }*/
        for(int pass : passes){
            temp = pass;
            //the two seats that don't have a difference of 1 are directly adjacent to our seat
            if(temp - tem != 1 && tem != 0){
                System.out.println("Part One: " + max + " Part Two: " + ((temp + tem) / 2));
                return;
            } else{
                tem = temp;
            }
        }
    }
}
