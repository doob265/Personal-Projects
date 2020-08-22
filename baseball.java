/*Mark Dubin
  8/22/20
  Baseball Simulation*/

import java.util.Scanner;
import java.util.Random;

public class baseball{
    private team home, away;
    public baseball(String h, String a){
        home = buildTeam(h, true);
        away = buildTeam(a, false);
    }

    private class team{
        private pitcher ace;
        private batter[] lineup;
        private String name;
        private boolean isHome;
        private int score;

        public team(pitcher pit, batter[] bats, String name, boolean site){
            this.ace = pit;
            this.lineup = bats;
            this.name = name;
            this.isHome = site;
        }
    }

    private class pitcher{
        private String name;
        private char hand;
        private int num, acc, field, stam, velo;

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

    private class batter{
        private String name;
        private char bhand, fhand;
        private int field, hit, speed, power, eye, pos, num, arm;

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

    public String randName(){
        String[] fname = {"Joe", "Bob", "Mark", "John", "Jim", "Tom", "Austin", "Brandon", "Max", "Rob", "Joe", "Sam", "Brad", "Chad", "Tim", "David"};
        String[] lname = {"Smith", "Jones", "Miller", "Davis", "Hamilton", "Ford", "Sanchez", "Johnson", "Wallace", "Davis"};
        int flen = fname.length, llen = lname.length;
        Random rand = new Random();

        int r = Math.abs(rand.nextInt() % flen);
        String ret = new String(fname[r]);

        r = Math.abs(rand.nextInt() % llen);
        ret = ret.concat(" " + lname[r]);

        return ret;

    }

    public batter genBat(int count){
        batter temp = new batter(randName(), randHand(), randHand(), randStats(), randStats(), randStats(), randStats(), randStats(), randStats(), count, randNum());
        return temp;
    }

    public pitcher genPit(){
        pitcher temp = new pitcher(randName(), randHand(), randStats(), randStats(), randStats(), randStats(), randStats());
        return temp;
    }

    public int randStats(){
        Random rand = new Random();
        int r = Math.abs((rand.nextInt() * 100) % 5);
        return r + 1;
    }

    public int randPos(){
        Random rand = new Random();
        int r = Math.abs((rand.nextInt() * 100) % 9);
        return r;
    }

    public int randNum(){
        Random rand = new Random();
        int r = Math.abs(rand.nextInt() % 100);
        return r;
    }

    public char randHand(){
        Random rand = new Random();
        int r = Math.abs(rand.nextInt() * 100);
        if(r % 2 == 0){
            return 'L';
        }
        return 'R';
    }

    public void printPitStats(pitcher p){
        System.out.println(p.name + " throws with his " + p.hand + " hand and wears number " + p.num + ". Accuracy: " + p.acc + " Fielding: " + p.field + " Stamina: " + p.stam + " Velocity: " + p.velo);
    }

    public void printBatStats(batter b){
        System.out.println(b.name + " bats with his " + b.bhand + " hand, throws with his " + b.fhand + " hand, wears number " + b.num + ", and plays " + b.pos + ". Speed: " + b.speed + " Fielding: " + b.field + " Eye: " + b.eye + " Hitting: " + b.hit + " Power: " + b.power + " Arm: " + b.arm);
    }

    public void printPit(pitcher p){
        System.out.println("Their starting pitcher today, wearing number " + p.num + ", throwing from the " + p.hand + " side, give it up for " + p.name + "!");
    }

    public void printBat(batter b, int spot){
        System.out.println("Batting " + spot + ", hitting from the " + b.bhand + " side, wearing number " + b.num + ", " + b.name + "!");
    }

    public team buildTeam(String name, boolean site){
        int i;
        batter[] bats = new batter[9];
        pitcher pit;
        Scanner scan = new Scanner(System.in);
        for(i = 2; i < 11; i++){
            bats[i-2] = genBat(i);
        }

        pit = genPit();

        team temp = new team(pit, bats, name, site);
        scan.close();
        return temp;
    }

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

