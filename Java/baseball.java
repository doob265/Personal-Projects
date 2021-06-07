/*Mark Dubin
  8/27/20
  Baseball Simulation*/

  //imports
import java.util.Scanner;
import java.util.Random;

    //baseball class, houses teams and outs
    public class baseball{
        private team home, away;
        private int outs;
        //baseball constructor, taking in team names and constructing teams
        public baseball(String h, String a){
            home = buildTeam(h, true);
            away = buildTeam(a, false);
        }

    //team class, houses roster of team, team name, and team score
    private class team{
        private pitcher ace;
        private batter[] lineup;
        private String name;
        private int score;
        private boolean isHome;

        //team constructor, taking in one pitcher, batter array of 9 batters, team name, and home/away boolean
        public team(pitcher pit, batter[] bats, String name, boolean site){
            this.ace = pit;
            this.lineup = bats;
            this.name = name;
            this.isHome = site;
        }
    }

    //pitcher class, houses pitcher name, handedness, jersey number, and stats
    private class pitcher{
        private String name;
        private char hand;
        private int num, acc, field, stam, velo;

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
    }

    //batter class, houses batter name, handedness, jersey number, and stats
    private class batter{
        private String name;
        private char bhand, fhand;
        private int field, hit, speed, power, eye, pos, num, arm;

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
    }

    //method used to generate random player names
    public String randName(){
        //name banks for first and last names
        String[] fname = {"Joe", "Bob", "Mark", "John", "Jim", "Tom", "Austin", "Brandon", "Max", "Rob", "Joe", "Sam", "Brad", "Chad", "Tim", "David", "Caleb"};
        String[] lname = {"Smith", "Jones", "Miller", "Davis", "Hamilton", "Ford", "Sanchez", "Johnson", "Wallace", "Davis"};

        //stores length of arrays
        int flen = fname.length, llen = lname.length;

        Random rand = new Random();

        //get random first name, copy it into return String
        int r = Math.abs(rand.nextInt() % flen);
        String ret = new String(fname[r]);

        //get random last name, concat it to end of chosen first name
        r = Math.abs(rand.nextInt() % llen);
        ret = ret.concat(" " + lname[r]);

        return ret;

    }

    //generate new batter with randomized parameters, count is defensive position/position in batting lineup
    public batter genBat(int count){
        batter temp = new batter(randName(), randHand(), randHand(), randStats(), randStats(), randStats(), randStats(), randStats(), randStats(), count, randNum());
        return temp;
    }

    //generate new pitcher with randomized parameters
    public pitcher genPit(){
        pitcher temp = new pitcher(randName(), randHand(), randStats(), randStats(), randStats(), randStats(), randStats());
        return temp;
    }

    //return random number, between 1 and 5, to use for a specific player stat
    public int randStats(){
        Random rand = new Random();
        int r = Math.abs((rand.nextInt() * 100) % 5);
        return r + 1;
    }

    //return random number, between 0 and 8, to use for a specific player's position
    public int randPos(){
        Random rand = new Random();
        int r = Math.abs((rand.nextInt() * 100) % 9);
        return r;
    }

    //return random number, between 0 and 99, to use for a specific player's jersey number
    public int randNum(){
        Random rand = new Random();
        int r = Math.abs(rand.nextInt() % 100);
        return r;
    }

    //return either 'L' or 'R' to be used to determine a specific player's handidness
    public char randHand(){
        Random rand = new Random();
        int r = Math.abs(rand.nextInt());
        if(r % 2 == 0){
            return 'L';
        }
        return 'R';
    }

    //used to print out all of pitcher p's information
    public void printPitStats(pitcher p){
        System.out.println(p.name + " throws with his " + p.hand + " hand and wears number " + p.num + ". Accuracy: " + p.acc + " Fielding: " + p.field + " Stamina: " + p.stam + " Velocity: " + p.velo);
    }

    //used to print out all of batter b's information
    public void printBatStats(batter b){
        System.out.println(b.name + " bats with his " + b.bhand + " hand, throws with his " + b.fhand + " hand, wears number " + b.num + ", and plays " + b.pos + ". Speed: " + b.speed + " Fielding: " + b.field + " Eye: " + b.eye + " Hitting: " + b.hit + " Power: " + b.power + " Arm: " + b.arm);
    }

    //used to print out pitcher p's basic information
    public void printPit(pitcher p){
        System.out.println("Their starting pitcher today, wearing number " + p.num + ", throwing from the " + p.hand + " side, give it up for " + p.name + "!");
    }

    //used to print out batter b's basic information
    public void printBat(batter b, int spot){
        System.out.println("Batting " + spot + ", hitting from the " + b.bhand + " side, wearing number " + b.num + ", " + b.name + "!");
    }

    //method used to construct team
    public team buildTeam(String name, boolean site){
        int i;

        //batter array of 9 used to generate entire lineup
        batter[] bats = new batter[9];
        pitcher pit;

        //generate 9 batters with i serving as defensive position/lineup order
        for(i = 2; i < 11; i++){
            bats[i-2] = genBat(i);
        }

        //generate pitcher
        pit = genPit();

        //take batters, pitcher, team name, and site boolean, and generate team
        team temp = new team(pit, bats, name, site);
        return temp;
    }

    //print out all player stats
    public void printStats(){
        System.out.println();
        for(batter b: this.home.lineup){
            printBatStats(b);
        }
        printPitStats(this.home.ace);
        System.out.println();

        for(batter b: this.away.lineup){
            printBatStats(b);
        }
        printPitStats(this.away.ace);
        System.out.println();
    }

    //method used to print out lineup information in digestable way for users
    public void startingLineups(){
        System.out.println();
        int i;
        System.out.println("Now presenting the starting lineup for the home team, the " + this.home.name + "!");
        //print out each member of starting lineup
        for(i = 0; i < 9; i++){
            this.printBat(this.home.lineup[i], i+1);
        }
        this.printPit(this.home.ace);
        System.out.println();

        System.out.println("Now presenting the starting lineup for the away team, the " + this.away.name + "!");
        //print out each member of starting lineup
        for(i = 0; i < 9; i++){
            this.printBat(this.away.lineup[i], i+1);
        }
        this.printPit(this.away.ace);
        System.out.println();
    }

    //ensure team name inputted is valid
    public static boolean strCheck(String word){
        int i;
        //if a single space is entered, return false
        if(word.length() == 1 && word.codePointAt(0) == 32){
            return false;
        }
        //ensures only letters and spaces have been entered
        for(i = 0; i < word.length(); i++){
            if(word.codePointAt(i) != 32 && (word.codePointAt(i) < 96 || word.codePointAt(i) > 123)){
                return false;
            }
        }
        return true;
    }

    //adjusts letters of team names for proper capitalization
    public static String capsFix(String word){
        int i;
        //get char array of string to easily adjust
        char[]w = word.toCharArray();

        //first letter should be uppercase
        char l = word.charAt(0);
        l = Character.toUpperCase(l);
        w[0] = l;

        //go through team name, capitalizing first letter after space
        for(i = 0; i < word.length(); i++){
            if(word.codePointAt(i) == 32){
                l = word.charAt(i+1);
                l = Character.toUpperCase(l);
                w[i+1] = l;
            }
        }

        //turn adjusted char array into string, return
        String ret = new String(w);
        word = ret;
        return word;
    }

    //stealing base method for away team
    public batter[] awaySteal(batter[] bases){
        Random rand = new Random();
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (this.home.lineup[1].arm + this.home.ace.velo) / 2;

        //runner on 1st and 2nd, no runner on 3rd
        if(bases[0] != null && bases[1] != null && bases[2] == null){
            System.out.println("Double steal attempt!");
            if(bases[1].speed > resistance && bases[1].speed > r){
                bases[2] = bases[1];
                bases[1] = bases[0];
                bases[0] = null;
                System.out.println(bases[2].name + " was able to steal third, and " + bases[1].name + " was able to steal second!");
            }
            //thrown out at 3rd
            else{
                name = bases[1].name;
                bases[1] = bases[0];
                bases[0] = null;
                this.outs++;
                if(this.outs >= 3){
                    System.out.println(name + " tried to steal third, but was thrown out by the catcher! Out number " + this.outs + ".");
                }
                else{
                    System.out.println(name + " tried to steal third, but was thrown out by the catcher! Out number " + this.outs + ". " + bases[1].name + " was able to steal second!");
                }
            }
        }
        //runner on 1st, no runner on 2nd
        else if(bases[0] != null && bases[1] == null){
            //base stolen
            if(bases[0].speed > resistance && bases[0].speed > r){
                bases[1] = bases[0];
                bases[0] = null;
                System.out.println(bases[1].name + " was able to steal second!");
            }
            //thrown out
            else{
                name = bases[0].name;
                bases[0] = null;
                this.outs++;
                System.out.println(name + " tried to steal second, but was thrown out by the catcher! Out number " + this.outs + ".");
            }
        }
        //runner on 2nd, no runner on 3rd
        else if(bases[1] != null && bases[2] == null){
            //base stolen
            if(bases[1].speed > resistance && bases[1].speed > r){
                bases[2] = bases[1];
                bases[1] = null;
                System.out.println(bases[2].name + " was able to steal third!");
            }
            //thrown out
            else{
                name = bases[1].name;
                bases[1] = null;
                this.outs++;
                System.out.println(name + " tried to steal third, but was thrown out by the catcher! Out number " + this.outs + ".");
            }
        }
        return bases;
    }

    //stealing base method for away team
    public batter[] homeSteal(batter[] bases){
        Random rand = new Random();
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (this.away.lineup[1].arm + this.away.ace.velo) / 2;

        //runner on 1st and 2nd, no runner on 3rd
        if(bases[0] != null && bases[1] != null && bases[2] == null){
            System.out.println("Double steal attempt!");
            if(bases[1].speed > resistance && bases[1].speed > r){
                bases[2] = bases[1];
                bases[1] = bases[0];
                bases[0] = null;
                System.out.println(bases[2].name + " was able to steal third, and " + bases[1].name + " was able to steal second!");
            }
            //thrown out at 3rd
            else{
                name = bases[1].name;
                bases[1] = bases[0];
                bases[0] = null;
                this.outs++;
                if(this.outs >= 3){
                    System.out.println(name + " tried to steal third, but was thrown out by the catcher! Out number " + this.outs + ".");
                }
                else{
                    System.out.println(name + " tried to steal third, but was thrown out by the catcher! Out number " + this.outs + ". " + bases[1].name + " was able to steal second!");
                }
            }
        }
        //runner on 1st, no runner on 2nd
        else if(bases[0] != null && bases[1] == null){
            //base stolen
            if(bases[0].speed > resistance && bases[0].speed > r){
                bases[1] = bases[0];
                bases[0] = null;
                System.out.println(bases[1].name + " was able to steal second!");
            }
            //thrown out
            else{
                name = bases[0].name;
                bases[0] = null;
                this.outs++;
                System.out.println(name + " tried to steal second, but was thrown out by the catcher! Out number " + this.outs + ".");
            }
        }
        //runner on 2nd, no runner on 3rd
        else if(bases[1] != null && bases[2] == null){
            //base stolen
            if(bases[1].speed > resistance && bases[1].speed > r){
                bases[2] = bases[1];
                bases[1] = null;
                System.out.println(bases[2].name + " was able to steal third!");
            }
            //thrown out
            else{
                name = bases[1].name;
                bases[1] = null;
                this.outs++;
                System.out.println(name + " tried to steal third, but was thrown out by the catcher! Out number " + this.outs + ".");
            }
        }
        return bases;
    }

    //determines outcome of at bat, how many bases (if any) batter will advance
    public int outcome(batter b, pitcher p){
        Random r = new Random();
        int tb = 0, hit = (b.hit * b.eye * b.power * b.speed) / 60, pitch = (p.acc * p.velo) / 25;
        double rand = Math.abs(r.nextDouble() % 5);

        //0 bases gained
        if(pitch >= hit){
            return tb;
        } 
        //at least one base will be gained
        else if(hit > pitch){
            rand = r.nextDouble() % 5;
            return Math.abs(((b.power * b.eye)) - (int)rand) % 5;
        }
        return tb;
    }

    //method for away team hitting a sacrifice fly
    public batter[] awaySacFly(batter[] bases, int to){
        Random rand = new Random();
        int r = Math.abs(rand.nextInt() % 5);
        batter s, t;
        //runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            this.away.score++;
            System.out.println("Batter hit a sac fly. " + t.name + " comes around to score, " + s.name + " advances to third.");
        }
        //runner on 2nd
        else if(bases[1] != null){
            //advances safely
            if(bases[1].speed > this.home.lineup[to].arm || bases[1].speed > r){
                s = bases[1];
                bases[2] = bases[1];
                bases[1] = null;
                System.out.println("Batter hit a sac fly, " + s.name + " advances to third.");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                System.out.println("Batter hit a sac fly, " + s.name + " got doubled off trying to advance to third!");
                this.outs++;
            }
        }
        //runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].speed > this.home.lineup[to].arm || bases[2].speed > r){
                t = bases[2];
                bases[2] = null;
                this.away.score++;
                System.out.println("Batter hit a sac fly, " + t.name + " comes around to score!");
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                System.out.println("Batter hit a sac fly, " + t.name + " got doubled off trying to come home and score!");
                this.outs++;
            }
        }

        return bases;
    }

    public batter[] homeSacFly(batter[] bases, int to, int inning){
        Random rand = new Random();
        int r = Math.abs(rand.nextInt() % 5);
        batter s, t;
        //runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            this.home.score++;
            System.out.println("Batter hit a sac fly. " + t.name + " comes around to score, " + s.name + " advances to third.");
            if(inning >= 9 && this.home.score > this.away.score){
                System.out.println();
                System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                return bases;
            }
        }
        //runner on 2nd
        else if(bases[1] != null){
            //advances safely
            if(bases[1].speed > this.away.lineup[to].arm || bases[1].speed > r){
                s = bases[1];
                bases[2] = bases[1];
                bases[1] = null;
                System.out.println("Batter hit a sac fly, " + s.name + " advances to third.");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                System.out.println("Batter hit a sac fly, " + s.name + " got doubled off trying to advance to third!");
                this.outs++;
            }
        }
        //runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].speed > this.away.lineup[to].arm || bases[2].speed > r){
                t = bases[2];
                bases[2] = null;
                this.home.score++;
                System.out.println("Batter hit a sac fly, " + t.name + " comes around to score!");
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                System.out.println("Batter hit a sac fly, " + t.name + " got doubled off trying to come home and score!");
                this.outs++;
            }
        }

        return bases;
    }

    //method to handle away time recording an out or multiple outs
    public void awayOut(batter[] bases, int to){
        Random r = new Random();
        int roll;
        roll = Math.abs(r.nextInt() % 5);
        //double play, hit to pitcher
        if(bases[0] != null && this.outs < 2 && to == 0 && this.home.ace.field > roll){
            bases[0] = null;
            this.outs+= 2;
            System.out.println("Double play! There are now " + this.outs + " outs.");
        }
        //triple play hit to pitcher
        else if(this.outs == 0 && bases[0] != null && bases[1] != null && to == 0 && this.home.ace.field > roll){
            bases[0] = null;
            this.outs+= 3;
            System.out.println("Triple play! There are now " + this.outs + " outs.");
        }
        //double play, hit to infielder
        else if(bases[0] != null && this.outs < 2 && to < 5 && this.home.lineup[to].field > roll){
            bases[0] = null;
            this.outs+= 2;
            System.out.println("Double play! There are now " + this.outs + " outs.");
        }
        //triple play hit to infielder
        else if(this.outs == 0 && bases[0] != null && bases[1] != null && to < 5 && this.home.lineup[to].field > roll){
            bases[0] = null;
            this.outs+= 3;
            System.out.println("Triple play! There are now " + this.outs + " outs.");
        }
        //sac fly hit to outfielder
        else if(this.outs < 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && this.away.lineup[to].field > roll){
            this.outs++;
            bases = awaySacFly(bases, to);
        }
        //ordinary out
        else{
            this.outs++;
            System.out.println("Out number " + this.outs + ".");
        }
        return;
    }

    //method to handle home time recording an out or multiple outs
    public void homeOut(batter[] bases, int to, int inning){
        Random r = new Random();
        int roll;
        roll = Math.abs(r.nextInt() % 5);
        //double play, hit to pitcher
        if(bases[0] != null && this.outs < 2 && to == 5 && this.away.ace.field > roll){
            bases[0] = null;
            this.outs+= 2;
            System.out.println("Double play! There are now " + this.outs + " outs.");
        }
        //triple play, hit to infielder
        else if(this.outs == 0 && bases[0] != null && bases[1] != null && to == 0 && this.away.ace.field > roll){
            bases[0] = null;
            this.outs+= 3;
            System.out.println("Triple play! There are now " + this.outs + " outs.");
        }
        //double play, hit to infielder
        if(bases[0] != null && this.outs < 2 && to < 5 && this.away.lineup[to].field > roll){
            bases[0] = null;
            this.outs+= 2;
            System.out.println("Double play! There are now " + this.outs + " outs.");
        }
        //triple play, hit to infielder
        else if(this.outs == 0 && bases[0] != null && bases[1] != null && to < 5 && this.away.lineup[to].field > roll){
            bases[0] = null;
            this.outs+= 3;
            System.out.println("Triple play! There are now " + this.outs + " outs.");
        }
        //sac fly, hit to outfielder
        else if(this.outs < 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && this.away.lineup[to].field > roll){
            this.outs++;
            bases = homeSacFly(bases, to, inning);
        }
        //ordinary out
        else{
            this.outs++;
            System.out.println("Out number " + this.outs + ".");
        }
        return;
    }

    //method user to check if an error has occured
    public boolean errorCheck(int field){
        Random r = new Random();
        boolean error = false;
        int roll, i;
        roll = Math.abs(r.nextInt() % 5);
        //roll twice to greatly reduce odds of errors
        for(i = 0; i < 2; i++){
            Math.abs(roll = r.nextInt() % 5);
            //must be true twice for error to occur
            if(field < roll){
                error = true;
            }
            //error will not occur, return false
            else{
                error = false;
                return error;
            }
        }
        return error;
    }

    //method used to handle errors committed by home team
    public batter[] awayError(batter[] bases, batter atBat){
        Random r = new Random();
        int roll;
        roll = Math.abs(r.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            System.out.println("Error! " + atBat.name + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = awaySingle(bases, atBat);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            System.out.println("Error! " + atBat.name + " made it to 2nd base!");
            bases = awayDouble(bases, atBat);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            System.out.println("Error! " + atBat.name + " made it to 3rd base!");
            bases = awayTriple(bases, atBat);
        }
        //four-base error
        else{
            System.out.println("Error! " + atBat.name + " came all the way around to score!");
            bases = awayHR(bases);
        }
        return bases;
    }

    //error used to handle errors committed by away team
    public batter[] homeError(batter[] bases, int inning, batter atBat){
        Random r = new Random();
        int roll;
        roll = Math.abs(r.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            System.out.println("Error! " + atBat.name + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = homeSingle(bases, inning, atBat);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            System.out.println("Error! " + atBat.name + " made it to 2nd base!");
            bases = homeDouble(bases, inning, atBat);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            System.out.println("Error! " + atBat.name + " made it to 3rd base!");
            bases = homeTriple(bases, inning, atBat);
        }
        //four-base error
        else{
            System.out.println("Error! " + atBat.name + " came all the way around to score!");
            bases = homeHR(bases, inning);
        }
        return bases;
    }

    //method to handle away team hitting a single
    public batter[] awaySingle(batter[] bases, batter atBat){
        int i;
        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            //runner on 3rd comes in to score
            if(i == 2 && bases[i] != null){
                bases[i] = null;
                this.away.score++;
                System.out.println("One run has scored!");
            }
            //runner on any other base advances
            else if(bases[i] != null){
                bases[i+1] = bases[i];
                bases[i] = null;
            }
        }
        //put batter on 1st
        bases[0] = atBat;
        return bases;
    }

    //method to handle home team hitting a single
    public batter[] homeSingle(batter[] bases, int inning, batter atBat){
        int i;
        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            //runner on 3rd scores
            if(i == 2 && bases[i] != null){
                bases[i] = null;
                this.home.score++;
                //if bottom 9 or later and home score greater than away, game over
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                System.out.println("One run has scored!");
            }
            //runner on any other bases moves up one base
            else if(bases[i] != null){
                bases[i+1] = bases[i];
                bases[i] = null;
            }
        }
        //put batter on 1st
        bases[0] = atBat;
        return bases;
    }

    //method to handle away team hitting a double
    public batter[] awayDouble(batter[] bases, batter atBat){
        int j, count = 0;
        //deal with each base in reverse
        for(j = 2; j >= 0; j--){
            //runner on 2nd or 3rd come in to score
            if(j >= 1 && bases[j] != null){
                bases[j] = null;
                this.away.score++;
                count++;
            }
            //runner on 1st moves to 3rd
            else if(j == 0 && bases[j] != null){
                bases[j+2] = bases[j];
                bases[j] = null;
            }
        }
        //put batter on 2nd
        bases[1] = atBat;
        //grammar
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        count = 0;
        return bases;
    }

    //method to handle home team hitting a double
    public batter[] homeDouble(batter[] bases, int inning, batter atBat){
        int j, count = 0;
        //deal with each base in reverse order
        for(j = 2; j >= 0; j--){
            //runner on 2nd/3rd score
            if(j >= 1 && bases[j] != null){
                bases[j] = null;
                this.home.score++;
                //walkoff check
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                count++;
            }
            //runner on 1st moves to 3rd
            else if(j == 0 && bases[j] != null){
                bases[j+2] = bases[j];
                bases[j] = atBat;
            }
        }
        //put batter on 2nd
        bases[1] = atBat;
        //grammar check
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    //method to handle away team hitting a triple
    public batter[] awayTriple(batter[] bases, batter atBat){
        int j, count = 0;
        //go through each base, if any has a runner they score
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j] = null;
                this.away.score++;
                count++;
            }
        }
        //move batter to 3rd
        bases[2] = atBat;
        //grammar check
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    //method to handle home team hitting a triple
    public batter[] homeTriple(batter[] bases, int inning, batter atBat){
        int j, count = 0;
        //go through each base, any runner scores
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j] = null;
                this.home.score++;
                //walkoff checker
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                count++;
            }
        }
        //put batter on 3rd
        bases[2] = atBat;
        //grammar check
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    //method to handle away team hitting a HR
    public batter[] awayHR(batter[] bases){
        int j, count = 0;
        //any runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j] = null;
                this.away.score++;
                count++;
            }
        }
        //add one more for the batter
        this.away.score++;
        count++;
        //grammar check
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    //method to handle home team hitting a HR
    public batter[] homeHR(batter[] bases, int inning){
        int j, count = 0;
        //any runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j] = null;
                this.home.score++;
                //walkoff check
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                count++;
            }
        }
        //add one more run for batter
        this.home.score++;
        count++;
        //grammar check
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    //method that handles the gameplay
    public void playBall(){
        int i, j, hspot = 1, rspot = 1, outcome, to, roll;
        batter[] bases = new batter[3];
        Random r = new Random();

        System.out.println("Time to play ball!");

        //outer loop will run 9 times, the amount of innings in a normal game
        for(i = 1; i <= 9; i++){
            //new inning, reset outs and bases
            this.outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }
            System.out.println();
            System.out.println("Top of the " + i + " inning. ");
            //while loop will continue to run until top of inning ends
            while(this.outs < 3){
                //prevents infinite loop in the case of extreme outlier case
                if(this.away.score > 100 || this.home.score > 100){
                    System.out.println("Score exceeded");
                    return;
                }
                System.out.println("Now up to bat, number " + this.away.lineup[rspot].num + ", " + this.away.lineup[rspot].name + "!");
                //stolen base checker to see if runner on base attempts/succeeds in stealing
                roll = Math.abs(r.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].speed >= 3) || (bases[1] != null && bases[1].speed >= 3))){
                    bases = awaySteal(bases);
                }
                //in case picked off runner ended inning
                if(this.outs >= 3){
                    outcome = -1;
                }
                //if not, get outcome 
                else{
                outcome = this.outcome(this.away.lineup[rspot], this.home.ace);
                }
                //out was/should be recorded
                if(outcome == 0){
                    //get position ball was hit to
                    to = Math.abs(r.nextInt() % 9);
                    //error on account of pitcher
                    if(to == 0 && errorCheck(this.home.ace.field)){
                        bases = awayError(bases, this.away.lineup[rspot]);
                    }
                    //error on account of position player
                    else if(errorCheck(this.home.lineup[to].field)){
                        bases = awayError(bases, this.away.lineup[rspot]);
                    }
                    //no error, out(s) recorded
                    else{
                        awayOut(bases, to);
                    }
                }
                //single
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = awaySingle(bases, this.away.lineup[rspot]);
                }
                //double
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = awayDouble(bases, this.away.lineup[rspot]);
                }
                //triple
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = awayTriple(bases, this.away.lineup[rspot]);
                }
                //HR
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = awayHR(bases);
                }
                //advance spot in lineup by 1, wrap back to beginning if at end
                rspot = (rspot + 1) % 9;
            }
            //top of inning over, reset outs and bases
            this.outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }
            System.out.println();
            //game over check, if true return
            if(i == 9 && this.home.score > this.away.score){
                System.out.println();
                System.out.println("Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
                return;
            }
            System.out.println("Bottom of the " + i + " inning. ");
            //will run until bottom of inning ends or walkoff
            while(this.outs < 3){
                //prevents infinite loop in rare outlier case
                if(this.home.score > 100 || this.away.score > 100){
                    System.out.println("Score exceeded");
                    return;
                }
                System.out.println("Now up to bat, number " + this.home.lineup[hspot].num + ", " + this.home.lineup[hspot].name + "!");
                //stolen base check
                roll = Math.abs(r.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].speed >= 3) || (bases[1] != null && bases[1].speed >= 3))){
                    bases = homeSteal(bases);
                }
                //if picked off runner ended inning
                if(this.outs >= 3){
                    outcome = -1;
                }
                //if not, get outcome of at bat
                else{
                    outcome = this.outcome(this.home.lineup[hspot], this.away.ace);
                }
                //out/error
                if(outcome == 0){
                    //get position ball was hit to
                    to = Math.abs(r.nextInt() % 9);
                    //error by pitcher
                    if(to == 0 && errorCheck(this.away.ace.field)){
                        bases = homeError(bases, i, this.home.lineup[hspot]);
                    }
                    //error by fielder
                    else if(errorCheck(this.away.lineup[to].field)){
                        bases = homeError(bases, i, this.home.lineup[hspot]);
                    }
                    //out(s)
                    else{
                        homeOut(bases, to, i);
                    }
                    //walkoff check
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                //single
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = homeSingle(bases, i, this.home.lineup[hspot]);
                    //walkoff check
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                //double
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = homeDouble(bases, i, this.home.lineup[hspot]);
                    //walkoff check
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                //triple
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = homeTriple(bases, i, this.home.lineup[hspot]);
                    //walkoff check
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                //HR
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = homeHR(bases, i);
                    //walkoff check
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                //advance home lineup by 1 spot, if at the end loop back around
                hspot = (hspot + 1) % 9;
            }
            System.out.println("Inning " + i + " is over. The score is now " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
        }

        //if game still tied after 9 innings, go to extra innings
        if(this.home.score == this.away.score){
            this.extras();
        }
        System.out.println();
        System.out.println("Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
    }

    //if game goes to extra innings, this method will take over for gameplay
    public void extras(){
        int i, j, hspot = 1, rspot = 1, outcome, to;
        batter[] bases = new batter[3];
        Random r = new Random();
        int roll;

        System.out.println("Extra innings!");
        //i keeps track of what inning it is
        i = 10;
        //gameplay will continue until someone wins or extremely long scenario
        while(this.away.score == this.home.score){
            //prevent infinite loop in rare scenarios
            if(i >= 50 || this.away.score > 100 || this.home.score > 100){
                System.out.println("Time exceeded");
                return;
            }
            //new inning, reset outs and bases
            this.outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }
            System.out.println();
            System.out.println("Top of the " + i + " inning. ");
            //will run until top of inning ends
            while(this.outs < 3){
                //prevent infinite loop in rare scenario
                if(this.away.score > 100 || this.home.score > 100){
                    System.out.println("Score exceeded");
                    return;
                }
                System.out.println("Now up to bat, number " + this.away.lineup[rspot].num + ", " + this.away.lineup[rspot].name + "!");
                //stolen base check
                roll = Math.abs(r.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].speed >= 3) || (bases[1] != null && bases[1].speed >= 3))){
                    bases = awaySteal(bases);
                }
                //if inning should end due to picked off runner being out 3
                if(this.outs >= 3){
                    outcome = -1;
                }
                //if not, run at bat
                else{
                    outcome = this.outcome(this.away.lineup[rspot], this.home.ace);
                }
                //out/error
                if(outcome == 0){
                    //get position ball was hit to
                    to = Math.abs(r.nextInt() % 9);
                    //error by pitcher
                    if(to == 0 && errorCheck(this.home.ace.field)){
                        bases = awayError(bases, this.away.lineup[rspot]);
                    }
                    //error by fielder
                    else if(errorCheck(this.home.lineup[to].field)){
                        bases = awayError(bases, this.away.lineup[rspot]);
                    }
                    //out(s)
                    else{
                        awayOut(bases, to);
                    }
                }
                //single
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = awaySingle(bases, this.away.lineup[rspot]);
                }
                //double
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = awayDouble(bases, this.away.lineup[rspot]);
                }
                //triple
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = awayTriple(bases, this.away.lineup[rspot]);
                }
                //HR
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = awayHR(bases);
                }
                //advance road lineup by 1 spot, wrap around if at end
                rspot = (rspot + 1) % 9;
            }
            //top of inning over, reset bases and outs
            this.outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }
            System.out.println();
            System.out.println("Bottom of the " + i + " inning. ");
            //prevent infinite loop
            while(this.outs < 3){
                if(this.away.score > 100 || this.home.score > 100){
                    System.out.println("Score exceeded");
                    return;
                }
                System.out.println("Now up to bat, number " + this.home.lineup[hspot].num + ", " + this.home.lineup[hspot].name + "!");
                //stolen base check
                roll = Math.abs(r.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].speed >= 3) || (bases[1] != null && bases[1].speed >= 3))){
                    bases = homeSteal(bases);
                }
                //if picked off runner ends inning
                if(this.outs >= 3){
                    outcome = -1;
                }
                //if not, get outcome of at bat
                else{
                    outcome = this.outcome(this.home.lineup[hspot], this.away.ace);
                }
                //out/error
                if(outcome == 0){
                    //get position ball was hit to
                    to = Math.abs(r.nextInt() % 9);
                    //error by pitcher
                    if(to == 0 && errorCheck(this.away.ace.field)){
                        bases = homeError(bases, i, this.home.lineup[hspot]);
                    }
                    //error by fielder
                    if(errorCheck(this.away.lineup[to].field)){
                        bases = homeError(bases, i, this.home.lineup[hspot]);
                    }
                    //out
                    else{
                        homeOut(bases, to, i);
                    }
                    //walkoff check
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                //single
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = homeSingle(bases, i, this.home.lineup[hspot]);
                    //walkoff check
                    if(this.home.score > this.away.score){
                        return;
                    }
                }
                //double
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = homeDouble(bases, i, this.home.lineup[hspot]);
                    //walkoff check
                    if(this.home.score > this.away.score){
                        return;
                    }
                }
                //triple
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = homeTriple(bases, i, this.home.lineup[hspot]);
                    //walkoff check
                    if(this.home.score > this.away.score){
                        return;
                    }
                }
                //HR
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = homeHR(bases, i);
                    //walkoff check
                    if(this.home.score > this.away.score){
                        return;
                    }
                }
                //advance home lineup by one spot, wrap around if at end
                hspot = (hspot + 1) % 9;
            }
            System.out.println("Inning " + i + " is over. The score is now " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
            i++;
        }
        return;
    }

    //main method
    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        String home, away;

        //scan in home team name
        System.out.println("Please enter a team name for the home team, only entering alphabetic characters a-z.");
        home = scan.nextLine();
        home = home.toLowerCase();

        //continually scan until appropriately formatted name is entered
        while(!strCheck(home)){
            System.out.println("Sorry, please only enter alphabetical letters a-z.");
            home = scan.nextLine();
            home = home.toLowerCase();
        }
        //readjust caps
        home = capsFix(home);

        //scan in away team name
        System.out.println("Now please enter a team name for the away team, only entering alphabetic characters a-z.");
        away = scan.nextLine();
        away = away.toLowerCase();

        //continually scan until appropriately formatted name is entered
        while(!strCheck(away)){
            System.out.println("Sorry, please only enter alphabetical letters a-z.");
            away = scan.nextLine();
            away = away.toLowerCase();
        }
        //readjust caps
        away = capsFix(away);

        //create new baseball instance passing in team names
        baseball b = new baseball(home, away);

        //print out detailed stats of each player
        //b.printStats();

        //print out basic starting lineups for each team
        b.startingLineups();

        //simulate the game
        b.playBall();
    }
}
