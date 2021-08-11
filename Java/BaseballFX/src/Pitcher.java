/*Mark Dubin
  6/12/21
  BaseballFX - Pitcher Class*/

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Pitcher implements Player{
    private String name;
    private char hand;
    private int num, acc, field, stam, velo, ks = 0, walks = 0;
    private boolean W = false;

    private static Random rand = new Random();

    //Pitcher constructor, taking in name, handedness, jersy number, and stats
    public Pitcher(String name, char hand, int field, int stam, int acc, int velo, int num){
        this.name = name;
        this.hand = hand;
        this.num = num;
        this.acc = acc;
        this.field = field;
        this.stam = stam;
        this.velo = velo;
    }

    public String getName(){
        return name;
    }

    public char getHand(){
        return hand;
    }

    public int getNum(){
        return num;
    }

    public int getField(){
        return field;
    }

    public int getAcc(){
        return acc;
    }

    public int getStam(){
        return stam;
    }

    public int getVelo(){
        return velo;
    }

    public int getWalks(){
        return walks;
    }

    public void incWalks(){
        walks++;
    }

    public int getStrikeOuts(){
        return ks;
    }

    public void incStrikeOuts(){
        ks++;
    }

    public boolean getResult(){
        return W;
    }

    public void isWinner(){
        W = true;
    }

    //return random number, between 1 and 5, to use for a specific player stat
    public static int randStats(){
        int r = Math.abs((rand.nextInt() * 100) % 5);
        return r + 1;
    }

    //return random number, between 0 and 8, to use for a specific player's position
    public static int randPos(){
        return Math.abs((rand.nextInt() * 100) % 9);
    }

    //return random number, between 0 and 99, to use for a specific player's jersey number
    public static int randNum(){
        return Math.abs(rand.nextInt() % 100);
    }

    //return either 'L' or 'R' to be used to determine a specific player's handidness
    public static char randHand(){
        int r = Math.abs(rand.nextInt());
        if(r % 100 <= 25){
            return 'L';
        }
        return 'R';
    }

    //method used to generate random player names
    public static String randName(){
        //name banks for first and last names
        ArrayList<String> fNames = App.getFirstNames();
        ArrayList<String> lNames = App.getLastNames();

        //get random first name, copy it into return String
        int r = Math.abs(rand.nextInt() % fNames.size());
        String ret = new String(fNames.get(r));

        //get random last name, concat it to end of chosen first name
        r = Math.abs(rand.nextInt() % lNames.size());
        ret = ret.concat(" " + lNames.get(r));

        return ret;
    }

    //generate new Pitcher with randomized parameters
    public static Pitcher genPit(){
        return new Pitcher(randName(), randHand(), randStats(), randStats(), randStats(), randStats(), randStats());
    }
}
