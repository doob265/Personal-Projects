import java.util.*;
import java.io.*;
import java.math.*;

public class dayThirteen {
    public static void main(String[] args) throws IOException{
        Scanner s = new Scanner(new File("dayThirteenInput.txt"));
        //time we need to get there
        String t = s.nextLine();
        //line containing bus #'s, x's, and commas
        String line = s.nextLine(), temp;
        //System.out.println(time + " " + line);
        int low = 0, high = line.indexOf(",");
        boolean right = true;
        //part one
        //index = 0, cur, min = 9999999, diff = 0, ans = 0, time = Integer.parseInt(t);
        //int[] buses = new int[9];
        //go through list, getting content between each comma, put ints into list
       /*while(high != -1){
            temp = line.substring(low, high);
            //System.out.println(temp);
            if(!temp.equals("x")){
                buses[index] = Integer.parseInt(temp);
                index++;
            }
            low = high+1;
            high = line.indexOf(",", low);
            //System.out.println(low + " " + high);
            //gets last item on row if it is a num
            if(high == -1){
                temp = line.substring(low);
                //System.out.println(temp);
                if(!temp.equals("x")){
                    buses[index] = Integer.parseInt(temp);
                    index++;
                }
            }
        }
        //for each bus, calculate time. if less than current min time, save dataa
        for(int bus : buses){
            cur = bus;
            while(cur <= time) cur += bus;
            System.out.println(cur);
            if((cur - time) > 0 && cur < min){
                min = cur;
                ans = bus;
                diff = cur - time;
            }
        }
        //System.out.println(min + " " + diff + " " + ans + " " + time);
        //print requested output, bus # times mins you have to wait
        System.out.println(ans * diff);*/

        //part two
        ArrayList<BigInteger> buses = new ArrayList<BigInteger>();
        while(high != -1){
            temp = line.substring(low, high);
            //System.out.println(temp);
            if(!temp.equals("x")) buses.add(new BigInteger(temp));
            else buses.add(new BigInteger("0"));
            low = high+1;
            high = line.indexOf(",", low);
            //System.out.println(low + " " + high);
            //gets last item on row if it is a num
            if(high == -1){
                temp = line.substring(low);
                //System.out.println(temp);
                if(!temp.equals("x")) buses.add(new BigInteger(temp));
                else buses.add(new BigInteger("0"));
            }
        }
        BigInteger min, time, cur, index, rem;
        min = buses.remove(0);
        //System.out.print(min);
        time = new BigInteger("100000000000000");
        rem = time.mod(min);
        time = time.subtract(rem);
        //System.out.println(time);
        //System.out.println(time.divide(min));
        while(true){
            index = new BigInteger("1");
            time = time.add(min);
            right = true;
            //System.out.println("time " + time);
            for(BigInteger bus : buses){
                //System.out.println(bus);
                if(right == true && bus.equals(new BigInteger("0"))) index = index.add(new BigInteger("1"));
                else if(right == true){
                    cur = new BigInteger("100000000000000");
                    rem = cur.mod(bus);
                    cur = cur.subtract(rem);
                    while(cur.compareTo(time) <= 0) cur = cur.add(bus);
                    if(index.compareTo(new BigInteger("17")) == 1) System.out.println("cur " + cur + " index " + index);
                    if(!cur.equals(time.add(index))) right = false;
                    index = index.add(new BigInteger("1"));
                }
            }
            if(right){
                System.out.println(time);
                return;
            }
        }
    }
}
