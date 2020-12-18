//Mark Dubin
//12/15/2020
//AoC 2020 Day 15: Rambunctious Recitation

import java.util.*;

public class dayFifteen {
    public static void main(String[] args){
        int index = 7, cur, high, low = 0, i;
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(11);
        nums.add(18);
        nums.add(0);
        nums.add(20);
        nums.add(1);
        nums.add(7);
        nums.add(16);

        //part 1
        while(index < 2020){
            //System.out.println(index);
            cur = nums.get(index - 1);
            if(index - 1 == nums.indexOf(cur)){
                nums.add(0);
            }
            else{
                high = nums.lastIndexOf(cur);
                low = -1;
                i = high - 1;
                //while this nested while loop works for part 1, it is way too slow for part 2
                while(low == -1){
                    if(nums.get(i) == cur) low = i;
                    i--;
                }
                nums.add(high - low);
            }
            index++;
        }
        System.out.println(nums.get(index - 1));
    }
}
