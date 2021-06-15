/*Mark Dubin
  6/12/21
  BaseballFX - Gameplay Class*/

//imports
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.Random;
import javafx.application.Platform;

//Baseball class, houses Teams and outs
public class Baseball{
    public Random rand = new Random();
    private static boolean isTop;
    private static Team home, away;
    private static int outs, inning;
    private static TextArea outputTextArea, scoreArea, awayTextArea, homeTextArea;
    private static ImageView lhb, rhb, firstRunner, secondRunner, thirdRunner, awayOnDeck, homeOnDeck;

    //Baseball constructor, taking in Team names and constructing Teams
    public Baseball(String h, String a, TextArea outputArea, TextArea sArea, TextArea awayArea, TextArea homeArea, ImageView lb, ImageView rb, ImageView f, ImageView s, ImageView t, ImageView awayNext, ImageView homeNext){
        home = Team.buildTeam(h, true);
        away = Team.buildTeam(a, false);
        outputTextArea = outputArea;
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

    public static Team getHome(){
        return home;
    }

    public static Team getAway(){
        return away;
    }

    //ensure Team name inputted is valid
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

    //adjusts letters of Team names for proper capitalization
    public static String capsFix(String word){
        int i;
        //get char array of string to easily adjust
        char[]w = word.toCharArray();

        //first letter should be uppercase
        char l = word.charAt(0);
        l = Character.toUpperCase(l);
        w[0] = l;

        //go through Team name, capitalizing first letter after space
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

    //stealing base method for away Team
    public Batter[] awaySteal(Batter[] bases){
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (home.getLineup()[1].getArm() + home.getAce().getVelo()) / 2;

        //Runner on 1st and 2nd, no Runner on 3rd
        if(bases[0] != null && bases[1] != null && bases[2] == null){
            Visuals.appendArea("\n" +"Double steal attempt!");
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = bases[0];
                bases[0] = null;
                Visuals.appendArea("\n" +bases[2].getName() + " was able to steal third, and " + bases[1].getName() + " was able to steal second!");
            }
            //thrown out at 3rd
            else{
                name = bases[1].getName();
                bases[1] = bases[0];
                bases[0] = null;
                outs++;
                if(outs >= 3){
                    Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ".");
                }
                else{
                    Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ". " + bases[1].getName() + " was able to steal second!");
                }
            }
        }
        //Runner on 1st, no Runner on 2nd
        else if(bases[0] != null && bases[1] == null){
            //base stolen
            if(bases[0].getSpeed() > resistance && bases[0].getSpeed() > r){
                bases[1] = bases[0];
                bases[0] = null;
                Visuals.appendArea("\n" +bases[1].getName() + " was able to steal second!");
            }
            //thrown out
            else{
                name = bases[0].getName();
                bases[0] = null;
                outs++;
                Visuals.appendArea("\n" +name + " tried to steal second, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }
        //Runner on 2nd, no Runner on 3rd
        else if(bases[1] != null && bases[2] == null){
            //base stolen
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = null;
                Visuals.appendArea("\n" +bases[2].getName() + " was able to steal third!");
            }
            //thrown out
            else{
                name = bases[1].getName();
                bases[1] = null;
                outs++;
                Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }

        Visuals.adjustRunners(bases);

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        
        return bases;
    }

    //stealing base method for away Team
    public Batter[] homeSteal(Batter[] bases){
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (away.getLineup()[1].getArm() + away.getAce().getVelo()) / 2;

        //Runner on 1st and 2nd, no Runner on 3rd
        if(bases[0] != null && bases[1] != null && bases[2] == null){
            Visuals.appendArea("\n" +"Double steal attempt!");
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = bases[0];
                bases[0] = null;
                Visuals.appendArea("\n" +bases[2].getName() + " was able to steal third, and " + bases[1].getName() + " was able to steal second!");
            }
            //thrown out at 3rd
            else{
                name = bases[1].getName();
                bases[1] = bases[0];
                bases[0] = null;
                outs++;
                if(outs >= 3){
                    Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + away.getLineup()[1].getName() + "! Out number " + outs + ".");
                }
                else{
                    Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + home.getLineup()[1].getName() + "! Out number " + outs + ". " + bases[1].getName() + " was able to steal second!");
                }
            }
        }
        //Runner on 1st, no Runner on 2nd
        else if(bases[0] != null && bases[1] == null){
            //base stolen
            if(bases[0].getSpeed() > resistance && bases[0].getSpeed() > r){
                bases[1] = bases[0];
                bases[0] = null;
                Visuals.appendArea("\n" +bases[1].getName() + " was able to steal second!");
            }
            //thrown out
            else{
                name = bases[0].getName();
                bases[0] = null;
                outs++;
                Visuals.appendArea("\n" +name + " tried to steal second, but was thrown out by the catcher, " + away.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }
        //Runner on 2nd, no Runner on 3rd
        else if(bases[1] != null && bases[2] == null){
            //base stolen
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = null;
                Visuals.appendArea("\n" +bases[2].getName() + " was able to steal third!");
            }
            //thrown out
            else{
                name = bases[1].getName();
                bases[1] = null;
                outs++;
                Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + away.getLineup()[1].getName() + "! Out number " + outs + ".");
            }
        }

