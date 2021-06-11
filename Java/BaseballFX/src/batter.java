import java.util.Random;

public class batter implements player {
    private String name;
    private char bhand, fhand;
    private int field, hit, speed, power, eye, pos, num, arm, hits = 0, abs = 0;

    private static Random rand = new Random();

    //batter constructor, taking in name, handedness, jersy number, and stats
    public batter(String name, char bhand, char fhand, int field, int hit, int speed, int pop, int eye, int arm, int pos, int num){
        this.name = name;
        this.bhand = bhand;
        this.fhand = fhand;
        this.field = field;
        this.hit = hit;
        this.speed = speed;
        this.power = pop;
        this.eye = eye;
        this.pos = pos;
        this.num = num;
        this.arm = arm;
    }

    public String getName(){
        return name;
    }

    public char getBHand(){
        return bhand;
    }

    public char getFHand(){
        return fhand;
    }

    public int getField(){
        return field;
    }

    public int getHit(){
        return hit;
    }

    public int getSpeed(){
        return speed;
    }

    public int getPower(){
        return power;
    }

    public int getEye(){
        return eye;
    }

    public int getPos(){
        return pos;
    }

    public int getNum(){
        return num;
    }

    public int getArm(){
        return arm;
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

    //generate new batter with randomized parameters, count is defensive position/position in batting lineup
    public static batter genBat(int count){
        return new batter(randName(), randHand(), randHand(), randStats(), randStats(), randStats(), randStats(), randStats(), randStats(), count, randNum());
    }

    public int getHits(){
        return hits;
    }

    public void incHits(){
        hits++;
    }

    public int getAbs(){
        return abs;
    }

    public void incAbs(){
        abs++;
    }
}
