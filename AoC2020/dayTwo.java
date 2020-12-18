//Mark Dubin   
//12/02/2020
//AoC Day 2: Password Philosophy

import java.util.*;
import java.io.*;

public class dayTwo {
    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(new File("dayTwoInput.txt"));
        int low, high, index = 2, i, count = 0, answerOne = 0, answerTwo = 0, line = 0;
        String temp, num, letter, str;
        char[] arr, let;
        boolean checkLow = false, checkHigh = false;
        while(scan.hasNextLine()){
            //System.out.println("line " + line);
            //line++;
            index = 0;
            count = 0;
            temp = scan.nextLine();
            //get beginning of low bounds
            num = temp.substring(0, 1);
            checkLow = false;
            try{
                //if multiple digits, get rest of low bounds
                Integer.parseInt(temp.substring(1, 2));
                num = num.concat(temp.substring(1, 2));
                checkLow = true;
            } catch (NumberFormatException nfe){
            }
            low = Integer.parseInt(num);
            //System.out.println(low);
            //starting place of high bounds
            if(checkLow) index = 3;
            else index = 2;
            //System.out.println("index " + index);
            //get high bounds using same process as low
            num = temp.substring(index, index+1);
            checkHigh = false;
            try{
                Integer.parseInt(temp.substring(index+1, index+2));
                num = num.concat(temp.substring(index+1, index+2));
                checkHigh = true;
            } catch (NumberFormatException nfe){
            }
            high = Integer.parseInt(num);
            //System.out.println(high);
            //position of letter to check
            if(checkLow && checkHigh) index = 6;
            else if(checkLow || checkHigh) index = 5;
            else index = 4;
            letter = temp.substring(index, index+1);
            //System.out.println(letter);
            //position of password
            index += 3;
            str = temp.substring(index);
            //System.out.println(str);
            let = letter.toCharArray();
            arr = str.toCharArray();
            //part one
            //check how many occurances of the letter there are
            for(i = 0; i < arr.length; i++){
                if(arr[i] == let[0]) count++;
            }
            //System.out.println("line " + line + " low " + low + " high " + high + " char " + letter + " str " + str + " count " + count);
            //if in bounds, valid
            if(count >= low && count <= high) answerOne++;
            //move to next line
            line++;
            //part two
            //check if letter occurs at either index, but not both
            if((arr[low-1] == let[0] && arr[high-1] != let[0]) || (arr[low-1] != let[0] && arr[high-1] == let[0])) answerTwo++;
        }
        System.out.println("Part One: " + answerOne + " Part Two: " + answerTwo);
    }
}
