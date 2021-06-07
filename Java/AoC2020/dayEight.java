//Mark Dubin
//12/08/2020
//Aoc 2020 Day 8: Handled Halting

import java.util.*;
import java.io.*;

public class dayEight {
    //part two
    public static int test(String[] ins, int[] vals){
        int[] visited = new int[629];
        int index = 0, acc = 0;
        while(visited[index] == 0 && index < 628){
            visited[index] = 1;
            if(ins[index].equals("acc")){
                acc += vals[index];
                index++;
            }
            else if(ins[index].equals("jmp")){
                index += vals[index];
            }
            else{
                index++;
            }
        }
        if(visited[index] == 1) return -1;
        else if(index >= 628) return acc;
        return -1;
    }
    public static void main(String[] args) throws IOException{
        String temp, ins, num;
        String[] set = new String[629], copy = new String[629];
        Scanner s = new Scanner(new File("dayEightInput.txt"));
        int acc = 0, n, index = 0, i;
        int [] visited = new int[629], vals = new int[629];
        //get every line from file, make ints negative if necessary
        while(s.hasNextLine()){
            temp = s.nextLine();
            ins = temp.substring(0, 3);
            num = temp.substring(3);
            n = Integer.parseInt(num.substring(1));
            if(num.charAt(0) == '-') n -= 2*n;
            //System.out.println(ins + " " + n);
            vals[index] = n;
            set[index] = ins;
            index++;
        }
        index = 0;
        //part 1
        //go through until a index is visited twice
        while(visited[index] == 0){
            //mark current index as visited
            visited[index] = 1;
            //if at a acc, increas counter by value
            if(set[index].equals("acc")){
                acc += vals[index];
                index++;
            }
            //if jmp, jmp by specified amount
            else if(set[index].equals("jmp")){
                index += vals[index];
            }
            //would be at a nop, so move to next line
            else{
                index++;
            }
        }
        System.out.println("Part one: " + acc);

        //part two
        //make a copy of instruction set
        copy = set;
        for(i = 0; i < set.length; i++){
            //will attempt to change this jmp
            if(set[i].equals("jmp")){
                copy[i] = "nop";
                //run test to see if program completes
                acc = test(copy, vals);
                //if acc is -1, test returned a completed program with altered jmp to nop, print counter and return
                if(acc > -1){
                    System.out.println("Part two: " + acc);
                    return;
                }
                //if didn't return, reset
                copy[i] = "jmp";
            }
            //will attempt to change this nop
            else if(set[i].equals("nop")){
                copy[i] = "jmp";
                //run test to see if program completes
                acc = test(copy, vals);
                //if acc is -1, test returned a completed program with altered nop to jmp, print counter and return
                if(acc > -1){
                    System.out.println("Part two: " + acc);
                    return;
                }
                //if didn't return, reset
                copy[i] = "nop";
            }
        }
    }
}
