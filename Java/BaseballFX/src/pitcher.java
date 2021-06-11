import java.util.Random;

public class pitcher implements player{
    private String name;
    private char hand;
    private int num, acc, field, stam, velo;

    private static Random rand = new Random();

    //pitcher constructor, taking in name, handedness, jersy number, and stats
    public pitcher(String name, char hand, int field, int stam, int acc, int velo, int num){
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
        if(r % 100 <= 10){
            return 'L';
        }
        return 'R';
    }

    //method used to generate random player names
    public static String randName(){
        //name banks for first and last names
        String[] fname = {"Joe", "Bob", "Mark", "John", "Jim", "Tom", "Austin", "Brandon", "Max", "Rob", "Joe", "Sam", "Brad", "Chad", "Tim", "David", "Caleb"};
        String[] lname = {"Smith", "Jones", "Miller", "Davis", "Hamilton", "Ford", "Sanchez", "Johnson", "Wallace", "Davis"};

        //stores length of arrays
        int flen = fname.length, llen = lname.length;

        //get random first name, copy it into return String
        int r = Math.abs(rand.nextInt() % flen);
        String ret = new String(fname[r]);

        //get random last name, concat it to end of chosen first name
        r = Math.abs(rand.nextInt() % llen);
        ret = ret.concat(" " + lname[r]);

        return ret;
    }

    //generate new pitcher with randomized parameters
    public static pitcher genPit(){
        return new pitcher(randName(), randHand(), randStats(), randStats(), randStats(), randStats(), randStats());
    }
}
