/*Mark Dubin
  8/23/20
  Baseball Simulation*/

  //imports
import java.util.concurrent.TimeUnit;

import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.Random;
import javafx.application.Platform;

    //baseball class, houses teams and outs
    public class baseball{
        public Random rand = new Random();
        private boolean isTop;
        private team home, away;
        private int outs, inning;
        private TextArea outpuTextArea, scoreArea, awayTextArea, homeTextArea;
        private ImageView lhb, rhb, firstRunner, secondRunner, thirdRunner, awayOnDeck, homeOnDeck;
        //baseball constructor, taking in team names and constructing teams
        public baseball(String h, String a, TextArea outputArea, TextArea sArea, TextArea awayArea, TextArea homeArea, ImageView lb, ImageView rb, ImageView f, ImageView s, ImageView t, ImageView awayNext, ImageView homeNext){
            home = buildTeam(h, true);
            away = buildTeam(a, false);
            outpuTextArea = outputArea;
            scoreArea = sArea;
            lhb = lb;
            rhb = rb;
            firstRunner = f;
            secondRunner = s;
            thirdRunner = t;
            awayOnDeck = awayNext;
            homeOnDeck = homeNext;
            awayTextArea = awayArea;
            homeTextArea = homeArea;
        }

    public void setInning(int i){
        inning = i;
    }

    public void setTop(boolean top){
        isTop = top;
    }

    public team getHome(){
        return home;
    }

    public team getAway(){
        return away;
    }

    //method used to construct team
    public team buildTeam(String name, boolean site){
        int i;

        //batter array of 9 used to generate entire lineup
        batter[] bats = new batter[9];
        pitcher pit;

        //generate 9 batters with i serving as defensive position/lineup order
        for(i = 2; i < 11; i++){
            bats[i-2] = batter.genBat(i);
        }

        //generate pitcher
        pit = pitcher.genPit();

        //take batters, pitcher, team name, and site boolean, and generate team
        team temp = new team(pit, bats, name, site);
        return temp;
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
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (home.getLineup()[1].getArm() + home.getAce().getVelo()) / 2;

        //Runner on 1st and 2nd, no Runner on 3rd
        if(bases[0] != null && bases[1] != null && bases[2] == null){
            appendArea("\n" +"Double steal attempt!");
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = bases[0];
                bases[0] = null;
                appendArea("\n" +bases[2].getName() + " was able to steal third, and " + bases[1].getName() + " was able to steal second!");
            }
            //thrown out at 3rd
            else{
                name = bases[1].getName();
                bases[1] = bases[0];
                bases[0] = null;
                outs++;
                if(outs >= 3){
                    appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ".");
                }
                else{
                    appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ". " + bases[1].getName() + " was able to steal second!");
                }
            }
        }
        //Runner on 1st, no Runner on 2nd
        else if(bases[0] != null && bases[1] == null){
            //base stolen
            if(bases[0].getSpeed() > resistance && bases[0].getSpeed() > r){
                bases[1] = bases[0];
                bases[0] = null;
                appendArea("\n" +bases[1].getName() + " was able to steal second!");
            }
            //thrown out
            else{
                name = bases[0].getName();
                bases[0] = null;
                outs++;
                appendArea("\n" +name + " tried to steal second, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }
        //Runner on 2nd, no Runner on 3rd
        else if(bases[1] != null && bases[2] == null){
            //base stolen
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = null;
                appendArea("\n" +bases[2].getName() + " was able to steal third!");
            }
            //thrown out
            else{
                name = bases[1].getName();
                bases[1] = null;
                outs++;
                appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }

        adjustRunners(bases);

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        
        return bases;
    }

    //stealing base method for away team
    public batter[] homeSteal(batter[] bases){
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (away.getLineup()[1].getArm() + away.getAce().getVelo()) / 2;

        //Runner on 1st and 2nd, no Runner on 3rd
        if(bases[0] != null && bases[1] != null && bases[2] == null){
            appendArea("\n" +"Double steal attempt!");
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = bases[0];
                bases[0] = null;
                appendArea("\n" +bases[2].getName() + " was able to steal third, and " + bases[1].getName() + " was able to steal second!");
            }
            //thrown out at 3rd
            else{
                name = bases[1].getName();
                bases[1] = bases[0];
                bases[0] = null;
                outs++;
                if(outs >= 3){
                    appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + away.getLineup()[1].getName() + "! Out number " + outs + ".");
                }
                else{
                    appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ". " + bases[1].getName() + " was able to steal second!");
                }
            }
        }
        //Runner on 1st, no Runner on 2nd
        else if(bases[0] != null && bases[1] == null){
            //base stolen
            if(bases[0].getSpeed() > resistance && bases[0].getSpeed() > r){
                bases[1] = bases[0];
                bases[0] = null;
                appendArea("\n" +bases[1].getName() + " was able to steal second!");
            }
            //thrown out
            else{
                name = bases[0].getName();
                bases[0] = null;
                outs++;
                appendArea("\n" +name + " tried to steal second, but was thrown out by the catcher, " + away.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }
        //Runner on 2nd, no Runner on 3rd
        else if(bases[1] != null && bases[2] == null){
            //base stolen
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = null;
                appendArea("\n" +bases[2].getName() + " was able to steal third!");
            }
            //thrown out
            else{
                name = bases[1].getName();
                bases[1] = null;
                outs++;
                appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + away.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }

        adjustRunners(bases);

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return bases;
    }

    //determines outcome of at bat, how many bases (if any) batter will advance
    public int outcome(batter b, pitcher p){
        int random, tb = 0, hit = (b.getHit() * b.getEye() * b.getPower() * b.getSpeed()) / 60, pitch = (p.getAcc() * p.getVelo()) / 25;

        //0 bases gained
        if(pitch >= hit){
            return tb;
        } 
        //at least one base will be gained
        else if(hit > pitch){
            random = rand.nextInt() % 100;
            return Math.abs(((b.getEye() * b.getPower())) - random) % 100;
        }
        return tb;
    }

    //method for away team hitting a sacrifice fly
    public batter[] awaySacFly(batter[] bases, int to){
        int r = Math.abs(rand.nextInt() % 5);
        batter s, t;
        //Runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            away.incScore(1);
            updateScoreArea();
            appendArea("\n" +"Batter hit a sac fly. " + t.getName() + " comes around to score, " + s.getName() + " advances to third. Out number " + outs + ".");
        }
        //Runner on 2nd
        else if(bases[1] != null){
            //advances safely
            if(bases[1].getSpeed() > home.getLineup()[to].getArm() || bases[1].getSpeed() > r){
                s = bases[1];
                bases[2] = bases[1];
                bases[1] = null;
                appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " advances to third. Out number " + outs + ".");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                outs++;
                appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " got doubled off trying to advance to third! Out number " + outs + ".");
            }
        }
        //Runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].getSpeed() > home.getLineup()[to].getArm() || bases[2].getSpeed() > r){
                t = bases[2];
                bases[2] = null;
                away.incScore(1);
                updateScoreArea();
                appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " comes around to score! Out number " + outs + ".");
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                outs++;
                appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " got doubled off trying to come home and score! Out number " + outs + ".");
            }
        }

        hideBatters();
        adjustRunners(bases);
        return bases;
    }

    public batter[] homeSacFly(batter[] bases, int to, int inning){
        int r = Math.abs(rand.nextInt() % 5);
        batter s, t;
        //Runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            home.incScore(1);
            updateScoreArea();
            appendArea("\n" +"Batter hit a sac fly. " + t.getName() + " comes around to score, " + s.getName() + " advances to third. Out number " + outs + ".");
            if(inning >= 9 && home.getScore() > away.getScore()){
                appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                return bases;
            }
        }
        //Runner on 2nd
        else if(bases[1] != null){
            //advances safely
            if(bases[1].getSpeed() > away.getLineup()[to].getArm() || bases[1].getSpeed() > r){
                s = bases[1];
                bases[2] = bases[1];
                bases[1] = null;
                appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " advances to third. Out number " + outs + ".");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                outs++;
                appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " got doubled off trying to advance to third! Out number " + outs + ".");
            }
        }
        //Runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].getSpeed() > away.getLineup()[to].getArm() || bases[2].getSpeed() > r){
                t = bases[2];
                bases[2] = null;
                home.incScore(1);
                updateScoreArea();
                appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " comes around to score! Out number " + outs + ".");
                if(inning >= 9 && home.getScore() > away.getScore()){
                    appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                    return bases;
                }
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                outs++;
                appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " got doubled off trying to come home and score! Out number " + outs + ".");
            }
        }

        hideBatters();
        adjustRunners(bases);
        return bases;
    }

    //method to handle away time recording an out or multiple outs
    public void awayOut(batter[] bases, int to){
        int roll;
        roll = Math.abs(rand.nextInt() % 5);
        //double play, hit to pitcher
        if(bases[0] != null && outs < 2 && to == 0 && home.getAce().getField() > roll){
            bases[0] = null;
            outs+= 2;
            appendArea("\n" +"Double play! There are now " + outs + " outs.");
        }
        //triple play hit to pitcher
        else if(outs == 0 && bases[0] != null && bases[1] != null && to == 0 && home.getAce().getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            appendArea("\n" + "Triple play! There are now " + outs + " outs.");
        }
        //double play, hit to infielder
        else if(bases[0] != null && outs < 2 && to < 5 && home.getLineup()[to].getField() > roll){
            bases[0] = null;
            outs+= 2;
            appendArea("\n" + "Double play! There are now " + outs + " outs.");
        }
        //triple play hit to infielder
        else if(outs == 0 && bases[0] != null && bases[1] != null && to < 5 && home.getLineup()[to].getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            appendArea("\n" + "Triple play! There are now " + outs + " outs.");
        }
        //sac fly hit to outfielder
        else if(outs < 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && away.getLineup()[to].getField() > roll){
            outs++;
            bases = awaySacFly(bases, to);
        }
        //ordinary out
        else{
            outs++;
            appendArea("\n" + "Out number " + outs + ".");
        }

        hideBatters();
        adjustRunners(bases);
        return;
    }

    //method to handle home time recording an out or multiple outs
    public void homeOut(batter[] bases, int to, int inning){
        int roll;
        roll = Math.abs(rand.nextInt() % 5);
        //double play, hit to pitcher
        if(bases[0] != null && outs < 2 && to == 0 && away.getAce().getField() > roll){
            bases[0] = null;
            outs+= 2;
            appendArea("\n" + "Double play! There are now " + outs + " outs.");
        }
        //triple play, hit to infielder
        else if(outs == 0 && bases[0] != null && bases[1] != null && to == 0 && away.getAce().getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            appendArea("\n" + "Triple play! There are now " + outs + " outs.");
        }
        //double play, hit to infielder
        if(bases[0] != null && outs < 2 && to < 5 && away.getLineup()[to].getField() > roll){
            bases[0] = null;
            outs+= 2;
            appendArea("\n" + "Double play! There are now " + outs + " outs.");
        }
        //triple play, hit to infielder
        else if(outs == 0 && bases[0] != null && bases[1] != null && to < 5 && away.getLineup()[to].getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            appendArea("\n" + "Triple play! There are now " + outs + " outs.");
        }
        //sac fly, hit to outfielder
        else if(outs < 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && away.getLineup()[to].getField() > roll){
            outs++;
            bases = homeSacFly(bases, to, inning);
        }
        //ordinary out
        else{
            outs++;
            appendArea("\n" + "Out number " + outs + ".");
        }

        hideBatters();
        adjustRunners(bases);
        return;
    }

    //method user to check if an error has occured
    public boolean errorCheck(int field){
        boolean error = false;
        int roll, i;
        roll = Math.abs(rand.nextInt() % 5);
        //roll twice to greatly reduce odds of errors
        for(i = 0; i < 2; i++){
            Math.abs(roll = rand.nextInt() % 5);
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
    public batter[] awayError(batter[] bases, batter atBat, int to){
        int roll;
        roll = Math.abs(rand.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            appendArea("\n" +"Error! " + atBat.getName() + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = awaySingle(bases, atBat, to);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            appendArea("\n" +"Error! " + atBat.getName() + " made it to 2nd base!");
            bases = awayDouble(bases, atBat, to);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            appendArea("\n" +"Error! " + atBat.getName() + " made it to 3rd base!");
            bases = awayTriple(bases, atBat);
        }
        //four-base error
        else{
            appendArea("\n" +"Error! " + atBat.getName() + " came all the way around to score!");
            bases = awayHR(bases, atBat);
        }

        adjustRunners(bases);
        return bases;
    }

    //error used to handle errors committed by away team
    public batter[] homeError(batter[] bases, int inning, batter atBat, int to){
        int roll;
        roll = Math.abs(rand.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            appendArea("\n" +"Error! " + atBat.getName() + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = homeSingle(bases, inning, atBat, to);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            appendArea("\n" +"Error! " + atBat.getName() + " made it to 2nd base!");
            bases = homeDouble(bases, inning, atBat, to);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            appendArea("\n" +"Error! " + atBat.getName() + " made it to 3rd base!");
            bases = homeTriple(bases, inning, atBat);
        }
        //four-base error
        else{
            appendArea("\n" +"Error! " + atBat.getName() + " came all the way around to score!");
            bases = homeHR(bases, inning, atBat);
        }

        adjustRunners(bases);
        return bases;
    }

    //method to handle away team hitting a single
    public batter[] awaySingle(batter[] bases, batter atBat, int to){
        int i, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            r = rand.nextInt() % 5;
            //Runner on 3rd comes in to score
            if(i == 2 && bases[i] != null){
                appendArea("\n" +bases[i].getName() + " has scored from third!");
                bases[i] = null;
                away.incScore(1);
                updateScoreArea();
            }
            //Runner on 2nd will try to score
            else if(bases[i] != null && r > 2 && i == 1 && bases[i].getSpeed() > 2 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(home.getLineup()[to].getArm() > r && home.getLineup()[to].getArm() > bases[i].getSpeed()){
                    outs++;
                    appendArea("\n" +bases[i].getName() + " was thrown out trying to come in to score from second! Out number " + outs);
                    bases[i] = null;
                }
                //scores
                else{
                    away.incScore(1);
                    updateScoreArea();
                    appendArea("\n" +bases[i].getName() + " has scored from second!");
                    bases[i] = null;
                }
            }
            //Runner on any other base advances
            else if(bases[i] != null){
                bases[i+1] = bases[i];
                bases[i] = null;
            }
        }
        //put batter on 1st
        bases[0] = atBat;
        adjustRunners(bases);
        atBat.incHits();
        hideBatters();
        return bases;
    }

    //method to handle home team hitting a single
    public batter[] homeSingle(batter[] bases, int inning, batter atBat, int to){
        int i, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            r = rand.nextInt() % 5;
            //Runner on 3rd scores
            if(i == 2 && bases[i] != null){
                appendArea("\n" +bases[i].getName() + " has scored from third!");
                bases[i] = null;
                home.incScore(1);
                updateScoreArea();
            }
            //Runner on 2nd will try to score
            else if(bases[i] != null && r > 2 && i == 1 && bases[i].getSpeed() > 2 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(away.getLineup()[to].getArm() > r && away.getLineup()[to].getArm() > bases[i].getSpeed()){
                    outs++;
                    appendArea("\n" +bases[i].getName() + " was thrown out trying to come in to score from second! Out number " + outs);
                    bases[i] = null;
                }
                //scores
                else{
                    home.incScore(1);
                    updateScoreArea();
                    appendArea("\n" +bases[i].getName() + " has scored from second!");
                    bases[i] = null;
                }
            }
            //Runner on any other bases moves up one base
            else if(bases[i] != null){
                bases[i+1] = bases[i];
                bases[i] = null;
            }
            //if bottom 9 or later and home score greater than away, game over
            if(inning >= 9 && home.getScore() > away.getScore()){
                appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                return bases;
            }
        }
        //put batter on 1st
        bases[0] = atBat;
        adjustRunners(bases);
        atBat.incHits();
        hideBatters();
        return bases;
    }

    //method to handle away team hitting a double
    public batter[] awayDouble(batter[] bases, batter atBat, int to){
        int j, count = 0, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse
        for(j = 2; j >= 0; j--){
            r = rand.nextInt() % 5;
            //Runner on 2nd or 3rd come in to score
            if(j >= 1 && bases[j] != null){
                bases[j] = null;
                away.incScore(1);
                updateScoreArea();
                count++;
            }
            //Runner on 1st will try to score
            else if(bases[j] != null && r > 2 && j == 0 && bases[j].getSpeed() > 3 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(home.getLineup()[to].getArm() > r && home.getLineup()[to].getArm() > bases[j].getSpeed()){
                    outs++;
                    appendArea("\n" +bases[j].getName() + " was thrown out trying to come in to score from first! Out number " + outs);
                    bases[j] = null;
                }
                //scores
                else{
                    away.incScore(1);
                    updateScoreArea();
                    count++;
                    bases[j] = null;
                }
            }
            //Runner on 1st moves to 3rd
            else if(j == 0 && bases[j] != null){
                bases[j+2] = bases[j];
                bases[j] = null;
            }
        }
        //put batter on 2nd
        bases[1] = atBat;
        adjustRunners(bases);
        //grammar
        if(count > 1){
            appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            appendArea("\n" +count + " run has scored!");
        }
        count = 0;
        atBat.incHits();
        hideBatters();
        return bases;
    }

    //method to handle home team hitting a double
    public batter[] homeDouble(batter[] bases, int inning, batter atBat, int to){
        int j, count = 0, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse order
        for(j = 2; j >= 0; j--){
            r = rand.nextInt() % 5;
            //Runner on 2nd/3rd score
            if(j >= 1 && bases[j] != null){
                bases[j] = null;
                home.incScore(1);
                updateScoreArea();
                count++;
            }
            //Runner on 1st will try to score
            else if(bases[j] != null && r > 2 && j == 0 && bases[j].getSpeed() > 3 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(away.getLineup()[to].getArm() > r && away.getLineup()[to].getArm() > bases[j].getSpeed()){
                    outs++;
                    appendArea("\n" +bases[j].getName() + " was thrown out trying to come in to score from first! Out number " + outs);
                    bases[j] = null;
                }
                //scores
                else{
                    home.incScore(1);
                    updateScoreArea();
                    count++;
                    bases[j] = null;
                }
            }
            //Runner on 1st moves to 3rd
            else if(j == 0 && bases[j] != null){
                bases[j+2] = bases[j];
                bases[j] = atBat;
            }
            //walkoff check
            if(inning >= 9 && home.getScore() > away.getScore()){
                appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                return bases;
            }
        }
        //put batter on 2nd
        bases[1] = atBat;
        adjustRunners(bases);

        //grammar check
        if(count > 1){
            appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            appendArea("\n" +count + " run has scored!");
        }

        hideBatters();
        atBat.incHits();
        return bases;
    }

    //method to handle away team hitting a triple
    public batter[] awayTriple(batter[] bases, batter atBat){
        int j, count = 0;
        //go through each base, if any has a Runner they score
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j] = null;
                away.incScore(1);
                updateScoreArea();
                count++;
                clearPlayers();
            }
        }
        //move batter to 3rd
        bases[2] = atBat;

        //grammar check
        if(count > 1){
            appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            appendArea("\n" +count + " run has scored!");
        }

        adjustRunners(bases);
        hideBatters();
        atBat.incHits();
        return bases;
    }

    //method to handle home team hitting a triple
    public batter[] homeTriple(batter[] bases, int inning, batter atBat){
        int j, count = 0;
        //go through each base, any Runner scores
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j] = null;
                home.incScore(1);
                updateScoreArea();
                clearPlayers();
                //walkoff checker
                if(inning >= 9 && home.getScore() > away.getScore()){
                    appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                    return bases;
                }
                count++;
            }
        }
        //put batter on 3rd
        bases[2] = atBat;
        adjustRunners(bases);

        //grammar check
        if(count > 1){
            appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            appendArea("\n" +count + " run has scored!");
        }

        hideBatters();
        atBat.incHits();
        return bases;
    }

    //method to handle away team hitting a HR
    public batter[] awayHR(batter[] bases, batter atBat){
        int j, count = 0;
        //any Runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j] = null;
                away.incScore(1);
                updateScoreArea();
                count++;
                clearPlayers();
            }
        }
        //add one more for the batter
        away.incScore(1);
        updateScoreArea();
        count++;
        //grammar check
        if(count > 1){
            appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            appendArea("\n" +count + " run has scored!");
        }

        hideBatters();
        atBat.incHits();
        return bases;
    }

    //method to handle home team hitting a HR
    public batter[] homeHR(batter[] bases, int inning, batter atBat){
        int j, count = 0;
        //any Runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j] = null;
                home.incScore(1);
                updateScoreArea();
                clearPlayers();
                //walkoff check
                if(inning >= 9 && home.getScore() > away.getScore()){
                    appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                    return bases;
                }
                count++;
            }
        }
        //add one more run for batter
        home.incScore(1);
        updateScoreArea();
        count++;
        //grammar check
        if(count > 1){
            appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            appendArea("\n" +count + " run has scored!");
        }

        hideBatters();
        atBat.incHits();
        return bases;
    }

    public TextArea getTextArea(){
        return outpuTextArea;
    }

    public TextArea getScoreArea(){
        return scoreArea;
    }

    public void appendArea(String msg){
        if (Platform.isFxApplicationThread()) {
            getTextArea().appendText(msg);
        } else {
            Platform.runLater(() -> getTextArea().appendText(msg));
        }
    }

    public void updateScoreArea(){
        if (Platform.isFxApplicationThread()) {
            if(isTop){
                getScoreArea().setText("Inning:\tTop " + inning + "\tOuts:\t" + outs + "\nScore:\t" + away.getTeamName() + ": " + away.getScore() + ", " + home.getTeamName() + ": " + home.getScore());
            } else{
                getScoreArea().setText("Inning:\tBottom " + inning + "\tOuts:\t" + outs + "\nScore:\t"+ away.getTeamName() + ": " + away.getScore() + ", " + home.getTeamName() + ": " + home.getScore()); 
            }
        } else {
            if(isTop){
                Platform.runLater(() -> getScoreArea().setText("Inning:\tTop " + inning + "\tOuts:\t" + outs + "\nScore:\t" + away.getTeamName() + ": " + away.getScore() + ", " + home.getTeamName() + ": " + home.getScore()));
            } else{
                Platform.runLater(() -> getScoreArea().setText("Inning:\tBottom " + inning + "\tOuts:\t" + outs + "\nScore:\t" + away.getTeamName() + ": " + away.getScore() + ", " + home.getTeamName() + ": " + home.getScore()));
            }
        }
    }

    public void updateAwayArea(){
        if(Platform.isFxApplicationThread()){
            awayTextArea.setText(away.getTeamName() + " Lineup: ");
            for(batter bat : away.getLineup()){
                awayTextArea.appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            awayTextArea.appendText("\n" + "P: " + away.getAce().getName());
        } else{
            Platform.runLater(() -> awayTextArea.setText(away.getTeamName() + " Lineup: "));
            for(batter bat : away.getLineup()){
                Platform.runLater(() -> awayTextArea.appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                awayTextArea.appendText("\n" + "P: " + away.getAce().getName());
            });
        }
    }

    public void updateHomeArea(){
        if(Platform.isFxApplicationThread()){
            homeTextArea.setText(home.getTeamName() + " Lineup: ");
            for(batter bat : away.getLineup()){
                homeTextArea.appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            homeTextArea.appendText("\n" + "P: " + home.getAce().getName());
        } else{
            Platform.runLater(() -> homeTextArea.setText(home.getTeamName() + " Lineup: "));
            for(batter bat : home.getLineup()){
                Platform.runLater(() -> homeTextArea.appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                homeTextArea.appendText("\n" + "P: " + home.getAce().getName());
            });
        }
    }

    public void clearPlayers(){
        thirdRunner.setVisible(false);
        secondRunner.setVisible(false);
        firstRunner.setVisible(false);
        lhb.setVisible(false);
        rhb.setVisible(false);
        awayOnDeck.setVisible(false);
        homeOnDeck.setVisible(false);
    }

    public void hideBatters(){
        lhb.setVisible(false);
        rhb.setVisible(false);
        awayOnDeck.setVisible(false);
        homeOnDeck.setVisible(false);
    }

    public void handedness(batter atBat, boolean top){
        if(atBat.getBHand() == 'R'){
            rhb.setVisible(true);
            lhb.setVisible(false);
        } else{
            rhb.setVisible(false);
            lhb.setVisible(true);
        }
        if(top){
            awayOnDeck.setVisible(true);
        } else{
            homeOnDeck.setVisible(true);
        }
    }

    public void adjustRunners(batter[] bases){
        if(bases[0] != null){
            firstRunner.setVisible(true);
        } else{
            firstRunner.setVisible(false);
        }
        if(bases[1] != null){
            secondRunner.setVisible(true);
        } else{
            secondRunner.setVisible(false);
        }
        if(bases[2] != null){
            thirdRunner.setVisible(true);
        } else{
            thirdRunner.setVisible(false);
        }
    }

    //method that handles the gameplay
    public void playBall(){

        int i, j, hspot = 0, rspot = 0, outcome, to, roll;
        batter[] bases = new batter[3];

        appendArea("Time to play ball!");

        //outer loop will run 9 times, the amount of innings in a normal game
        for(i = 1; i <= 9; i++){
            //new inning, reset outs and bases
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }
            clearPlayers();
            setInning(i);
            setTop(true);
            appendArea("\n\n" +"Top of the " + i + " inning. ");
            updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            //while loop will continue to run until top of inning ends
            while(outs < 3){
                //prevents infinite loop in the case of extreme outlier case
                if(away.getScore() > 100 || home.getScore() > 100){
                    appendArea("\n" + "Score exceeded");
                    return;
                }
                appendArea("\n" + "Now up to bat, number " + away.getLineup()[rspot].getNum() + ", " + away.getLineup()[rspot].getName() + "!");
                handedness(away.getLineup()[rspot], true);
                awayOnDeck.setVisible(true);
                homeOnDeck.setVisible(false);

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                //stolen base checker to see if Runner on base attempts/succeeds in stealing
                roll = Math.abs(rand.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].getSpeed() >= 3) || (bases[1] != null && bases[1].getSpeed() >= 3))){
                    bases = awaySteal(bases);
                }
                //in case picked off Runner ended inning
                if(outs >= 3){
                    outcome = -1;
                }
                //if not, get outcome 
                else{
                outcome = outcome(away.getLineup()[rspot], home.getAce());
                }
                //get position ball was hit to
                to = Math.abs(rand.nextInt() % 9);
                if(outs < 3){
                    away.getLineup()[rspot].incAbs();
                    //out was/should be recorded
                    if(outcome <= 50){
                        //error on account of pitcher
                        if(to == 0 && errorCheck(home.getAce().getField())){
                            bases = awayError(bases, away.getLineup()[rspot], to);
                        }
                        //error on account of position player
                        else if(errorCheck(home.getLineup()[to].getField())){
                            bases = awayError(bases, away.getLineup()[rspot], to);
                        }
                        //no error, out(s) recorded
                        else{
                            awayOut(bases, to);
                        }
                    }
                    //single
                    else if(outcome <= 75){
                        appendArea("\n" +"Single!");
                        bases = awaySingle(bases, away.getLineup()[rspot], to);
                    }
                    //double
                    else if(outcome <= 90){
                        appendArea("\n" +"Double!");
                        bases = awayDouble(bases, away.getLineup()[rspot], to);
                    }
                    //HR
                    else if(outcome <= 97){
                        appendArea("\n" +"HR!");
                        bases = awayHR(bases, away.getLineup()[rspot]);
                    }
                    //Triple
                    else{
                        appendArea("\n" +"Triple!");
                        bases = awayTriple(bases, away.getLineup()[rspot]);
                    }
                    //advance spot in lineup by 1, wrap back to beginning if at end
                    rspot = (rspot + 1) % 9;
                    updateScoreArea();
                    updateAwayArea();
                }
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            //top of inning over, reset outs and bases
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }

            clearPlayers();
            //game over check, if true return
            if(i == 9 && home.getScore() > away.getScore()){
                appendArea("\n\n" +"Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + ".");
                return;
            }

            //7th inning stretch
            if(i == 7){
                appendArea("\nTime for the 7th inning stretch!" );
                String musicFile = "Ballgame.mp3";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                try{
                    Thread.sleep(36000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            
            setTop(false);
            appendArea("\n\n" +"Bottom of the " + i + " inning. ");
            updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            //will run until bottom of inning ends or walkoff
            while(outs < 3){
                //prevents infinite loop in rare outlier case
                if(home.getScore() > 100 || away.getScore() > 100){
                    appendArea("\n\n" +"Score exceeded");
                    return;
                }
                appendArea("\n" +"Now up to bat, number " + home.getLineup()[hspot].getNum() + ", " + home.getLineup()[hspot].getName() + "!");
                handedness(home.getLineup()[hspot], false);
                awayOnDeck.setVisible(false);
                homeOnDeck.setVisible(true);

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                //stolen base check
                roll = Math.abs(rand.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].getSpeed() >= 3) || (bases[1] != null && bases[1].getSpeed() >= 3))){
                    bases = homeSteal(bases);
                }
                //if picked off Runner ended inning
                if(outs >= 3){
                    outcome = -1;
                }
                //if not, get outcome of at bat
                else{
                    outcome = outcome(home.getLineup()[hspot], away.getAce());
                }
                //get position ball was hit to
                to = Math.abs(rand.nextInt() % 9);
                if(outs < 3){
                    home.getLineup()[hspot].incAbs();
                    //out/error
                    if(outcome <= 50){
                        //error by pitcher
                        if(to == 0 && errorCheck(away.getAce().getField())){
                            bases = homeError(bases, i, home.getLineup()[hspot], to);
                        }
                        //error by fielder
                        else if(errorCheck(away.getLineup()[to].getField())){
                            bases = homeError(bases, i, home.getLineup()[hspot], to);
                        }
                        //out(s)
                        else{
                            homeOut(bases, to, i);
                        }
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //single
                    else if(outcome <= 75){
                        appendArea("\n" +"Single!");
                        bases = homeSingle(bases, i, home.getLineup()[hspot], to);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //double
                    else if(outcome <= 90){
                        appendArea("\n" +"Double!");
                        bases = homeDouble(bases, i, home.getLineup()[hspot], to);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //HR
                    else if(outcome <= 97){
                        appendArea("\n" +"HR!");
                        bases = homeHR(bases, i, home.getLineup()[hspot]);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //triple
                    else{
                        appendArea("\n" +"Triple!");
                        bases = homeTriple(bases, i, home.getLineup()[hspot]);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    updateScoreArea();
                    updateHomeArea();
                    //advance home lineup by 1 spot, if at the end loop back around
                    hspot = (hspot + 1) % 9;
                }
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            clearPlayers();
            appendArea("\n" +"Inning " + i + " is over. The score is now " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + ".");
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        //if game still tied after 9 innings, go to extra innings
        if(home.getScore() == away.getScore()){
            extras();
        }
        appendArea("\n\n" +"Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
    }

    //if game goes to extra innings, this method will take over for gameplay
    public void extras(){
        int i, j, hspot = 1, rspot = 1, outcome, to;
        batter[] bases = new batter[3];
        Random r = new Random();
        int roll;

        appendArea("\n" +"Extra innings!");
        //i keeps track of what inning it is
        i = 10;
        //gameplay will continue until someone wins or extremely long scenario
        while(away.getScore() == home.getScore()){
            //prevent infinite loop in rare scenarios
            if(i >= 50 || away.getScore() > 100 || home.getScore() > 100){
                appendArea("\n" +"Time exceeded");
                return;
            }
            //new inning, reset outs and bases
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }

            setTop(true);
            setInning(i);
            clearPlayers();
            appendArea("\n\n" +"Top of the " + i + " inning. ");
            updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            //will run until top of inning ends
            while(outs < 3){
                //prevent infinite loop in rare scenario
                if(away.getScore() > 100 || home.getScore() > 100){
                    appendArea("\n" +"Score exceeded");
                    return;
                }
                appendArea("\n" +"Now up to bat, number " + away.getLineup()[rspot].getNum() + ", " + away.getLineup()[rspot].getName() + "!");
                handedness(away.getLineup()[rspot], true);
                awayOnDeck.setVisible(true);
                homeOnDeck.setVisible(false);

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                //stolen base check
                roll = Math.abs(r.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].getSpeed() >= 3) || (bases[1] != null && bases[1].getSpeed() >= 3))){
                    bases = awaySteal(bases);
                }
                //if inning should end due to picked off Runner being out 3
                if(outs >= 3){
                    outcome = -1;
                }
                //if not, run at bat
                else{
                    outcome = outcome(away.getLineup()[rspot], home.getAce());
                }
                //get position ball was hit to
                to = Math.abs(r.nextInt() % 9);
                if(outs < 3){
                //out/error
                if(outcome <= 50){
                    //error on account of pitcher
                    if(to == 0 && errorCheck(home.getAce().getField())){
                        bases = awayError(bases, away.getLineup()[rspot], to);
                    }
                    //error on account of position player
                    else if(errorCheck(home.getLineup()[to].getField())){
                        bases = awayError(bases, away.getLineup()[rspot], to);
                    }
                    //no error, out(s) recorded
                    else{
                        awayOut(bases, to);
                    }
                }
                //single
                else if( outcome <= 75){
                    appendArea("\n" +"Single!");
                    bases = awaySingle(bases, away.getLineup()[rspot], to);
                }
                //double
                else if( outcome <= 90){
                    appendArea("\n" +"Double!");
                    bases = awayDouble(bases, away.getLineup()[rspot], to);
                }
                //HR
                else if( outcome <= 97){
                    appendArea("\n" +"HR!");
                    bases = awayHR(bases, away.getLineup()[rspot]);
                }
                //Triple
                else{
                    appendArea("\n" +"Triple!");
                    bases = awayTriple(bases, away.getLineup()[rspot]);
                }
                updateScoreArea();
                updateAwayArea();
                //advance road lineup by 1 spot, wrap around if at end
                rspot = (rspot + 1) % 9;
            }
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            //top of inning over, reset bases and outs
            outs = 0;
            for(j = 0; j < 3; j++){
                bases[j] = null;
            }

            setTop(false);
            clearPlayers();
            appendArea("\n\n" +"Bottom of the " + i + " inning. ");
            updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            //prevent infinite loop
            while(outs < 3){
                if(away.getScore() > 100 || home.getScore() > 100){
                    appendArea("\n" +"Score exceeded");
                    return;
                }
                appendArea("\n" +"Now up to bat, number " + home.getLineup()[hspot].getNum() + ", " + home.getLineup()[hspot].getName() + "!");
                handedness(home.getLineup()[hspot], false);
                awayOnDeck.setVisible(false);
                homeOnDeck.setVisible(true);

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                //stolen base check
                roll = Math.abs(r.nextInt() % 4);
                if(roll == 0 && ((bases[0] != null && bases[0].getSpeed() >= 3) || (bases[1] != null && bases[1].getSpeed() >= 3))){
                    bases = homeSteal(bases);
                }
                //if picked off Runner ends inning
                if(outs >= 3){
                    outcome = -1;
                }
                //if not, get outcome of at bat
                else{
                    outcome = outcome(home.getLineup()[hspot], away.getAce());
                }
                //get position ball was hit to
                to = Math.abs(r.nextInt() % 9);
                if(outs < 3){
                    //out/error
                    if(outcome <= 50){
                        //error by pitcher
                        if(to == 0 && errorCheck(away.getAce().getField())){
                            bases = homeError(bases, i, home.getLineup()[hspot], to);
                        }
                        //error by fielder
                        else if(errorCheck(away.getLineup()[to].getField())){
                            bases = homeError(bases, i, home.getLineup()[hspot], to);
                        }
                        //out(s)
                        else{
                            homeOut(bases, to, i);
                        }
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //single
                    else if( outcome <= 75){
                        appendArea("\n" +"Single!");
                        bases = homeSingle(bases, i, home.getLineup()[hspot], to);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //double
                    else if( outcome <= 90){
                        appendArea("\n" +"Double!");
                        bases = homeDouble(bases, i, home.getLineup()[hspot], to);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //HR
                    else if( outcome <= 97){
                        appendArea("\n" +"HR!");
                        bases = homeHR(bases, i, home.getLineup()[hspot]);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    //triple
                    else{
                        appendArea("\n" +"Triple!");
                        bases = homeTriple(bases, i, home.getLineup()[hspot]);
                        //walkoff check
                        if(i == 9 && home.getScore() > away.getScore()){
                            return;
                        }
                    }
                    updateScoreArea();
                    updateHomeArea();
                    //advance home lineup by one spot, wrap around if at end
                    hspot = (hspot + 1) % 9;
                }
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            clearPlayers();
            appendArea("\n" +"Inning " + i + " is over. The score is now " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + ".");
            i++;
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}