//Mark Dubin
//12/23/2020
//AoC Day 23: Crab Cups

import java.util.*;

public class dayTwentyThree {
    public static void main(String[] args){
        int i, cur, start, j, dest;
        int[] pick;
        ArrayList<Integer> cups = new ArrayList<Integer>();

        //given input
        cups.add(9);
        cups.add(4);
        cups.add(2);
        cups.add(3);
        cups.add(8);
        cups.add(7);
        cups.add(6);
        cups.add(1);
        cups.add(5);

        for(i = 10; i <= 1000000; i++) cups.add(i);

        //test input
        /*cups.add(3);
        cups.add(8);
        cups.add(9);
        cups.add(1);
        cups.add(2);
        cups.add(5);
        cups.add(4);
        cups.add(6);
        cups.add(7);*/

        cur = cups.get(0);

        //System.out.println("start: " + cups);

        //part 1, too slow for part 2
        for(i = 0; i < 10000000; i++){
            pick = new int[3];
            start = (cups.indexOf(cur) + 1) % cups.size();
            //System.out.print("cur: " + cur + " start " + start + " pick-up: ");
            for(j = 0; j < 3; j++){
                pick[j] = cups.get(start);
                cups.remove(start);
                start %= cups.size();
                //System.out.print(pick[j] + " ");
                //start++;
            }
            //System.out.println();
            dest = cur - 1;
            if(dest < 1) dest = 9;
            while(cups.indexOf(dest) == -1){
                dest--;
                if(dest < 1) dest = 9;
            }
            //System.out.println(" destination: " + dest);
            for(j = 0; j < 3; j++){
                cups.add((cups.indexOf(dest) + j + 1), pick[j]);
            }
            cur = cups.get((cups.indexOf(cur) + 1) % cups.size());
            System.out.println("end of round " + i);
            //System.out.println();
        }

        System.out.println("end");
    }
}
