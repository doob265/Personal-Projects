/*Mark Dubin
  6/15/21
  BaseballFX - Home Team Actions Class*/

import java.util.Random;

public class HomeActions {

    public static Random rand = new Random();
    
    //stealing base method for away Team
    public static Batter[] homeSteal(Batter[] bases){
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (Baseball.getAwayTeam().getLineup()[1].getArm() + Baseball.getAwayTeam().getAce().getVelo()) / 2;

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
                Baseball.incOuts(1);
                if(Baseball.getOuts()>= 3){
                    Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + Baseball.getAwayTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts()+ ".");
                }
                else{
                    Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + Baseball.getHomeTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts()+ ". " + bases[1].getName() + " was able to steal second!");
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
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +name + " tried to steal second, but was thrown out by the catcher, " + Baseball.getAwayTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts()+ ".");
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
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + Baseball.getAwayTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts()+ ".");
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

    public static Batter[] homeSacFly(Batter[] bases, int to, int inning){
        int r = Math.abs(rand.nextInt() % 5);
        Batter s, t;
        //Runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            Baseball.getHomeTeam().incScore(1);
            Visuals.updateScoreArea();
            Visuals.appendArea("\n" +"Batter hit a sac fly. " + t.getName() + " comes around to score, " + s.getName() + " advances to third. Out number " + Baseball.getOuts()+ ".");
            if(inning >= 9 && Baseball.getHomeTeam().getScore() > Baseball.getAwayTeam().getScore()){
                Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + Baseball.getAwayTeam().getTeamName() + " " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + " " + Baseball.getHomeTeam().getScore() + "!");
                Visuals.finalScoreArea();
                return bases;
            }
        }
        //Runner on 2nd
        else if(bases[1] != null){
            //advances safely
            if(bases[1].getSpeed() > Baseball.getAwayTeam().getLineup()[to].getArm() || bases[1].getSpeed() > r){
                s = bases[1];
                bases[2] = bases[1];
                bases[1] = null;
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " advances to third. Out number " + Baseball.getOuts()+ ".");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + s.getName() + " got doubled off trying to advance to third! Out number " + Baseball.getOuts()+ ".");
            }
        }
        //Runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].getSpeed() > Baseball.getAwayTeam().getLineup()[to].getArm() || bases[2].getSpeed() > r){
                t = bases[2];
                bases[2] = null;
                Baseball.getHomeTeam().incScore(1);
                Visuals.updateScoreArea();
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " comes around to score! Out number " + Baseball.getOuts()+ ".");
                if(inning >= 9 && Baseball.getHomeTeam().getScore() > Baseball.getAwayTeam().getScore()){
                    Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + Baseball.getAwayTeam().getTeamName() + " " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + " " + Baseball.getHomeTeam().getScore() + "!");
                    Visuals.finalScoreArea();
                    return bases;
                }
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " got doubled off trying to come home and score! Out number " + Baseball.getOuts()+ ".");
            }
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
        return bases;
    }

    //method to handle home time recording an out or multiple outs
    public static void homeOut(Batter[] bases, int to, int inning){
        int roll;
        roll = Math.abs(rand.nextInt() % 5);
        //double play, hit to Pitcher
        if(bases[0] != null && Baseball.getOuts()< 2 && to == 0 && Baseball.getAwayTeam().getAce().getField() > roll){
            bases[0] = null;
            Baseball.incOuts(2);
            Visuals.appendArea("\n" + "Double play! There are now " + Baseball.getOuts()+ " outs.");
            Baseball.getHomeTeam().getLineup()[Baseball.getHomeTeam().getBattingSpot()].incAbs();
        }
        //triple play, hit to infielder
        else if(Baseball.getOuts()== 0 && bases[0] != null && bases[1] != null && to == 0 && Baseball.getAwayTeam().getAce().getField() > roll){
            bases[0] = null;
            bases[1] = null;
            Baseball.incOuts(3);
            Visuals.appendArea("\n" + "Triple play! There are now " + Baseball.getOuts()+ " outs.");
            Baseball.getHomeTeam().getLineup()[Baseball.getHomeTeam().getBattingSpot()].incAbs();
        }
        //double play, hit to infielder
        if(bases[0] != null && Baseball.getOuts()< 2 && to < 5 && Baseball.getAwayTeam().getLineup()[to].getField() > roll){
            bases[0] = null;
            Baseball.incOuts(2);
            Visuals.appendArea("\n" + "Double play! There are now " + Baseball.getOuts()+ " outs.");
            Baseball.getHomeTeam().getLineup()[Baseball.getHomeTeam().getBattingSpot()].incAbs();
        }
        //triple play, hit to infielder
        else if(Baseball.getOuts()== 0 && bases[0] != null && bases[1] != null && to < 5 && Baseball.getAwayTeam().getLineup()[to].getField() > roll){
            bases[0] = null;
            bases[1] = null;
            Baseball.incOuts(3);
            Visuals.appendArea("\n" + "Triple play! There are now " + Baseball.getOuts()+ " outs.");
            Baseball.getHomeTeam().getLineup()[Baseball.getHomeTeam().getBattingSpot()].incAbs();
        }
        //sac fly, hit to outfielder
        else if(Baseball.getOuts()< 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && Baseball.getAwayTeam().getLineup()[to].getField() > roll){
            Baseball.incOuts(1);
            bases = homeSacFly(bases, to, inning);
        }
        //ordinary out
        else{
            Baseball.incOuts(1);
            Visuals.appendArea("\n" + "Out number " + Baseball.getOuts()+ ".");
            Baseball.getHomeTeam().getLineup()[Baseball.getHomeTeam().getBattingSpot()].incAbs();
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
        return;
    }

    //error used to handle errors committed by away Team
    public static Batter[] homeError(Batter[] bases, Batter atBat, int to){
        int roll;
        roll = Math.abs(rand.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = homeSingle(bases, atBat, to);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 2nd base!");
            bases = homeDouble(bases, atBat, to);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " made it to 3rd base!");
            bases = homeTriple(bases, atBat);
        }
        //four-base error
        else{
            Visuals.appendArea("\n" +"Error! " + atBat.getName() + " came all the way around to score!");
            bases = homeHR(bases);
        }

        Baseball.getHomeTeam().incErrors();
        Visuals.adjustRunners(bases);
        return bases;
    }

    //method to handle home Team walking
    public static Batter[] homeWalk(Batter[] bases, Batter atBat){
        //bases loaded
        if(bases[2] != null && bases[1] != null && bases[0] != null){
            Visuals.appendArea("\n" + bases[2].getName() + " has scored from third!");
            bases[2] = bases[1];
            bases[1] = bases[0];
            Baseball.getAwayTeam().incScore(1);
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
        if(Baseball.getInning() >= 9 && Baseball.getHomeTeam().getScore() > Baseball.getAwayTeam().getScore()){
            Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + Baseball.getAwayTeam().getTeamName() + " " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + " " + Baseball.getHomeTeam().getScore() + "!");
            Visuals.finalScoreArea();
            return bases;
        }

        //put Batter on 1st
        bases[0] = atBat;
        Visuals.adjustRunners(bases);
        Visuals.hideBatters();
        return bases;
    }

    //method to handle home Team hitting a single
    public static Batter[] homeSingle(Batter[] bases, Batter atBat, int to){
        int i, r;
        r = rand.nextInt() % 5;

        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            r = rand.nextInt() % 5;
            //Runner on 3rd scores
            if(i == 2 && bases[i] != null){
                Visuals.appendArea("\n" +bases[i].getName() + " has scored from third!");
                bases[i] = null;
                Baseball.getHomeTeam().incScore(1);
                Visuals.updateScoreArea();
            }
            //Runner on 2nd will try to score
            else if(bases[i] != null && r > 2 && i == 1 && bases[i].getSpeed() > 2 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(Baseball.getAwayTeam().getLineup()[to].getArm() > r && Baseball.getAwayTeam().getLineup()[to].getArm() > bases[i].getSpeed()){
                    Baseball.incOuts(1);
                    Visuals.appendArea("\n" +bases[i].getName() + " was thrown out trying to come in to score from second! Out number " + Baseball.getOuts());
                    bases[i] = null;
                }
                //scores
                else{
                    Baseball.getHomeTeam().incScore(1);
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
            if(Baseball.getInning() >= 9 && Baseball.getHomeTeam().getScore() > Baseball.getAwayTeam().getScore()){
                Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + Baseball.getAwayTeam().getTeamName() + " " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + " " + Baseball.getHomeTeam().getScore() + "!");
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

    //method to handle home Team hitting a double
    public static Batter[] homeDouble(Batter[] bases, Batter atBat, int to){
        int j, count = 0, r;
        r = rand.nextInt() % 5;

        //deal with each base in reverse order
        for(j = 2; j >= 0; j--){
            r = rand.nextInt() % 5;
            //Runner on 2nd/3rd score
            if(j >= 1 && bases[j] != null){
                bases[j] = null;
                Baseball.getHomeTeam().incScore(1);
                Visuals.updateScoreArea();
                count++;
            }
            //Runner on 1st will try to score
            else if(bases[j] != null && r > 2 && j == 0 && bases[j].getSpeed() > 3 && to > 4 && to != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(Baseball.getAwayTeam().getLineup()[to].getArm() > r && Baseball.getAwayTeam().getLineup()[to].getArm() > bases[j].getSpeed()){
                    Baseball.incOuts(1);
                    Visuals.appendArea("\n" +bases[j].getName() + " was thrown out trying to come in to score from first! Out number " + Baseball.getOuts());
                    bases[j] = null;
                }
                //scores
                else{
                    Baseball.getHomeTeam().incScore(1);
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
            if(Baseball.getInning() >= 9 && Baseball.getHomeTeam().getScore() > Baseball.getAwayTeam().getScore()){
                Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + Baseball.getAwayTeam().getTeamName() + " " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + " " + Baseball.getHomeTeam().getScore() + "!");
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

    //method to handle home Team hitting a triple
    public static Batter[] homeTriple(Batter[] bases, Batter atBat){
        int j, count = 0;

        //go through each base, any Runner scores
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j] = null;
                Baseball.getHomeTeam().incScore(1);
                Visuals.updateScoreArea();
                Visuals.clearPlayers();
                //walkoff checker
                if(Baseball.getInning() >= 9 && Baseball.getHomeTeam().getScore() > Baseball.getAwayTeam().getScore()){
                    Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + Baseball.getAwayTeam().getTeamName() + " " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + " " + Baseball.getHomeTeam().getScore() + "!");
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

    //method to handle home Team hitting a HR
    public static Batter[] homeHR(Batter[] bases){
        int j, count = 0;

        //any Runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j] = null;
                Baseball.getHomeTeam().incScore(1);
                Visuals.updateScoreArea();
                Visuals.clearPlayers();
                //walkoff check
                if(Baseball.getInning() >= 9 && Baseball.getHomeTeam().getScore() > Baseball.getAwayTeam().getScore()){
                    Visuals.appendArea("\n\n" +"Walkoff! Game over! The final score is " + Baseball.getAwayTeam().getTeamName() + " " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + " " + Baseball.getHomeTeam().getScore() + "!");
                    Visuals.finalScoreArea();
                    return bases;
                }
                count++;
            }
        }
        //add one more run for Batter
        Baseball.getHomeTeam().incScore(1);
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
}
