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

//Baseball class, houses Teams and outs
public class Baseball{
    public Random rand = new Random();
    private static boolean isTop;
    private static Team home, away;
    private static int outs, inning;
    private static TextArea outputTextArea, scoreArea, awayTextArea, homeTextArea;
    private static ImageView awayOnDeck, homeOnDeck;

    //Baseball constructor, taking in Team names and constructing Teams
    public Baseball(String h, String a, TextArea outputArea, TextArea sArea, TextArea awayArea, TextArea homeArea, ImageView awayNext, ImageView homeNext){
        home = Team.buildTeam(h, true);
        away = Team.buildTeam(a, false);
        outputTextArea = outputArea;
        scoreArea = sArea;
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

    public static Team getHomeTeam(){
        return home;
    }

    public static Team getAwayTeam(){
        return away;
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

    public static void incOuts(int num){
        outs+= num;
    }

    public static boolean getIsTop(){
        return isTop;
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
                bases = AwayActions.awaySteal(bases);
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
                        bases = AwayActions.awayError(bases, away.getLineup()[away.getBattingSpot()], to);
                        away.getLineup()[away.getBattingSpot()].incAbs();
                    }
                    //error on account of position player
                    else if(errorCheck(home.getLineup()[to].getField())){
                        bases = AwayActions.awayError(bases, away.getLineup()[away.getBattingSpot()], to);
                        away.getLineup()[away.getBattingSpot()].incAbs();
                    }
                    //no error, out(s) recorded
                    else{
                        AwayActions.awayOut(bases, to);
                    }
                }
                //walk
                else if(outcome <= 40){
                    bases = AwayActions.awayWalk(bases, away.getLineup()[away.getBattingSpot()]);
                    if(outcome > 32){
                        Visuals.appendArea("\n" + home.getAce().getName() + " just hit " + away.getLineup()[away.getBattingSpot()].getName() + " with the pitch!");
                    } else{
                        Visuals.appendArea("\n" + home.getAce().getName() + " has walked " + away.getLineup()[away.getBattingSpot()].getName() + ".");
                    }
                }
                //single
                else if(outcome <= 65){
                    Visuals.appendArea("\n" +"Single!");
                    bases = AwayActions.awaySingle(bases, away.getLineup()[away.getBattingSpot()], to);
                    away.getLineup()[away.getBattingSpot()].incHits();
                    away.incHits();
                    away.getLineup()[away.getBattingSpot()].incAbs();
                }
                //double
                else if(outcome <= 80){
                    Visuals.appendArea("\n" +"Double!");
                    bases = AwayActions.awayDouble(bases, away.getLineup()[away.getBattingSpot()], to);
                    away.getLineup()[away.getBattingSpot()].incHits();
                    away.incHits();
                    away.getLineup()[away.getBattingSpot()].incAbs();
                }
                //HR
                else if(outcome <= 93){
                    Visuals.appendArea("\n" +"HR!");
                    bases = AwayActions.awayHR(bases);
                    away.getLineup()[away.getBattingSpot()].incHits();
                    away.incHits();
                    away.getLineup()[away.getBattingSpot()].incAbs();
                }
                //Triple
                else{
                    Visuals.appendArea("\n" +"Triple!");
                    bases = AwayActions.awayTriple(bases, away.getLineup()[away.getBattingSpot()]);
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
                bases = HomeActions.homeSteal(bases);
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
                        bases = HomeActions.homeError(bases, home.getLineup()[home.getBattingSpot()], to);
                        home.getLineup()[home.getBattingSpot()].incAbs();
                    }
                    //error by fielder
                    else if(errorCheck(away.getLineup()[to].getField())){
                        bases = HomeActions.homeError(bases, home.getLineup()[home.getBattingSpot()], to);
                        home.getLineup()[home.getBattingSpot()].incAbs();
                    }
                    //out(s)
                    else{
                        HomeActions.homeOut(bases, to, inning);
                    }
                    //walkoff check
                    if(inning >= 9 && home.getScore() > away.getScore()){
                        return;
                    }
                }
                //walk
                else if(outcome <= 40){
                    bases = HomeActions.homeWalk(bases, home.getLineup()[home.getBattingSpot()]);
                    if(outcome > 32){
                        Visuals.appendArea("\n" + home.getAce().getName() + " just hit " + away.getLineup()[away.getBattingSpot()].getName() + " with the pitch!");
                    } else{
                        Visuals.appendArea("\n" + away.getAce().getName() + " has walked " + home.getLineup()[home.getBattingSpot()].getName());
                    }
                }
                //single
                else if(outcome <= 65){
                    Visuals.appendArea("\n" +"Single!");
                    bases = HomeActions.homeSingle(bases, home.getLineup()[home.getBattingSpot()], to);
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
                    bases = HomeActions.homeDouble(bases, home.getLineup()[home.getBattingSpot()], to);
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
                    bases = HomeActions.homeHR(bases);
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
                    bases = HomeActions.homeTriple(bases, home.getLineup()[home.getBattingSpot()]);
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
