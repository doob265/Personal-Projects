//Mark Dubin
//12/6/2020
//AoC 2020 Day 6: Custom Customs

import java.util.*;
import java.io.*;

public class daySix {
    //function calculates num of yes's for part 1
    public static int calcOne(String str){
        int count, i;
        String c, sub;
        count = 0;
        //System.out.println(str);
        for(i = 0; i < str.length(); i++){
            //get char at i
            c = str.substring(i, i+1);
            //System.out.print(" " + c);
            //everything looked at already
            sub = str.substring(0, i);
            //if c is a new char, increment count
            if(!sub.contains(c)) count++;
        }
        //System.out.println(" " + count);
        //return final count
        return count;
    }

    //function calculates num of yes's for part 1
    public static int calcTwo(ArrayList<String> lines){
        int count = 0, i;
        String c, sub;
        int[] ints = new int[26];
        //1 line is 1 person, can use part 1 func for this
        if(lines.size() == 1) return calcOne(lines.get(0));
        else{
            //go through each line
            for(String str : lines){
                for(i = 0; i < str.length(); i++){
                    //get cur char and everything previous
                    c = str.substring(i, i+1);
                    sub = str.substring(0, i);
                    //tracking how many people say yes to each question
                    if(!sub.contains(c)) ints[c.codePointAt(0) - 97]++;
                }
            }
            //for everyone to have said yes, index i must be equal to line size
            for(i = 0; i < ints.length; i++){
                if(ints[i] == lines.size()) count++;
            }
        }
        return count;
    }
    public static void main(String[] args) throws IOException{
        String temp, str = "";
        Scanner s = new Scanner(new File("daySixInput.txt"));
        int ans = 0, eve = 0;
        ArrayList<String> lines = new ArrayList<String>();

        while(s.hasNextLine()){
            temp = s.nextLine();
            //blank line indicates next group of answers
            if(!temp.isBlank()){
                str = str.concat(temp);
                lines.add(temp);
                //special case for very end of file, can't check for blank line
                if(!s.hasNextLine()){
                    //ans is counter for part 1, eve is counter for part 2
                    ans += calcOne(str);
                    eve += calcTwo(lines);
                    //reset lines and str
                    lines.clear();
                    str = "";
                }
            }
            //reached blank, run calculations
            else{
            ans += calcOne(str);
            eve += calcTwo(lines);
            //reset lines and str
            str = "";
            lines.clear();
            }
        }
        //print both answers
        System.out.println("Part 1: " + ans + " Part 2: " + eve);
    }
}