    public void startingLineups(){
        System.out.println();
        int i;
        System.out.println("Now presenting the starting lineup for the home team, the " + this.home.name + "!");
        for(i = 0; i < 9; i++){
            this.printBat(this.home.lineup[i], i+1);
        }
        this.printPit(this.home.ace);
        System.out.println();

        System.out.println("Now presenting the starting lineup for the away team, the " + this.away.name + "!");
        for(i = 0; i < 9; i++){
            this.printBat(this.away.lineup[i], i+1);
        }
        this.printPit(this.away.ace);
        System.out.println();
    }

    public static boolean strCheck(String word){
        int i;
        for(i = 0; i < word.length(); i++){
            if(word.codePointAt(i) != 32 && (word.codePointAt(i) < 96 || word.codePointAt(i) > 123)){
                return false;
            }
        }
        return true;
    }

    public static String capsFix(String word){
        int i;
        char[]w = word.toCharArray();
        char l = word.charAt(0);
        l = Character.toUpperCase(l);
        w[0] = l;
        for(i = 0; i < word.length(); i++){
            if(word.codePointAt(i) == 32){
                l = word.charAt(i+1);
                l = Character.toUpperCase(l);
                w[i+1] = l;
            }
        }
        String ret = new String(w);
        word = ret;
        return word;
    }

    public int outcome(batter b, pitcher p){
        Random r = new Random();
        int tb = 0, hit = (b.hit * b.eye * b.power * b.speed) / 60, pitch = (p.acc * p.velo) / 25;
        double rand = Math.abs(r.nextDouble() % 5);

        if(pitch >= hit){
            return tb;
        } if(hit > pitch){
            rand = r.nextDouble() % 5;
            return Math.abs(((b.power * b.eye)) - (int)rand) % 5;
        }
        return tb;
    }

    public int[] awaySacFly(int[] bases){
        if(bases[1] == 1 && bases[2] == 1){
            bases[1] = 0;
            this.away.score++;
            System.out.println("Sac fly. Runner on third comes around to score, runner on 2nd advances to third.");
        }
        else if(bases[1] == 1){
            bases[1] = 0;
            bases[2] = 1;
            System.out.println("Sac fly, runner on 2nd advances to third.");
        }
        else if(bases[2] == 1){
            bases[2] = 0;
            this.away.score++;
            System.out.println("Sac fly, runner on 3nd comes around to score!");
        }

        return bases;
    }

    public int[] homeSacFly(int[] bases){
        if(bases[1] == 1 && bases[2] == 1){
            bases[1] = 0;
            this.home.score++;
            System.out.println("Sac fly. Runner on third comes around to score, runner on 2nd advances to third.");
        }
        else if(bases[1] == 1){
            bases[1] = 0;
            bases[2] = 1;
            System.out.println("Sac fly, runner on 2nd advances to third.");
        }
        else if(bases[2] == 1){
            bases[2] = 0;
            this.home.score++;
            System.out.println("Sac fly, runner on 3nd comes around to score!");
        }

        return bases;
    }

    public int awayOut(int[]bases, int outs){
        Random r = new Random();
        int to, roll;
        to = Math.abs(r.nextInt() % 9);
        roll = Math.abs(r.nextInt() % 5);
        if(bases[0] == 1 && outs < 2 && to < 5 && this.home.lineup[to].field > roll){
            bases[0] = 0;
            outs+= 2;
            System.out.println("Double play! There are now " + outs + " outs.");
        }
        else if(outs == 0 && bases[0] == 1 && bases[1] == 1 && to < 5 && this.home.lineup[to].field > roll){
            bases[0] = 0;
            outs+= 2;
            System.out.println("Triple play! There are now " + outs + " outs.");
        }
        else if(outs < 2 && to > 4 && to < 8 && (bases[1] == 1 || bases[2] == 1)){
            outs++;
            bases = awaySacFly(bases);
        }
        else{
            outs++;
            System.out.println("Out number " + outs + ".");
        }
        return outs;
    }

