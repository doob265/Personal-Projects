//Mark Dubin
//12/25/2020
//AoC Day 25: Combo Breaker

import java.util.*;
import java.io.*;
import java.math.*;

public class dayTwentyFive{
    public static void main(String[] args) throws IOException{
        int i, cLoop = 0, dLoop = 0;
        BigInteger val, sub, card, door;

        //will be using first line of input as card public key, second line as door public key
        Scanner s = new Scanner(new File("dayTwentyFiveInput.txt"));
        card = new BigInteger(s.nextLine());
        door = new BigInteger(s.nextLine());

        //go through transformation infinitely until val becomes equal to card public key, storing loop size
        val = new BigInteger("1");
        sub = new BigInteger("7");
        while(!val.equals(card)){
            val = val.multiply(sub);
            val = val.mod(new BigInteger("20201227"));
            cLoop++;
        }
        //System.out.println(card + " " + cLoop + " " + val);

        //go through transformation infinitely until val becomes equal to door public key, storing loop size
        val = new BigInteger("1");
        sub = new BigInteger("7");
        while(!val.equals(door)){
            val = val.multiply(sub);
            val = val.mod(new BigInteger("20201227"));
            dLoop++;
        }
        //System.out.println(door + " " + dLoop + " " + val);

        //to find encryption key, go through transformation card loop size times using door public key as subject number 
        val = new BigInteger("1");
        for(i = 0; i < cLoop; i++){
            val = val.multiply(door);
            val = val.mod(new BigInteger("20201227"));
        }
        System.out.println(val);

        //to verify encryption key, go through transformation door loop size times using card public key as subject number 
        val = new BigInteger("1");
        for(i = 0; i < dLoop; i++){
            val = val.multiply(card);
            val = val.mod(new BigInteger("20201227"));
        }
        System.out.println(val);
    }
}
