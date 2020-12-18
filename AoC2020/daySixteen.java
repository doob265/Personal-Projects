//Mark Dubin
//12/16/2020
//AoC Day 16: Ticket Translation
//Part 2 solution is incorrect

import java.util.*;
import java.io.*;
import java.math.*;

public class daySixteen {

    //checks part 1 for invalid tickets
    public static boolean checkOne(int[]restrictions, int c){
        int i;
        for(i = 0; i < restrictions.length; i += 4){
            if((c >= restrictions[i] && c <= restrictions[i+1]) || (c >= restrictions[i+2] && c <= restrictions[i+3])) return true;
            else if(c < restrictions[i]) return false;
        }
        return false;
    }

    //returns indicies of all possible valid combos for part 2
    public static ArrayList<Integer> checkTwo(int[]restrictions, int c){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        int i;
        for(i = 0; i < restrictions.length; i += 4){
            if((c >= restrictions[i] && c <= restrictions[i+1]) || (c >= restrictions[i+2] && c <= restrictions[i+3])) ret.add(i / 4);
        }
        return ret;
    }
   public static void main(String[] args) throws IOException{
    int line = 0, i = 0, l, h, c, index, ans = 0, ind, j;
    BigInteger cnt = new BigInteger("1");
    String temp, low, high, cur;
    int[] restrictions = new int[80], num = new int[20];
    boolean valid = false;
    Scanner s = new Scanner(new File("daySixteenInput.txt"));
    ArrayList<Integer> current = new ArrayList<Integer>(), fin = new ArrayList<Integer>(), count = new ArrayList<Integer>(), mine = new ArrayList<Integer>();;
    ArrayList<ArrayList<Integer>> usable = new ArrayList<ArrayList<Integer>>(), counts = new ArrayList<ArrayList<Integer>>();
    //needs to be blank for part 2
    for(i = 0; i < 20; i++) fin.add(0);
    while(s.hasNextLine()){
        i = 0;
        //first 20 lines will be directions
        while(line < 20){
            temp = s.nextLine();
            //find first pair of bounding values
            low = temp.substring(temp.indexOf(":") + 2, temp.indexOf("-"));
            high = temp.substring(temp.indexOf("-") + 1, temp.indexOf(" or "));
            l = Integer.parseInt(low);
            h = Integer.parseInt(high);
            //System.out.println(l + " " + h);
            restrictions[i] = l;
            i++;
            restrictions[i] = h;
            i++;
            //find second pair of bounding values
            low = temp.substring(temp.indexOf(" or ") + 4, temp.lastIndexOf("-"));
            high = temp.substring(temp.lastIndexOf("-") + 1);
            l = Integer.parseInt(low);
            h = Integer.parseInt(high);
            //System.out.println(l + " " + h);
            restrictions[i] = l;
            i++;
            restrictions[i] = h;
            i++;
            line++;
        }
        //for(int n : restrictions) System.out.print(n + " ");
        //return;
        temp = s.nextLine();
        line = 25;
        //store user ticket for part 2
        if(temp.equals("your ticket:")){
            temp = s.nextLine();
                index = 0;
                line++;
                //get every value but last of line
                while(temp.indexOf(",", index) != -1){
                    cur = temp.substring(index, temp.indexOf(",", index));
                    //System.out.print(cur + " ");
                    c = Integer.parseInt(cur);
                    mine.add(c);
                    index = temp.indexOf(",", index) + 1;
                }
                //get last value of line
                cur = temp.substring(temp.lastIndexOf(",") + 1);
                //System.out.print(cur + " ");
                c = Integer.parseInt(cur);
                mine.add(c);
                index = temp.indexOf(",", index) + 1;
        }
        //get all other ticket values
        if(temp.equals("nearby tickets:")){
            //continue till EoF
            while(s.hasNextLine()){
                valid = true;
                temp = s.nextLine();
                index = 0;
                line++;
                //reset current every time
                current = new ArrayList<Integer>();
                //get all values of line except last
                while(temp.indexOf(",", index) != -1){
                    cur = temp.substring(index, temp.indexOf(",", index));
                    //System.out.print(cur + " ");
                    c = Integer.parseInt(cur);
                    current.add(c);
                    index = temp.indexOf(",", index) + 1;
                    //Part 1: first time checkOne returns false, increment answer by c
                    if(valid){ 
                        valid = checkOne(restrictions, c);
                        if(!valid) ans += c;
                    }
                }
                //get last value of line
                cur = temp.substring(temp.lastIndexOf(",") + 1);
                //System.out.print(cur + " ");
                c = Integer.parseInt(cur);
                current.add(c);
                index = temp.indexOf(",", index) + 1;
                //Part 1: first time checkOne returns false, increment answer by c
                if(valid){ 
                    valid = checkOne(restrictions, c);
                    if(!valid) ans += c;
                }
                if(valid) usable.add(current);
                
                //System.out.println(s.hasNextLine());
            }
        }
    }
    //System.out.println(usable.size());
    /*for(ArrayList<Integer> a : usable){
        System.out.println(a.size());
        for(Integer n : a){
            System.out.print(n + " ");
        }
        System.out.println();
    }*/
    //Part 2
    for(i = 0; i < 20; i++){
        count = new ArrayList<Integer>();
        current = new ArrayList<Integer>();
        num = new int[20];
        //get all possible matches for every usable ticket
        for(ArrayList<Integer> a : usable){
            //System.out.print(a.get(i) + " ");
            current.addAll(checkTwo(restrictions, a.get(i)));
            //System.out.println();
        }
        //System.out.println();
        //System.out.print("current: ");
        //for(int n : current) System.out.print(n + " ");
        //System.out.println();
        //reformat to match indices
        for(int n : current){
            if(n < 20) num[n]++;
        }
        //convert to arraylist
        for(int n : num) count.add(n);
        //System.out.print("count at index " + i + ": ");
        //all counts
        counts.add(count);
        ind = -1;
        //look for only max count on each row
        for(j = 0; j < 20; j++){ 
            //System.out.print(num[j] + " ");
            if(num[j] == usable.size() && ind == -1) ind = j;
            else if(num[j] == usable.size() && ind > -1) ind = -2;
        }
        //found unique max, add index to final indicies list
        if(ind > -1){ 
            fin.add(i, ind);
            fin.remove(i+1);
            //counts.remove(i);
        }
        //System.out.println();
    }
    //in "matrix", remove this val from all rows
    for(int n : fin){ 
        //System.out.print(n + " ");
        if(n != 0){
            for(ArrayList<Integer> a : counts){
                a.add(n, -1);
                a.remove(n+1);
            }
        }
    }

   /*System.out.println();
    for(ArrayList<Integer> a : counts){
        for(int n : a) System.out.print(n + " ");
        System.out.println();
    }*/

    //continue same process as above until fin is filled out with values
    while(fin.contains(0)){
        i = 0;
        for(ArrayList<Integer> a : counts){
            ind = -1;
            for(int n : a){
                if(n == usable.size() && ind == -1){ 
                    ind = a.indexOf(usable.size());
                    //if(i == 14) System.out.print("row " + i + " index " + ind + " count is 190.");
                }
                else if(n == 190 && ind > -1){
                    ind = -2;
                    //if(i == 14) System.out.print("row " + i + " index " + ind + " count is 190.");
                }
            }
            //System.out.println();
            if(ind > -1){ 
                //if(i == 14) System.out.println("row " + i + " index " + ind + " adding and removing.");
                fin.add(i, ind + 1);
                //System.out.println("fin added: ");
                //for(int n : fin) System.out.print(n + " ");
                //System.out.println();
                fin.remove(i+1);
                //System.out.println("fin removed : ");
                //for(int n : fin) System.out.print(n + " ");
                //System.out.println();
                //counts.remove(i);
            }
            i++;
        }
        //System.out.println();
        //System.out.println("fin: ");
        for(int n : fin){ 
            //System.out.print(n + " ");
            if(n != 0){
                for(ArrayList<Integer> a : counts){
                    a.add(n-1, -1);
                    a.remove(n);
                }
            }
        }
        /*System.out.println();
        for(ArrayList<Integer> a : counts){
            for(int n : a) System.out.print(n + " ");
            System.out.println();
        }*/
    }
    
   //System.out.println();

    /*for(int n : mine) System.out.print(n + " ");
    System.out.println();
    for(int n : fin){
        System.out.print(n + " ");
    }
    System.out.println();*/

    //six departue values at beginning of input file
    for(i = 0; i < 6; i++){
        index = fin.indexOf(i+1);
        //System.out.print(index + " ");
        //System.out.println(mine.get(index));
        cnt = cnt.multiply(new BigInteger(Integer.toString(mine.get(index))));
    }

    System.out.println("Part 1: " + ans);
    System.out.println("Part 2: " + cnt);
   } 
}
