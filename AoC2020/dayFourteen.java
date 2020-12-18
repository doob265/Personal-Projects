//Mark Dubin    
//12/14/2020
//AoC 2020 Day 14: Docking Data
//No solution for part 2

import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class dayFourteen {
    public static void main(String[] args) throws IOException{
        final int len = 36;
        BigInteger[] mem = new BigInteger[100000];
        BigInteger ans = new BigInteger("0");
        int adr, val, i, l, j = 0;
        Scanner s = new Scanner(new File("dayFourteenInput.txt"));
        String temp, m, binary;
        char[] value = new char[len], mask = new char[len];
        char[] res = new char[len];
        //part 2 
        //Map<BigInteger, BigInteger> mem = new HashMap<BigInteger, BigInteger>();

        while(s.hasNextLine()){
            temp = s.nextLine();
            //mask value found
            if(temp.substring(0, 4).equals("mask")){ 
                m = temp.substring(7);
                mask = m.toCharArray();
                //System.out.println("mask: " + m);
            }
            //if not mask, address
            else{
                adr = Integer.parseInt(temp.substring(4, temp.indexOf("]")));
                //System.out.println("adr: " + adr);
                val = Integer.parseInt(temp.substring(temp.indexOf("=")+2));
                //part one
                binary = Integer.toBinaryString(val);
                //part two
                //binary = Integer.toBinaryString(adr);
                l = binary.length();
                j = 0;
                //add in leading 0's as necessary
                for(i = 0; i < len; i++){
                    if(i < len - l){
                        value[i] = '0';
                    }
                    else {
                        value[i] = binary.charAt(j);
                        j++;
                    }
                }
                //calculate result
                for(i = 0; i < len; i++){
                    //res = value;
                    //part 1
                    if(mask[i] == '1') res[i] = '1';
                    else if(mask[i] == '0') res[i] = '0';
                    else res[i] = value[i];
                    //part 2 attempt
                    /*for(j = 0; j <= 1; j++){
                        if(mask[i] == '1') res[i] = '1';
                        else if(mask[i] == '0') res[i] = '0';
                        else{
                            if(j == 0) res[i] = '0';
                            else res[i] = '1';
                        }
                    }*/
                }
                //part 2 attempt
                /*for(i = 0; i < len; i++){
                    System.out.println("adr:  " + new String(value) + " res " + new String(res));
                    binary = new String(res);
                    System.out.println("adr " + new BigInteger(binary, 2) + " val " + temp.substring(temp.indexOf("=")+2));
                    //mem[new BigInteger(binary, 2).intValue()] = new BigInteger(temp.substring(temp.indexOf("=")+2));
                    if(!mem.containsKey(new BigInteger(binary, 2))) mem.put(new BigInteger(binary, 2), new BigInteger(temp.substring(temp.indexOf("=")+2)));
                    else{
                        mem.replace(new BigInteger(binary, 2), new BigInteger(temp.substring(temp.indexOf("=")+2)));
                    }
                }*/
                //put decimal version of result into memory at address
                binary = new String(res);
                mem[adr] = new BigInteger(binary, 2);
            }
        }
        //add up all decimal results and output
        for(BigInteger v : mem){
            try{
                ans = ans.add(v);
            } catch(NullPointerException e){

            }
        } 
        //part 2
        /*for(Map.Entry<BigInteger, BigInteger> v : mem.entrySet()){
            try{
                ans = ans.add(v.getValue());
            } catch(NullPointerException e){

            }
        }*/
        System.out.println("Part 1: " + ans);
    }
}
