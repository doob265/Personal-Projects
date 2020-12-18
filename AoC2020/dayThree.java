//Mark Dubin
//12/3/2020
//AoC Day 3: Toboggan Trajectory

import java.util.*;
import java.io.*;

public class dayThree {
    public static void main(String[] args) throws IOException{
        String temp;
        int one = 1, two = 1, three = 3, five = 5, seven = 7, oneCnt = 0, twoCnt = 0, threeCnt = 0, fiveCnt = 0, sevenCnt = 0, line = 2;
        Scanner scan = new Scanner(new File("dayThreeInput.txt"));
        temp = scan.nextLine();
        while(scan.hasNextLine()){
            temp = scan.nextLine();
            //System.out.println(temp + " " + x + " " + temp.charAt(x));
            //on tree
            if(temp.charAt(one) == '#') oneCnt++;
            //moving over one, wrapping around if necessary
            one += 1;
            one %= temp.length();

            //calculates for every other line
            if(line % 2 == 1){
                //on tree
                if(temp.charAt(two) == '#') twoCnt++;
                //moving over one, wrapping if necessary
                two += 1;
                two %= temp.length();
            }

            //part one
            //on tree
            if(temp.charAt(three) == '#') threeCnt++;
            //moving over 3, wrapping around if necessary
            three += 3;
            three %= temp.length();

            //on tree
            if(temp.charAt(five) == '#') fiveCnt++;
            //moving over 5, wrapping around if necessary
            five += 5;
            five %= temp.length();

            //on tree
            if(temp.charAt(seven) == '#') sevenCnt++;
            //moving over 7, wrapping around if necessary
            seven += 7;
            seven %= temp.length();

            //incrementing line tracker
            line++;
        }
        System.out.println("Part One: " + threeCnt);
        System.out.println("Part Two: " + oneCnt * twoCnt * threeCnt * fiveCnt * sevenCnt);
    }
}