        Visuals.adjustRunners(bases);

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return bases;
    }

    //determines outcome of at bat, how many bases (if any) Batter will advance
    public int outcome(Batter b, Pitcher p){
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

    //method for away Team hitting a sacrifice fly
    public Batter[] awaySacFly(Batter[] bases, int to){
        int r = Math.abs(rand.nextInt() % 5);
        Batter s, t;
        //Runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            away.incScore(1);
            Visuals.updateScoreArea();
            Visuals.appendArea("\n" +"Batter hit a sac fly. " + t.getName() + " comes around to score, " + s.getName() + " advances to third. Out number " + outs + ".");
        }
        //Runner on 2nd
        else if(bases[1] != null){
            //advances safely
            if(bases[1].getSpeed() > home.getLineup()[to].getArm() || bases[1].getSpeed() > r){
                s = bases[1];
                bases[2] = bases[1];
                bases[1] = null;
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " advances to third. Out number " + outs + ".");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                outs++;
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " got doubled off trying to advance to third! Out number " + outs + ".");
            }
        }
        //Runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].getSpeed() > home.getLineup()[to].getArm() || bases[2].getSpeed() > r){
                t = bases[2];
                bases[2] = null;
                away.incScore(1);
                Visuals.updateScoreArea();
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " comes around to score! Out number " + outs + ".");
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                outs++;
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " got doubled off trying to come home and score! Out number " + outs + ".");
            }
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
        return bases;
    }

    public Batter[] homeSacFly(Batter[] bases, int to, int inning){
        int r = Math.abs(rand.nextInt() % 5);
        Batter s, t;
        //Runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            home.incScore(1);
            Visuals.updateScoreArea();
            Visuals.appendArea("\n" +"Batter hit a sac fly. " + t.getName() + " comes around to score, " + s.getName() + " advances to third. Out number " + outs + ".");
            if(inning >= 9 && home.getScore() > away.getScore()){
                Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                Visuals.finalScoreArea();
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
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " advances to third. Out number " + outs + ".");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                outs++;
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " got doubled off trying to advance to third! Out number " + outs + ".");
            }
        }
        //Runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].getSpeed() > away.getLineup()[to].getArm() || bases[2].getSpeed() > r){
                t = bases[2];
                bases[2] = null;
                home.incScore(1);
                Visuals.updateScoreArea();
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " comes around to score! Out number " + outs + ".");
                if(inning >= 9 && home.getScore() > away.getScore()){
                    Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                    Visuals.finalScoreArea();
                    return bases;
                }
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                outs++;
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " got doubled off trying to come home and score! Out number " + outs + ".");
            }
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
        return bases;
    }

    //method to handle away time recording an out or multiple outs
    public void awayOut(Batter[] bases, int to){
        int roll;
        roll = Math.abs(rand.nextInt() % 5);
        //double play, hit to Pitcher
        if(bases[0] != null && outs < 2 && to == 0 && home.getAce().getField() > roll){
            bases[0] = null;
            outs+= 2;
            Visuals.appendArea("\n" +"Double play! There are now " + outs + " outs.");
            away.getLineup()[away.getBattingSpot()].incAbs();
        }
        //triple play hit to Pitcher
        else if(outs == 0 && bases[0] != null && bases[1] != null && to == 0 && home.getAce().getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            Visuals.appendArea("\n" + "Triple play! There are now " + outs + " outs.");
            away.getLineup()[away.getBattingSpot()].incAbs();
        }
        //double play, hit to infielder
        else if(bases[0] != null && outs < 2 && to < 5 && home.getLineup()[to].getField() > roll){
            bases[0] = null;
            outs+= 2;
            Visuals.appendArea("\n" + "Double play! There are now " + outs + " outs.");
            away.getLineup()[away.getBattingSpot()].incAbs();
        }
        //triple play hit to infielder
        else if(outs == 0 && bases[0] != null && bases[1] != null && to < 5 && home.getLineup()[to].getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            Visuals.appendArea("\n" + "Triple play! There are now " + outs + " outs.");
            away.getLineup()[away.getBattingSpot()].incAbs();
        }
        //sac fly hit to outfielder
        else if(outs < 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && away.getLineup()[to].getField() > roll){
            outs++;
            bases = awaySacFly(bases, to);
        }
        //ordinary out
        else{
            outs++;
            Visuals.appendArea("\n" + "Out number " + outs + ".");
            away.getLineup()[away.getBattingSpot()].incAbs();
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
        return;
    }

    //method to handle home time recording an out or multiple outs
    public void homeOut(Batter[] bases, int to, int inning){
        int roll;
        roll = Math.abs(rand.nextInt() % 5);
        //double play, hit to Pitcher
        if(bases[0] != null && outs < 2 && to == 0 && away.getAce().getField() > roll){
            bases[0] = null;
            outs+= 2;
            Visuals.appendArea("\n" + "Double play! There are now " + outs + " outs.");
            home.getLineup()[home.getBattingSpot()].incAbs();
        }
        //triple play, hit to infielder
        else if(outs == 0 && bases[0] != null && bases[1] != null && to == 0 && away.getAce().getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            Visuals.appendArea("\n" + "Triple play! There are now " + outs + " outs.");
            home.getLineup()[home.getBattingSpot()].incAbs();
        }
        //double play, hit to infielder
        if(bases[0] != null && outs < 2 && to < 5 && away.getLineup()[to].getField() > roll){
            bases[0] = null;
            outs+= 2;
            Visuals.appendArea("\n" + "Double play! There are now " + outs + " outs.");
            home.getLineup()[home.getBattingSpot()].incAbs();
        }
        //triple play, hit to infielder
        else if(outs == 0 && bases[0] != null && bases[1] != null && to < 5 && away.getLineup()[to].getField() > roll){
            bases[0] = null;
            bases[1] = null;
            outs+= 3;
            Visuals.appendArea("\n" + "Triple play! There are now " + outs + " outs.");
            home.getLineup()[home.getBattingSpot()].incAbs();
        }
        //sac fly, hit to outfielder
        else if(outs < 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && away.getLineup()[to].getField() > roll){
            outs++;
            bases = homeSacFly(bases, to, inning);
        }
        //ordinary out
        else{
            outs++;
            Visuals.appendArea("\n" + "Out number " + outs + ".");
            home.getLineup()[home.getBattingSpot()].incAbs();
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
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

    //method used to handle errors committed by home Team
    public Batter[] awayError(Batter[] bases, Batter atBat, int to){
        int roll;
        roll = Math.abs(rand.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = awaySingle(bases, atBat, to);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 2nd base!");
            bases = awayDouble(bases, atBat, to);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 3rd base!");
            bases = awayTriple(bases, atBat);
        }
        //four-base error
        else{
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " came all the way around to score!");
            bases = awayHR(bases, atBat);
        }

        away.incErrors();
        Visuals.adjustRunners(bases);
        return bases;
    }

    //error used to handle errors committed by away Team
    public Batter[] homeError(Batter[] bases, int inning, Batter atBat, int to){
        int roll;
        roll = Math.abs(rand.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = homeSingle(bases, inning, atBat, to);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 2nd base!");
            bases = homeDouble(bases, inning, atBat, to);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 3rd base!");
            bases = homeTriple(bases, inning, atBat);
        }
        //four-base error
        else{
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " came all the way around to score!");
            bases = homeHR(bases, inning, atBat);
        }

        home.incErrors();
        Visuals.adjustRunners(bases);
        return bases;
    }

    //method to handle away Team walking
    public Batter[] awayWalk(Batter[] bases, Batter atBat){
        //bases loaded
        if(bases[2] != null && bases[1] != null && bases[0] != null){
            Visuals.appendArea("\n" + bases[2].getName() + " has scored from third!");
            bases[2] = bases[1];
            bases[1] = bases[0];
            away.incScore(1);
            Visuals.updateScoreArea();
        //runners on 1st and 2nd, no runner on 3rd
        } else if(bases[2] == null && bases[1] != null && bases[0] != null){
            bases[2] = bases[1];
            bases[1] = bases[0];
        //runner on 1st, no runner on 2nd, potential runner on 3rd is irrelevant
        } else if(bases[1] == null && bases[0] != null){
            bases[1] = bases[0];
        }
        //put Batter on 1st
        bases[0] = atBat;
        Visuals.adjustRunners(bases);
        Visuals.hideBatters();
        return bases;
    }

    //method to handle home Team walking
    public Batter[] homeWalk(Batter[] bases, Batter atBat){
        //bases loaded
        if(bases[2] != null && bases[1] != null && bases[0] != null){
            Visuals.appendArea("\n" + bases[2].getName() + " has scored from third!");
            bases[2] = bases[1];
            bases[1] = bases[0];
            away.incScore(1);
            Visuals.updateScoreArea();
        //runners on 1st and 2nd, no runner on 3rd
        } else if(bases[2] == null && bases[1] != null && bases[0] != null){
            bases[2] = bases[1];
            bases[1] = bases[0];
        //runner on 1st, no runner on 2nd, potential runner on 3rd is irrelevant
        } else if(bases[1] == null && bases[0] != null){
            bases[1] = bases[0];
        }

        //if bottom 9 or later and home score greater than away, game over
        if(inning >= 9 && home.getScore() > away.getScore()){
            Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
            Visuals.finalScoreArea();
            return bases;
        }

        //put Batter on 1st
        bases[0] = atBat;
        Visuals.adjustRunners(bases);
        Visuals.hideBatters();
        return bases;
    }

    //method to handle away Team hitting a single
    public Batter[] awaySingle(Batter[] bases, Batter atBat, int to){
        int i, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            r = rand.nextInt() % 5;
            //Runner on 3rd comes in to score
            if(i == 2 && bases[i] != null){
                Visuals.appendArea("\n" + bases[i].getName() + " has scored from third!");
                bases[i] = null;
                away.incScore(1);
                Visuals.updateScoreArea();
            }
            //Runner on 2nd will try to score
            else if(bases[i] != null && r > 2 && i == 1 && bases[i].getSpeed() > 2 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(home.getLineup()[to].getArm() > r && home.getLineup()[to].getArm() > bases[i].getSpeed()){
                    outs++;
                    Visuals.appendArea("\n" +bases[i].getName() + " was thrown out trying to come in to score from second! Out number " + outs);
                    bases[i] = null;
                }
                //scores
                else{
                    away.incScore(1);
                    Visuals.updateScoreArea();
                    Visuals.appendArea("\n" +bases[i].getName() + " has scored from second!");
                    bases[i] = null;
                }
            }
            //Runner on any other base advances
            else if(bases[i] != null){
                bases[i+1] = bases[i];
                bases[i] = null;
            }
        }
        //put Batter on 1st
        bases[0] = atBat;
        Visuals.adjustRunners(bases);
        Visuals.hideBatters();
        return bases;
    }

    //method to handle home Team hitting a single
    public Batter[] homeSingle(Batter[] bases, int inning, Batter atBat, int to){
        int i, r;
        r = rand.nextInt() % 5;

        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            r = rand.nextInt() % 5;
            //Runner on 3rd scores
            if(i == 2 && bases[i] != null){
                Visuals.appendArea("\n" +bases[i].getName() + " has scored from third!");
                bases[i] = null;
                home.incScore(1);
                Visuals.updateScoreArea();
            }
            //Runner on 2nd will try to score
            else if(bases[i] != null && r > 2 && i == 1 && bases[i].getSpeed() > 2 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(away.getLineup()[to].getArm() > r && away.getLineup()[to].getArm() > bases[i].getSpeed()){
                    outs++;
                    Visuals.appendArea("\n" +bases[i].getName() + " was thrown out trying to come in to score from second! Out number " + outs);
                    bases[i] = null;
                }
                //scores
                else{
                    home.incScore(1);
                    Visuals.updateScoreArea();
                    Visuals.appendArea("\n" +bases[i].getName() + " has scored from second!");
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
                Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                Visuals.finalScoreArea();
                return bases;
            }
        }
        //put Batter on 1st
        bases[0] = atBat;
        Visuals.adjustRunners(bases);

        Visuals.hideBatters();
        return bases;
    }

    //method to handle away Team hitting a double
    public Batter[] awayDouble(Batter[] bases, Batter atBat, int to){
        int j, count = 0, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse
        for(j = 2; j >= 0; j--){
            r = rand.nextInt() % 5;
            //Runner on 2nd or 3rd come in to score
            if(j >= 1 && bases[j] != null){
                bases[j] = null;
                away.incScore(1);
                Visuals.updateScoreArea();
                count++;
            }
            //Runner on 1st will try to score
            else if(bases[j] != null && r > 2 && j == 0 && bases[j].getSpeed() > 3 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(home.getLineup()[to].getArm() > r && home.getLineup()[to].getArm() > bases[j].getSpeed()){
                    outs++;
                    Visuals.appendArea("\n" +bases[j].getName() + " was thrown out trying to come in to score from first! Out number " + outs);
                    bases[j] = null;
                }
                //scores
                else{
                    away.incScore(1);
                    Visuals.updateScoreArea();
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
        //put Batter on 2nd
        bases[1] = atBat;
        Visuals.adjustRunners(bases);
        //grammar
        if(count > 1){
            Visuals.appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" +count + " run has scored!");
        }
        count = 0;
        Visuals.hideBatters();
        return bases;
    }

    //method to handle home Team hitting a double
    public Batter[] homeDouble(Batter[] bases, int inning, Batter atBat, int to){
        int j, count = 0, r;
        r = rand.nextInt() % 5;

        //deal with each base in reverse order
        for(j = 2; j >= 0; j--){
            r = rand.nextInt() % 5;
            //Runner on 2nd/3rd score
            if(j >= 1 && bases[j] != null){
                bases[j] = null;
                home.incScore(1);
                Visuals.updateScoreArea();
                count++;
            }
            //Runner on 1st will try to score
            else if(bases[j] != null && r > 2 && j == 0 && bases[j].getSpeed() > 3 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(away.getLineup()[to].getArm() > r && away.getLineup()[to].getArm() > bases[j].getSpeed()){
                    outs++;
                    Visuals.appendArea("\n" +bases[j].getName() + " was thrown out trying to come in to score from first! Out number " + outs);
                    bases[j] = null;
                }
                //scores
                else{
                    home.incScore(1);
                    Visuals.updateScoreArea();
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
                Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                Visuals.finalScoreArea();
                return bases;
            }
        }
        //put Batter on 2nd
        bases[1] = atBat;
        Visuals.adjustRunners(bases);

        //grammar check
        if(count > 1){
            Visuals.appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" +count + " run has scored!");
        }

        Visuals.hideBatters();
        return bases;
    }

    //method to handle away Team hitting a triple
    public Batter[] awayTriple(Batter[] bases, Batter atBat){
        int j, count = 0;
        //go through each base, if any has a Runner they score
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j] = null;
                away.incScore(1);
                Visuals.updateScoreArea();
                count++;
                Visuals.clearPlayers();
            }
        }
        //move Batter to 3rd
        bases[2] = atBat;

        //grammar check
        if(count > 1){
            Visuals.appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" +count + " run has scored!");
        }

        Visuals.adjustRunners(bases);
        Visuals.hideBatters();
        return bases;
    }

    //method to handle home Team hitting a triple
    public Batter[] homeTriple(Batter[] bases, int inning, Batter atBat){
        int j, count = 0;

        //go through each base, any Runner scores
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j] = null;
                home.incScore(1);
                Visuals.updateScoreArea();
                Visuals.clearPlayers();
                //walkoff checker
                if(inning >= 9 && home.getScore() > away.getScore()){
                    Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                    Visuals.finalScoreArea();
                    return bases;
                }
                count++;
            }
        }
        //put Batter on 3rd
        bases[2] = atBat;
        Visuals.adjustRunners(bases);

        //grammar check
        if(count > 1){
            Visuals.appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" +count + " run has scored!");
        }

        Visuals.hideBatters();
        return bases;
    }

    //method to handle away Team hitting a HR
    public Batter[] awayHR(Batter[] bases, Batter atBat){
        int j, count = 0;
        //any Runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j] = null;
                away.incScore(1);
                Visuals.updateScoreArea();
                count++;
                Visuals.clearPlayers();
            }
        }
        //add one more for the Batter
        away.incScore(1);
        Visuals.updateScoreArea();
        count++;
        //grammar check
        if(count > 1){
            Visuals.appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" +count + " run has scored!");
        }

        Visuals.hideBatters();
        return bases;
    }

    //method to handle home Team hitting a HR
    public Batter[] homeHR(Batter[] bases, int inning, Batter atBat){
        int j, count = 0;

        //any Runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j] = null;
                home.incScore(1);
                Visuals.updateScoreArea();
                Visuals.clearPlayers();
                //walkoff check
                if(inning >= 9 && home.getScore() > away.getScore()){
                    Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
                    Visuals.finalScoreArea();
                    return bases;
                }
                count++;
            }
        }
        //add one more run for Batter
        home.incScore(1);
        Visuals.updateScoreArea();
        count++;
        //grammar check
        if(count > 1){
            Visuals.appendArea("\n" +count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" +count + " run has scored!");
        }

        Visuals.hideBatters();
        return bases;
    }

    public static TextArea getOutputTextArea(){
        return outputTextArea;
    }

    public static TextArea getScoreArea(){
        return scoreArea;
    }

    public static TextArea getHomeArea(){
        return homeTextArea;
    }

    public static TextArea getAwayArea(){
        return awayTextArea;
    }

    public static int getInning(){
        return inning;
    }

    public static int getOuts(){
        return outs;
    }

    public static ImageView getFirstRunner(){
        return firstRunner;
    }

    public static ImageView getSecondRunner(){
        return secondRunner;
    }

    public static ImageView getThirdRunner(){
        return thirdRunner;
    }

    public static ImageView getLhb(){
        return lhb;
    }

    public static ImageView getRhb(){
        return rhb;
    }

    public static ImageView getHomeOnDeck(){
        return homeOnDeck;
    }

    public static ImageView getAwayOnDeck(){
        return awayOnDeck;
    }

    public static boolean getIsTop(){
        return isTop;
    }

    public void topInning(){
        int outcome, to, roll;
        Batter[] bases = new Batter[3];

        //while loop will continue to run until top of inning ends
        while(outs < 3){
            //prevents infinite loop in the case of extreme outlier case
            if(away.getScore() > 100 || home.getScore() > 100){
                Visuals.appendArea("\n" + "Score exceeded");
                return;
            }
            Visuals.appendArea("\n" + "Now up to bat, number " + away.getLineup()[away.getBattingSpot()].getNum() + ", " + away.getLineup()[away.getBattingSpot()].getName() + "!");
            Visuals.handedness(away.getLineup()[away.getBattingSpot()]);
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
            outcome = outcome(away.getLineup()[away.getBattingSpot()], home.getAce());
            }
            //get position ball was hit to
            to = Math.abs(rand.nextInt() % 9);
            if(outs < 3){
                //out was/should be recorded
                if(outcome <= 30){
                    //error on account of Pitcher
                    if(to == 0 && errorCheck(home.getAce().getField())){
                        bases = awayError(bases, away.getLineup()[away.getBattingSpot()], to);
                        away.getLineup()[away.getBattingSpot()].incAbs();
                    }
                    //error on account of position player
                    else if(errorCheck(home.getLineup()[to].getField())){
                        bases = awayError(bases, away.getLineup()[away.getBattingSpot()], to);
                        away.getLineup()[away.getBattingSpot()].incAbs();
                    }
                    //no error, out(s) recorded
                    else{
                        awayOut(bases, to);
                    }
                }
                //walk
                else if(outcome <= 40){
                    bases = awayWalk(bases, away.getLineup()[away.getBattingSpot()]);
                    if(outcome > 32){
                        Visuals.appendArea("\n" + home.getAce().getName() + " just hit " + away.getLineup()[away.getBattingSpot()].getName() + " with the pitch!");
                    } else{
                        Visuals.appendArea("\n" + home.getAce().getName() + " has walked " + away.getLineup()[away.getBattingSpot()].getName() + ".");
                    }
                }
                //single
                else if(outcome <= 65){
                    Visuals.appendArea("\n" +"Single!");
                    bases = awaySingle(bases, away.getLineup()[away.getBattingSpot()], to);
                    away.getLineup()[away.getBattingSpot()].incHits();
                    away.incHits();
                    away.getLineup()[away.getBattingSpot()].incAbs();
                }
                //double
                else if(outcome <= 80){
                    Visuals.appendArea("\n" +"Double!");
                    bases = awayDouble(bases, away.getLineup()[away.getBattingSpot()], to);
                    away.getLineup()[away.getBattingSpot()].incHits();
                    away.incHits();
                    away.getLineup()[away.getBattingSpot()].incAbs();
                }
                //HR
                else if(outcome <= 93){
                    Visuals.appendArea("\n" +"HR!");
                    bases = awayHR(bases, away.getLineup()[away.getBattingSpot()]);
                    away.getLineup()[away.getBattingSpot()].incHits();
                    away.incHits();
                    away.getLineup()[away.getBattingSpot()].incAbs();
                }
                //Triple
                else{
                    Visuals.appendArea("\n" +"Triple!");
                    bases = awayTriple(bases, away.getLineup()[away.getBattingSpot()]);
                    away.getLineup()[away.getBattingSpot()].incHits();
                    away.incHits();
                    away.getLineup()[away.getBattingSpot()].incAbs();
                }

                away.nextBatter();
                Visuals.updateScoreArea();
                Visuals.updateAwayArea();
            }
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void bottomInning(){
        int outcome, to, roll;
        Batter[] bases = new Batter[3];

        //will run until bottom of inning ends or walkoff
        while(outs < 3){
            //prevents infinite loop in rare outlier case
            if(home.getScore() > 100 || away.getScore() > 100){
                Visuals.appendArea("\n\n" +"Score exceeded");
                return;
            }
            Visuals.appendArea("\n" +"Now up to bat, number " + home.getLineup()[home.getBattingSpot()].getNum() + ", " + home.getLineup()[home.getBattingSpot()].getName() + "!");
            Visuals.handedness(home.getLineup()[home.getBattingSpot()]);
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
                outcome = outcome(home.getLineup()[home.getBattingSpot()], away.getAce());
            }
            //get position ball was hit to
            to = Math.abs(rand.nextInt() % 9);
            if(outs < 3){
                //out/error
                if(outcome <= 30){
                    //error by Pitcher
                    if(to == 0 && errorCheck(away.getAce().getField())){
                        bases = homeError(bases, inning, home.getLineup()[home.getBattingSpot()], to);
                        home.getLineup()[home.getBattingSpot()].incAbs();
                    }
                    //error by fielder
                    else if(errorCheck(away.getLineup()[to].getField())){
                        bases = homeError(bases, inning, home.getLineup()[home.getBattingSpot()], to);
                        home.getLineup()[home.getBattingSpot()].incAbs();
                    }
                    //out(s)
                    else{
                        homeOut(bases, to, inning);
                    }
                    //walkoff check
                    if(inning >= 9 && home.getScore() > away.getScore()){
                        return;
                    }
                }
                //walk
                else if(outcome <= 40){
                    bases = homeWalk(bases, home.getLineup()[home.getBattingSpot()]);
                    if(outcome > 32){
                        Visuals.appendArea("\n" + home.getAce().getName() + " just hit " + away.getLineup()[away.getBattingSpot()].getName() + " with the pitch!");
                    } else{
                        Visuals.appendArea("\n" + away.getAce().getName() + " has walked " + home.getLineup()[home.getBattingSpot()].getName());
                    }
                }
                //single
                else if(outcome <= 65){
                    Visuals.appendArea("\n" +"Single!");
                    bases = homeSingle(bases, inning, home.getLineup()[home.getBattingSpot()], to);
                    home.getLineup()[home.getBattingSpot()].incHits();
                    home.incHits();
                    home.getLineup()[home.getBattingSpot()].incAbs();
                    //walkoff check
                    if(inning >= 9 && home.getScore() > away.getScore()){
                        return;
                    }
                }
                //double
                else if(outcome <= 80){
                    Visuals.appendArea("\n" +"Double!");
                    bases = homeDouble(bases, inning, home.getLineup()[home.getBattingSpot()], to);
                    home.getLineup()[home.getBattingSpot()].incHits();
                    home.incHits();
                    home.getLineup()[home.getBattingSpot()].incAbs();
                    //walkoff check
                    if(inning >= 9 && home.getScore() > away.getScore()){
                        return;
                    }
                }
                //HR
                else if(outcome <= 93){
                    Visuals.appendArea("\n" +"HR!");
                    bases = homeHR(bases, inning, home.getLineup()[home.getBattingSpot()]);
                    home.getLineup()[home.getBattingSpot()].incHits();
                    home.incHits();
                    home.getLineup()[home.getBattingSpot()].incAbs();
                    //walkoff check
                    if(inning >= 9 && home.getScore() > away.getScore()){
                        return;
                    }
                }
                //triple
                else{
                    Visuals.appendArea("\n" + "Triple!");
                    bases = homeTriple(bases, inning, home.getLineup()[home.getBattingSpot()]);
                    home.getLineup()[home.getBattingSpot()].incHits();
                    home.incHits();
                    home.getLineup()[home.getBattingSpot()].incAbs();
                    //walkoff check
                    if(inning >= 9 && home.getScore() > away.getScore()){
                        return;
                    }
                }
                Visuals.updateScoreArea();
                Visuals.updateHomeArea();
                //advance home lineup by 1 spot, if at the end loop back around
                home.nextBatter();
            }
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //method that handles the gameplay
    public void playBall(){
        int i;

        Visuals.appendArea("Time to play ball!");

        //outer loop will run 9 times, the amount of innings in a normal game
        for(i = 1; i <= 9; i++){
            //new inning, reset outs, visuals, set inning data
            outs = 0;
            Visuals.clearPlayers();
            setInning(i);
            setTop(true);
            Visuals.appendArea("\n\n" +"Top of the " + i + " inning.");
            Visuals.updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            topInning();

            //top of inning over, reset outs, visuals
            outs = 0;
            Visuals.clearPlayers();

            //game over check, if true return
            if(i == 9 && home.getScore() > away.getScore()){
                Visuals.appendArea("\n\n" +"Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + ".");
                Visuals.finalScoreArea();
                return;
            }

            //7th inning stretch
            if(i == 7){
                Visuals.appendArea("\nTime for the 7th inning stretch!");
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
            
            //set inning data, visuals
            setTop(false);
            Visuals.appendArea("\n\n" +"Bottom of the " + i + " inning. ");
            Visuals.updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            bottomInning();

            //update visuals
            Visuals.clearPlayers();
            Visuals.appendArea("\n" +"Inning " + i + " is over. The score is now " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + ".");

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        //if game still tied after 9 innings, go to extra innings
        if(home.getScore() == away.getScore()){
            extras();
        } else{
            Visuals.appendArea("\n\n" + "Game over! The final score is " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + "!");
        }

        //game over
        Visuals.finalScoreArea();
    }

    //if game goes to extra innings, this method will take over for gameplay
    public void extras(){
        int i;

        Visuals.appendArea("\n" +"Extra innings!");
        //i keeps track of what inning it is
        i = 10;

        //gameplay will continue until someone wins or extremely long scenario
        while(away.getScore() == home.getScore()){
            //prevent infinite loop in rare scenarios
            if(i >= 50 || away.getScore() > 100 || home.getScore() > 100){
                Visuals.appendArea("\n" +"Time exceeded");
                return;
            }
            //new inning, reset outs, visuals, update inning data
            outs = 0;
            setTop(true);
            setInning(i);
            Visuals.clearPlayers();
            Visuals.appendArea("\n\n" +"Top of the " + i + " inning. ");
            Visuals.updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            topInning();

            //top of inning over, reset outs, visuals
            outs = 0;
            setTop(false);
            Visuals.clearPlayers();
            Visuals.appendArea("\n\n" +"Bottom of the " + i + " inning. ");
            Visuals.updateScoreArea();

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            bottomInning();
            
            //update visuals after inning, increment i to next inning
            Visuals.clearPlayers();
            if(away.getScore() == home.getScore()){
            Visuals.appendArea("\n" +"Inning " + i + " is over. The score is now " + away.getTeamName() + " " + away.getScore() + ", " + home.getTeamName() + " " + home.getScore() + ".");
            }
            i++;
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