    public int homeOut(int[]bases, int outs){
        Random r = new Random();
        int to, roll;
        to = Math.abs(r.nextInt() % 9);
        roll = Math.abs(r.nextInt() % 5);
        if(bases[0] == 1 && outs < 2 && to < 5 && this.away.lineup[to].field > roll){
            bases[0] = 0;
            outs+= 2;
            System.out.println("Double play! There are now " + outs + " outs.");
        }
        else if(outs == 0 && bases[0] == 1 && bases[1] == 1 && to < 5 && this.away.lineup[to].field > roll){
            bases[0] = 0;
            outs+= 2;
            System.out.println("Triple play! There are now " + outs + " outs.");
        }
        else if(outs < 2 && to > 4 && to < 8 && (bases[1] == 1 || bases[2] == 1)){
            outs++;
            bases = homeSacFly(bases);
        }
        else{
            outs++;
            System.out.println("Out number " + outs + ".");
        }
        return outs;
    }

    public boolean errorCheck(int field){
        Random r = new Random();
        boolean error = false;
        int roll, i;
        roll = Math.abs(r.nextInt() % 5);
        for(i = 0; i < 2; i++){
            Math.abs(roll = r.nextInt() % 5);
            if(field < roll){
                error = true;
            }
            else{
                error = false;
                return error;
            }
        }
        return error;
    }

    public int[] awayError(int[]bases){
        Random r = new Random();
        int roll;
        roll = Math.abs(r.nextInt() % 100);
        if(roll <= 70){
            System.out.println("Error! Batter made it to 1st base.");
            bases = awaySingle(bases);
        }
        else if(roll >= 71 && roll <= 90){
            System.out.println("Error! Batter made it to 2nd base.");
            bases = awayDouble(bases);
        }
        else if(roll >= 91 && roll <= 97){
            System.out.println("Error! Batter made it to 3rd base.");
            bases = awayTriple(bases);
        }
        else{
            System.out.println("Error! Batter came all the way around to score!");
            bases = awayHR(bases);
        }
        return bases;
    }

    public int[] homeError(int[]bases, int inning){
        Random r = new Random();
        int roll;
        roll = Math.abs(r.nextInt() % 100);
        if(roll <= 70){
            System.out.println("Error! Batter made it to 1st base.");
            bases = homeSingle(bases, inning);
        }
        else if(roll >= 71 && roll <= 90){
            System.out.println("Error! Batter made it to 2nd base.");
            bases = homeDouble(bases, inning);
        }
        else if(roll >= 91 && roll <= 97){
            System.out.println("Error! Batter made it to 3rd base.");
            bases = homeTriple(bases, inning);
        }
        else{
            System.out.println("Error! Batter came all the way around to score!");
            bases = homeHR(bases, inning);
        }
        return bases;
    }

    public int[] awaySingle(int[]bases){
        int i;
        for(i = 2; i >= 0; i--){
            if(i == 2 && bases[i] == 1){
                bases[i] = 0;
                this.away.score++;
                System.out.println("One run has scored!");
            }
            else if(bases[i] == 1){
                bases[i+1] = 1;
                bases[i] = 0;
            }
        }
        bases[0] = 1;
        return bases;
    }

    public int[] homeSingle(int[]bases, int inning){
        int i;
        for(i = 2; i >= 0; i--){
            if(i == 2 && bases[i] == 1){
                bases[i] = 0;
                this.home.score++;
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                System.out.println("One run has scored!");
            }
            else if(bases[i] == 1){
                bases[i+1] = 1;
                bases[i] = 0;
            }
        }
        bases[0] = 1;
        return bases;
    }

    public int[] awayDouble(int[]bases){
        int j, count = 0;
        for(j = 2; j >= 0; j--){
            if(j >= 1 && bases[j] == 1){
                bases[j] = 0;
                this.away.score++;
                count++;
            }
            else if(j == 0 && bases[j] == 1){
                bases[j+2] = 1;
                bases[j] = 0;
            }
        }
        bases[1] = 1;
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        count = 0;
        return bases;
    }

