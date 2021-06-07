import java.io.*;
import java.util.*;

public class dayNineteen{
    public static void main(String[] args) throws IOException{
        String temp, num, left, right, rules;
        int n, index, l, r;
        ArrayList<Integer> arr;
        char let;
        Scanner s = new Scanner(new File("dayNineteenInput.txt"));
        Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        while(s.hasNextLine()){
            temp = s.nextLine();
            num = " ";
            arr = new ArrayList<Integer>();
            //System.out.println(temp);
            //base a or b rule
            if(temp.indexOf(":") != -1){
                num = temp.substring(0, temp.indexOf(":"));
                n = Integer.parseInt(num);
                //rule contains a or b
                if(temp.indexOf("\"") != -1){
                    let = temp.charAt(temp.indexOf("\"")+1);
                    if(let == 'a'){
                        arr.add(-1);
                        System.out.println(n + " " + arr);
                        map.put(n, arr);
                    }
                    else if(let == 'b'){
                        arr.add(-2);
                        System.out.println(n + " " + arr);
                        map.put(n, arr);
                    }
                }
                //or rule
                else if(temp.indexOf("|") != -1){
                    //left side
                    num = "";
                    index = temp.indexOf(":") + 2;
                    left = temp.substring(index, temp.indexOf("|"));
                    //System.out.println("left: " + left);
                    index = 0;
                    while(!left.substring(index, index + 1).isBlank()){ 
                        //System.out.print("while" + " ");
                        System.out.print(left.substring(index, index + 1) + " ");
                        num = num.concat(left.substring(index, index + 1));
                        index++;
                        //System.out.println(num);
                    }
                    //System.out.println("num: " + num);
                    l = Integer.parseInt(num);
                    //System.out.println(l);
                    arr.add(l);
                    //2 vals
                    if(index + 1 < left.length() && left.charAt(index + 1) != ' '){
                        num = "";
                        index++;
                        while(left.charAt(index) != ' '){
                            num = num.concat(left.substring(index, index + 1));
                            index++;
                        }
                        r = Integer.parseInt(num);
                        System.out.println(r);
                        arr.add(r);
                    }
                    System.out.println(n + " " + arr);
                    //map.put(n, arr);
                    //right side
                    //arr = new ArrayList<Integer>();
                    num = "";
                    index = temp.indexOf("|") + 2;
                    right = temp.substring(index);
                    System.out.println(index + " " + right + " " + right.length());
                    index = 0;
                    while(index < right.length() && right.charAt(index) != ' '){
                        num = num.concat(right.substring(index, index + 1));
                        index++;
                    }
                    l = Integer.parseInt(num);
                    System.out.println(l);
                    arr.add(l);
                    //2 vals
                    if(index < right.length() && right.charAt(index + 1) != ' '){
                        num = "";
                        index++;
                        while(index < right.length() && right.charAt(index) != ' '){
                            num = num.concat(right.substring(index, index + 1));
                            index++;
                        }
                        r = Integer.parseInt(num);
                        System.out.println(r);
                        arr.add(r);
                    }
                    System.out.println(n + " " + arr);
                    map.put(n, arr);
                }
                //no or rule
                else{
                    num = "";
                    index = temp.indexOf(":") + 2;
                    left = temp.substring(index);
                    System.out.println("left: " + left);
                    index = 0; 
                    while(index < left.length() && left.charAt(index) != ' '){
                        num = num.concat(left.substring(index, index + 1));
                        index++;
                    }
                    l = Integer.parseInt(num);
                    System.out.println(l);
                    arr.add(l);
                    //2 vals
                    if(index < left.length() && left.charAt(index + 1) != ' '){
                        num = "";
                        index++;
                        while(index < left.length() && left.charAt(index) != ' '){
                            num = num.concat(left.substring(index, index + 1));
                            index++;
                        }
                        r = Integer.parseInt(num);
                        System.out.println(r);
                        arr.add(r);
                    }
                    System.out.println(n + " " + arr);
                    map.put(n, arr);
                }
            }
            else{
                
            }
        }
        for(Map.Entry<Integer, ArrayList<Integer>> v : map.entrySet()){
            System.out.println(v);
            //System.out.println(v.getKey() + " " + v.getValue());
        }
    }
}