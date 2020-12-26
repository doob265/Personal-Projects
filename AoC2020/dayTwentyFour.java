//Mark Dubin
//12/24/2020
//AoC Day 24: Lobby Layout

import java.util.*;
import java.io.*;

public class dayTwentyFour {
    public static void main(String[] args) throws IOException{
        int i, x, y, cur = 0, ans = 0;
        int[] coords = new int[2];
        Scanner s = new Scanner(new File("dayTwentyFourInput.txt"));
        ArrayList<String> dirs = new ArrayList<String>();
        String temp, dir;
        Map<Integer[], Integer> map = new HashMap<Integer[], Integer>();
        boolean present = false;

        while(s.hasNextLine()){
            temp = s.nextLine();
            x = 0;
            y = 0;
            present = false;
            dirs = new ArrayList<String>();
            System.out.println(temp);
            dir = "";
            for(i = 0; i < temp.length(); i++){
                if(i == temp.length() - 1) dir = dir.concat(temp.substring(i));
                else dir = dir.concat(temp.substring(i, i+1));
                if(temp.charAt(i) != 'n' && temp.charAt(i) != 's'){
                    dirs.add(dir);
                    dir = "";
                }
            }
            System.out.println(dirs);
            for(String str : dirs){
                if(str.equals("w")) x--;
                else if(str.equals("e")) x++;
                else if(str.equals("ne")){
                    x++;
                    y++;
                }
                else if(str.equals("nw")){
                    x--;
                    y++;
                }
                else if(str.equals("sw")){
                    x--;
                    y--;
                }
                else if(str.equals("se")){
                    x++;
                    y--;
                }
            }
            coords[0] = x;
            coords[1] = y;
            for(Map.Entry<Integer[], Integer> v : map.entrySet()){
                if(v.getKey().equals(coords)){
                    present = true;
                    if(v.getValue() == 1) cur = 0;
                    else cur = 1;
                }
            }
            //if(present) map.replace(coords, cur);
            //else map.put(coords, 1);
        }
        for(Map.Entry<Integer[], Integer> v : map.entrySet()){
            System.out.println(v);
            if(v.getValue().equals(1)){
                ans++;
            }
        }
        System.out.println("Part 1: " + ans);
    }
}