    public int[] homeDouble(int[]bases, int inning){
        int j, count = 0;
        for(j = 2; j >= 0; j--){
            if(j >= 1 && bases[j] == 1){
                bases[j] = 0;
                this.home.score++;
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                count++;
            }
            else if(j == 0 && bases[j] == 1){
                bases[j+2] = 1;
                bases[j] = 0;
            }
        }
        bases[1] = 1;
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    public int[] awayTriple(int[] bases){
        int j, count = 0;
        for(j = 2; j >= 0; j--){
            if(bases[j] == 1){
                bases[j] = 0;
                this.away.score++;
                count++;
            }
        }
        bases[2] = 1;
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    public int[] homeTriple(int[] bases, int inning){
        int j, count = 0;
        for(j = 2; j >= 0; j--){
            if(bases[j] == 1){
                bases[j] = 0;
                this.home.score++;
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                count++;
            }
        }
        bases[2] = 1;
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    public int[] awayHR(int[] bases){
        int j, count = 0;
        for(j = 0; j < 3; j++){
            if(bases[j] == 1){
                bases[j] = 0;
                this.away.score++;
                count++;
            }
        }
        this.away.score++;
        count++;
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    public int[] homeHR(int[] bases, int inning){
        int j, count = 0;
        for(j = 0; j < 3; j++){
            if(bases[j] == 1){
                bases[j] = 0;
                this.home.score++;
                if(inning >= 9 && this.home.score > this.away.score){
                    System.out.println();
                    System.out.println("Walkoff! Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + "!");
                    return bases;
                }
                count++;
            }
        }
        this.home.score++;
        count++;
        if(count > 1){
            System.out.println(count + " runs have scored!");
        }
        else if(count > 0){
            System.out.println(count + " run has scored!");
        }
        return bases;
    }

    public void playBall(){
        int i, j, outs, hspot = 1, rspot = 1, outcome, to;
        int[]bases = new int[5];
        Random r = new Random();

        System.out.println("Time to play ball!");

        for(i = 1; i <= 9; i++){
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = 0;
            }
            System.out.println();
            System.out.println("Top of the " + i + " inning. ");
            while(outs < 3){
                if(this.away.score > 100 || this.home.score > 100){
                    System.out.println("Score exceeded");
                    return;
                }
                System.out.println("Now up to bat, number " + this.away.lineup[rspot].num + ", " + this.away.lineup[rspot].name + "!");
                outcome = this.outcome(this.away.lineup[rspot], this.home.ace);
                if(outcome == 0){
                    to = Math.abs(r.nextInt() % 9);
                    if(errorCheck(this.away.lineup[to].field)){
                        bases = awayError(bases);
                    }
                    else{
                        outs = awayOut(bases, outs);
                    }
                }
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = awaySingle(bases);
                }
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = awayDouble(bases);
                }
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = awayTriple(bases);
                }
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = awayHR(bases);
                }
                rspot = (rspot + 1) % 9;
            }
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = 0;
            }
            System.out.println();
            if(i == 9 && this.home.score > this.away.score){
                System.out.println();
                System.out.println("Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
                return;
            }
            System.out.println("Bottom of the " + i + " inning. ");
            while(outs < 3){
                if(this.home.score > 100 || this.away.score > 100){
                    System.out.println("Score exceeded");
                    return;
                }
                System.out.println("Now up to bat, number " + this.home.lineup[hspot].num + ", " + this.home.lineup[hspot].name + "!");
                outcome = this.outcome(this.home.lineup[hspot], this.away.ace);
                if(outcome == 0){
                    to = Math.abs(r.nextInt() % 9);
                    if(errorCheck(this.home.lineup[to].field)){
                        bases = homeError(bases, i);
                    }
                    else{
                        outs = homeOut(bases, outs);
                    }
                }
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = homeSingle(bases, i);
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = homeDouble(bases, i);
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = homeTriple(bases, i);
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = homeHR(bases, i);
                    if(i == 9 && this.home.score > this.away.score){
                        return;
                    }
                }

                hspot = (hspot + 1) % 9;
            }
            System.out.println("Inning " + i + " is over. The score is now " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
        }

        if(this.home.score == this.away.score){
            bases = this.extras();
        }
        System.out.println();
        System.out.println("Game over! The final score is " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
    }

    public int[] extras(){
        int i, j, outs, hspot = 1, rspot = 1, outcome, to;
        int[]bases = new int[5];
        Random r = new Random();

        System.out.println("Extra innings!");
        i = 10;
        while(this.away.score == this.home.score){
            if(i >= 50 || this.away.score > 100 || this.home.score > 100){
                System.out.println("Time exceeded");
                return bases;
            }
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = 0;
            }
            System.out.println();
            System.out.println("Top of the " + i + " inning. ");
            while(outs < 3){
                if(this.away.score > 100 || this.home.score > 100){
                    System.out.println("Score exceeded");
                    return bases;
                }
                System.out.println("Now up to bat, number " + this.away.lineup[rspot].num + ", " + this.away.lineup[rspot].name + "!");
                outcome = this.outcome(this.away.lineup[rspot], this.home.ace);
                if(outcome == 0){
                    to = Math.abs(r.nextInt() % 9);
                    if(errorCheck(this.away.lineup[to].field)){
                        bases = awayError(bases);
                    }
                    else{
                        outs = awayOut(bases, outs);
                    }
                }
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = awaySingle(bases);
                }
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = awayDouble(bases);
                }
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = awayTriple(bases);
                }
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = awayHR(bases);
                }
                rspot = (rspot + 1) % 9;
            }
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = 0;
            }
            System.out.println();
            System.out.println("Bottom of the " + i + " inning. ");
            while(outs < 3){
                if(this.away.score > 100 || this.home.score > 100){
                    System.out.println("Score exceeded");
                    return bases;
                }
                System.out.println("Now up to bat, number " + this.home.lineup[hspot].num + ", " + this.home.lineup[hspot].name + "!");
                outcome = this.outcome(this.home.lineup[hspot], this.away.ace);
                if(outcome == 0){
                    to = Math.abs(r.nextInt() % 9);
                    if(errorCheck(this.home.lineup[to].field)){
                        bases = homeError(bases, i);
                    }
                    else{
                        outs = homeOut(bases, outs);
                    }
                }
                else if(outcome == 1){
                    System.out.println("Single!");
                    bases = homeSingle(bases, i);
                    if(this.home.score > this.away.score){
                        return bases;
                    }
                }
                else if(outcome == 2){
                    System.out.println("Double!");
                    bases = homeDouble(bases, i);
                    if(this.home.score > this.away.score){
                        return bases;
                    }
                }
                else if(outcome == 3){
                    System.out.println("Triple!");
                    bases = homeTriple(bases, i);
                    if(this.home.score > this.away.score){
                        return bases;
                    }
                }
                else if(outcome == 4){
                    System.out.println("HR!");
                    bases = homeHR(bases, i);
                    if(this.home.score > this.away.score){
                        return bases;
                    }
                }

                hspot = (hspot + 1) % 9;
            }
            System.out.println("Inning " + i + " is over. The score is now " + this.away.name + " " + this.away.score + ", " + this.home.name + " " + this.home.score + ".");
            i++;
        }
        return bases;
    }


    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        String home, away;

        System.out.println("Please enter a team name for the home team, only entering alphabetic characters a-z.");
        home = scan.nextLine();
        home = home.toLowerCase();

        while(!strCheck(home)){
            System.out.println("Sorry, please only enter alphabetical letters a-z.");
            home = scan.nextLine();
            home = home.toLowerCase();
        }
        home = capsFix(home);

        System.out.println("Now please enter a team name for the away team, only entering alphabetic characters a-z.");
        away = scan.nextLine();
        away = away.toLowerCase();

        while(!strCheck(away)){
            System.out.println("Sorry, please only enter alphabetical letters a-z.");
            away = scan.nextLine();
            away = away.toLowerCase();
        }
        away = capsFix(away);

        baseball b = new baseball(home, away);

        //b.printStats();
        b.startingLineups();

        b.playBall();

    }
}
