//Mark Dubin
//10/12/2020
//AoC 2020 Day 12: Rain Risk

import java.util.*;
import java.io.*;

public class ferry {
    //ints for ferry constructor
    int longitude, latitude, direction, wayLong, wayLat;

    //ferry constructor sets default values
    public ferry(){
        this.direction = 0;
        this.longitude = 0;
        this.latitude = 0;
        this.wayLong = 10;
        this.wayLat = 1;
    }

    //part one forward function moves ship forward 
    public void forwardOne(int num){
        //switch on current direction, moves ship appropriately
        switch(this.direction){
            //north
            case(90):
            this.latitude += num;
            //System.out.println("Ship moved North " + num + " latitude is " + this.latitude);
            return;
            //south
            case(270):
            this.latitude -= num;
            //System.out.println("Ship moved South " + num + " latitude is " + this.latitude);
            return;
            //east
            case(0):
            this.longitude += num;
            //System.out.println("Ship moved East " + num + " longitude is " + this.longitude);
            return;
            //west
            case(180):
            this.longitude -= num;
            //System.out.println("Ship moved West " + num + " longitude is " + this.longitude);
            return;
            default:
            return;
        }
    }

    //part one turn function turns ship
    public void turn(int num, String let){
        //right turn
        if(let.equals("R")){
            //System.out.print("turn: cur direction " + this.direction + " ");
            this.direction -= num;
            //move back into proper range
            if(this.direction < 0) this.direction += 360;
            //System.out.println("Ship turned right " + num + " degrees, direction is " + this.direction);
        }
        //left turn
        else if(let.equals("L")){
            //System.out.print("turn: cur direction " + this.direction + " ");
            this.direction += num;
            //move back into proper range
            if(this.direction >= 360) this.direction -= 360;
            //System.out.println("Ship turned left " + num + " degrees, direction is " + this.direction);
        }
    }

    //part two forward method
    public void forwardTwo(int num){
        int i, x = this.wayLong - this.longitude, y = this.wayLat - this.latitude;
        //System.out.print(" S (" + this.longitude + "," + this.latitude + ")");
        //System.out.print(" W (" + this.wayLong + "," + this.wayLat + ")");
        //System.out.print(" Moving ship long " + x + " lat " + y + " " + num + " times");
        //advance ship towards waypoint num times
        for(i = 0; i < num; i++){
            this.latitude += y;
            this.longitude += x;
        }
        //move waypoint appropriate distance away from ship
        this.wayLat = this.latitude + y;
        this.wayLong = this.longitude + x;

        //System.out.print(" S (" + this.longitude + "," + this.latitude + ")");
        //System.out.println(" W (" + this.wayLong + "," + this.wayLat + ")");
    }

    //part two rotate method on L and R
    public void rotate(int num, String let){
        //System.out.println("Ship coordinates: (" + this.longitude + ", " + this.latitude + ")");
        //System.out.print("W (" + this.wayLong + "," + this.wayLat + ")" + " S (" + this.longitude + "," + this.latitude + ")" + " " + let + " ");
        int s = 0, c = 0, x = 0, y = 0;
        //sin and cosine values for 4 possible turn values
        if(num == 0){
            s = 0;
            c = 1;
        }
        else if(num == 90){
            c = 0;
            s = 1;
        }
        else if(num == 180){
            s = 0;
            c = -1;
        }
        else if(num == 270){
            c = 0;
            s = -1;
        }
        /*(double x = 0, y = 0, s, c;
        s = Math.sin((double) num);
        c = Math.cos((double) num);*/
        //System.out.print("num " + num + " s " + s + " c " + c);

        //move waypoint to "origin"
        this.wayLat -= this.latitude;
        this.wayLong -= this.longitude;

        //System.out.print(" W (" + this.wayLong + "," + thiswayLat + ") ");

        //clockwise turn
        if(let.equals("R")){
            x = (this.wayLong * c) + (this.wayLat * s);
            y = (-this.wayLong * s) + (this.wayLat * c);
        }
        //counter-clockwise turn
        else if(let.equals("L")){
            x = (this.wayLong * c) - (this.wayLat * s);
            y = (this.wayLong * s) + (this.wayLat * c);
        }
        //System.out.print(" x " + x + " y " + y);

        //move away from "origin"
        this.wayLat = y + this.latitude;
        this.wayLong =  x + this.longitude;

        //System.out.println("W (" + this.wayLong + "," + this.wayLat + ")");
    }
    public static void main(String[] args) throws IOException{
        //create ferry object, other variables
        ferry fOne = new ferry();
        ferry fTwo = new ferry();
        String temp, letter, n;
        int num;
        //new scanner with specific file name
        Scanner s = new Scanner(new File("dayTwelveInput.txt"));
        //go through entire file
        while(s.hasNextLine()){
            //get values from line
            temp = s.nextLine();
            letter = temp.substring(0, 1);
            n = temp.substring(1);
            num = Integer.parseInt(n);
            //part one
            //switch on letter moves ship appropriately
            switch(letter){
                case("N"):
                fOne.latitude += num;
                //System.out.println("Ship moved North " + num + " latitude is " + f.latitude);
                break;
                case("S"):
                fOne.latitude -= num;
                //System.out.println("Ship moved South " + num + " latitude is " + f.latitude);
                break;
                case("E"):
                fOne.longitude += num;
                //System.out.println("Ship moved East " + num + " longitude is " + f.longitude);
                break;
                case("W"):
                fOne.longitude -= num;
                //System.out.println("Ship moved West " + num + " longitude is " + f.longitude);
                break;
                //moves ship forward with function call
                case("F"):
                //System.out.print("Forward:");
                fOne.forwardOne(num);
                break;
                //turns right with function call
                case("R"):
                fOne.turn(num, letter);
                break;
                //turns left with function call
                case("L"):
                fOne.turn(num, letter);
                break;
                default:
                break;
            }
            //part two
            //switch will move waypoint appropriately for NSEW
            switch(letter){
                case("N"):
                fTwo.wayLat += num;
                //System.out.println("Waypoint moved North " + num + " latitude is " + f.wayLat);
                break;
                case("S"):
                fTwo.wayLat -= num;
                //System.out.println("Waypoint moved South " + num + " latitude is " + f.wayLat);
                break;
                case("E"):
                fTwo.wayLong += num;
                //System.out.println("Waypoint moved East " + num + " longitude is " + f.wayLong);
                break;
                case("W"):
                fTwo.wayLong -= num;
                //System.out.println("Waypoint moved West " + num + " longitude is " + f.wayLong);
                break;
                //moves ship forward with function call, also adjusts waypoint
                case("F"):
                //System.out.print("Forward:");
                fTwo.forwardTwo(num);
                break;
                //R and L will rotate waypoint appropriately in function
                case("R"):
                //System.out.print("Rot ");
                fTwo.rotate(num, letter);
                break;
                case("L"):
                //System.out.print("Rot ");
                fTwo.rotate(num, letter);
                break;
                default:
                break;
            }
        }
        //System.out.println(f.latitude);
        //System.out.println(f.longitude);
        //print out Manhattan Distance from start
        System.out.println(Math.abs(fOne.latitude)+Math.abs(fOne.longitude));
        System.out.println(Math.abs(fTwo.latitude)+Math.abs(fTwo.longitude));
    }
}
