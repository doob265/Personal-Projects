//Mark Dubin
//12/07/2020
//AoC 2020 Day 7: Havey Haversacks
//Part 2 incomplete

import java.util.*;
import java.io.*;

public class daySeven {

    //recursive method for part 1 will return number of bags that can contain a shiny gold bag
    public static int recOne(Map<String, Integer> map, Map<String, Integer> used) throws IOException{
        //System.out.println("rec");
        String temp, sub;
        int start, ans = 0, n;
        Map<String, Integer> next = new HashMap<String, Integer>();
        Scanner s = new Scanner(new File("daySevenInput.txt"));
        /*for(Map.Entry<String, Integer> ent : map.entrySet()){
            System.out.println(ent);
        }*/
        //need to go through each line of file again
        while(s.hasNextLine()){
            temp = s.nextLine();
            for(Map.Entry<String, Integer> ent : map.entrySet()){
                //bag hasn't been used, line has shiny gold at beginning of line, increase counter
                if(!used.containsKey(ent.getKey()) && temp.contains(ent.getKey()) && temp.indexOf(ent.getKey()) == 0) ans++;
                //bag hasn't been used, line has shiny gold not at beginning of line
                else if(!used.containsKey(ent.getKey()) && temp.contains(ent.getKey()) && temp.indexOf(ent.getKey()) != 0){
                    //put this bag into map that will be passed recursively
                    start = temp.indexOf("bags");
                    sub = temp.substring(0, start);
                    //ans++;
                    n = Integer.parseInt(temp.substring(start+13, start+14));
                    //System.out.println(temp + " " + start + " " + sub + " " + n);
                    next.put(sub, n);
                    //map.put(sub, n);
                }
            }
        }
        //put everything in current map into used map
        used.putAll(map);
        //if next is empty, no more bags need to be checked, return
        if(next.size() == 0) return ans;
        //recursively call with next and used
        return ans + recOne(next, used);
    }

    //not-working recursive method for part 2 to find how many bags in shiny gold bag
    public static int recTwo(Map<String, Integer> map, Map<String, Integer> used) throws IOException{
        int ans = 0, low, high, n, val;
        String temp, num, sub;
        boolean done = false;
        Map<String, Integer> next = new HashMap<String, Integer>();
        Scanner s = new Scanner(new File("daySevenInput.txt"));
        /*for(Map.Entry<String, Integer> ent : map.entrySet()){
            System.out.println(ent);
        }*/
        //go back through file again
        while(s.hasNextLine()){
            temp = s.nextLine();
            for(Map.Entry<String, Integer> ent : map.entrySet()){
                if(temp.contains(ent.getKey() + " contain")){
                    //System.out.println(ent);
                    //System.out.println(temp);
                    if(!temp.contains("no other bags.")){
                        //System.out.println(ent);
                        //System.out.println(temp);
                        //System.out.println();
                        low = temp.indexOf("contain") + 8;
                        num = temp.substring(low, low + 1);
                        //System.out.println(num);
                        low += 2;
                        n = Integer.parseInt(num);
                        high = temp.indexOf(",");
                        if(high == -1){
                            done = true;
                            high = temp.indexOf(".");
                        }
                        sub = temp.substring(low, high);
                        if(n == 1) sub = sub.concat("s");
                        next.put(sub, n);
                        val = ent.getValue() * n;
                        val += ent.getValue();
                        ans += val;
                        //System.out.println(ent.getValue() + " * " + n + " + " + val);
                        while(temp.indexOf(",", high + 1) != -1){
                            low = high + 2;
                            num = temp.substring(low, low+1);
                            low += 2;
                            n = Integer.parseInt(num);
                            high = temp.indexOf(",", high + 1);
                            sub = temp.substring(low, high);
                            if(n == 1) sub = sub.concat("s");
                            next.put(sub, n);
                            val = ent.getValue() * n;
                            val += ent.getValue();
                            ans += val;
                            //System.out.println(ent.getValue() + " * " + n + " + " + val);
                        }
                        if(!done){
                            low = high + 2;
                            num = temp.substring(low, low+1);
                            low += 2;
                            n = Integer.parseInt(num);
                            high = temp.indexOf(".");
                            sub = temp.substring(low, high);
                            if(n == 1) sub = sub.concat("s");
                            next.put(sub, n);
                            val = ent.getValue() * n;
                            val += ent.getValue();
                            ans += val;
                            //System.out.println(ent.getValue() + " * " + n + " + " + val);
                        }
                    }
                    //else System.out.println(temp);
                }
            }
        }
        used.putAll(map);
        /*for(Map.Entry<String, Integer> ent : next.entrySet()){
            System.out.println(ent);
        }
        System.out.println();*/
        if(next.size() == 0) return ans;
        return ans + recTwo(next, used);
    }
    public static void main(String[] args) throws IOException{
        String temp, sub, num;
        int ans = 0, start, n, low, high;
        Map<String, Integer> mapOne = new HashMap<String, Integer>(), mapTwo = new HashMap<String, Integer>(), used = new HashMap<String, Integer>();
        Scanner s = new Scanner(new File("daySevenInput.txt"));
        //go through each line individually
        while(s.hasNextLine()){
            temp = s.nextLine();
            //part one
            //store in map if line begins with "shiny gold"
            if(temp.contains("shiny gold")){
                if(!temp.substring(0, 10).equals("shiny gold")){
                    start = temp.indexOf("bags");
                    sub = temp.substring(0, start);
                    n = Integer.parseInt(temp.substring(start+13, start+14));
                    mapOne.put(sub, n);
                }
            }
            //part two
            if(temp.contains("shiny gold bags contain")){
                low = temp.indexOf("contain") + 8;
                num = temp.substring(low, low + 1);
                low += 2;
                n = Integer.parseInt(num);
                high = temp.indexOf(",");
                sub = temp.substring(low, high);
                if(n == 1) sub = sub.concat("s");
                mapTwo.put(sub, n);
                while(temp.indexOf(",", high + 1) != -1){
                    low = high + 2;
                    num = temp.substring(low, low+1);
                    low += 2;
                    n = Integer.parseInt(num);
                    high = temp.indexOf(",", high + 1);
                    sub = temp.substring(low, high);
                    if(n == 1) sub = sub.concat("s");
                    mapTwo.put(sub, n);
                }
                low = high + 2;
                num = temp.substring(low, low+1);
                low += 2;
                n = Integer.parseInt(num);
                high = temp.indexOf(".");
                sub = temp.substring(low, high);
                if(n == 1) sub = sub.concat("s");
                mapTwo.put(sub, n);
            }
        }

        //recOne will go through map, finding number of bags can eventually contain 1 shiny gold bag, print result
         ans += recOne(mapOne, used);
         System.out.println("Part one: " + ans);
        /*for(Map.Entry<String, Integer> ent : map.entrySet()){
            System.out.println(ent);
        }*/
        //System.out.println();
        s.close();
        ans = recTwo(mapTwo, used);
        System.out.println("Part two: " + ans);
    }
}
