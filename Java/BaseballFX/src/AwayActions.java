/*Mark Dubin
  6/15/21
  BaseballFX - Away Team Actions Class*/

import java.util.Random;
public class AwayActions {
    
    private static Random rand = new Random();
    
    //stealing base method for away Team
    public static Batter[] awaySteal(Batter[] bases){
        String name;
        //calculations for randomization
        int r = Math.abs(rand.nextInt() % 5), resistance = (Baseball.getHomeTeam().getLineup()[1].getArm() + Baseball.getHomeTeam().getAce().getVelo()) / 2;

        //Runner on 1st and 2nd, no Runner on 3rd
        if(bases[0] != null && bases[1] != null && bases[2] == null){
            Visuals.appendArea("\n" +"Double steal attempt!");
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = bases[0];
                bases[0] = null;
                Visuals.appendArea("\n" +bases[2].getName() + " was able to steal third, and " + bases[1].getName() + " was able to steal second!");
                bases[2].incSbs();
                bases[1].incSbs();
            }
            //thrown out at 3rd
            else{
                name = bases[1].getName();
                bases[1].incCs();
                bases[1] = bases[0];
                bases[0] = null;
                Baseball.incOuts(1);
                if(Baseball.getOuts() >= 3){
                    Visuals.appendArea("\n" + name + " tried to steal third, but was thrown out by the catcher, " + Baseball.getHomeTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts() + ".");

                }
                else{
                    Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + Baseball.getHomeTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts() + ". " + bases[1].getName() + " was able to steal second!");
                    bases[1].incSbs();
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
                bases[1].incSbs();
            }
            //thrown out
            else{
                name = bases[0].getName();
                bases[0].incCs();
                bases[0] = null;
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +name + " tried to steal second, but was thrown out by the catcher, " + Baseball.getHomeTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts() + ".");
            }
        }
        //Runner on 2nd, no Runner on 3rd
        else if(bases[1] != null && bases[2] == null){
            //base stolen
            if(bases[1].getSpeed() > resistance && bases[1].getSpeed() > r){
                bases[2] = bases[1];
                bases[1] = null;
                Visuals.appendArea("\n" +bases[2].getName() + " was able to steal third!");
                bases[2].incSbs();
            }
            //thrown out
            else{
                name = bases[1].getName();
                bases[1].incCs();
                bases[1] = null;
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +name + " tried to steal third, but was thrown out by the catcher, " + Baseball.getHomeTeam().getLineup()[1].getName() + "! Out number " + Baseball.getOuts() + ".");
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

    //method for away Team hitting a sacrifice fly
    public static Batter[] awaySacFly(Batter[] bases, int to, Batter atBat){
        int r = Math.abs(rand.nextInt() % 5);
        Batter s, t;
        //Runners on 2nd and 3rd
        if(bases[1] != null && bases[2] != null){
            s = bases[1];
            t = bases[2];
            bases[2] = bases[1];
            bases[1] = null;
            Baseball.getAwayTeam().incScore(1);
            Visuals.updateScoreArea();
            Visuals.appendArea("\n" +"Batter hit a sac fly. " + t.getName() + " comes around to score, " + s.getName() + " advances to third. Out number " + Baseball.getOuts() + ".");
            t.incRuns();
            atBat.incSfs();
        }
        //Runner on 2nd
        else if(bases[1] != null){
            //advances safely
            if(bases[1].getSpeed() > Baseball.getHomeTeam().getLineup()[to].getArm() || bases[1].getSpeed() > r){
                s = bases[1];
                bases[2] = bases[1];
                bases[1] = null;
                Visuals.appendArea("\n" + "Batter hit a ball to the outfield, caught, " + s.getName() + " advances to third. Out number " + Baseball.getOuts() + ".");
            }
            //thrown out
            else{
                s = bases[1];
                bases[1] = null;
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +"Batter hit a ball to the outfield, caught, but " + s.getName() + " got doubled off trying to advance to third! Out number " + Baseball.getOuts() + ".");
            }
        }
        //Runner on 3rd
        else if(bases[2] != null){
            //advances safely
            if(bases[2].getSpeed() > Baseball.getHomeTeam().getLineup()[to].getArm() || bases[2].getSpeed() > r){
                t = bases[2];
                bases[2] = null;
                Baseball.getAwayTeam().incScore(1);
                Visuals.updateScoreArea();
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " comes around to score! Out number " + Baseball.getOuts() + ".");
                t.incRuns();
                atBat.incSfs();
            }
            //thrown out
            else{
                t = bases[2];
                bases[2] = null;
                Baseball.incOuts(1);
                Visuals.appendArea("\n" +"Batter hit a sac fly, " + t.getName() + " got doubled off trying to come home and score! Out number " + Baseball.getOuts() + ".");
            }
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
        return bases;
    }

    //method to handle away time recording an out or multiple outs
    public static void awayOut(Batter[] bases, Player fielder, int to, Batter atBat){
        int roll;
        roll = Math.abs(rand.nextInt() % 5);
        //double play, hit to Pitcher
        if(bases[0] != null && Baseball.getOuts() < 2 && fielder instanceof Pitcher && ((Pitcher)fielder).getField() > roll){
            String first = bases[0].getName();
            bases[0] = null;
            Baseball.incOuts(2);
            atBat.incGidps();
            Visuals.appendArea("\n" + "A comebacker to the mound, snagged by " + ((Pitcher)fielder).getName() + ", and they're able to double off " + first + " at first! There are now " + Baseball.getOuts() + " outs.");
            atBat.incAbs();
        }
        //triple play hit to Pitcher
        else if(Baseball.getOuts() == 0 && bases[0] != null && bases[1] != null && fielder instanceof Pitcher && ((Pitcher)fielder).getField() > roll){
            bases[0] = null;
            bases[1] = null;
            Baseball.incOuts(3);
            Visuals.appendArea("\n" + "Triple play! There are now " + Baseball.getOuts() + " outs.");
            atBat.incAbs();
        }
        //double play, hit to infielder
        else if(bases[0] != null && Baseball.getOuts() < 2 && to < 5 && Baseball.getHomeTeam().getLineup()[to].getField() > roll){
            bases[0] = null;
            Baseball.incOuts(2);
            atBat.incGidps();
            Visuals.appendArea("\n" + "Double play! There are now " + Baseball.getOuts() + " outs.");
            atBat.incAbs();
        }
        //triple play hit to infielder
        else if(Baseball.getOuts() == 0 && bases[0] != null && bases[1] != null && to < 5 && Baseball.getHomeTeam().getLineup()[to].getField() > roll){
            bases[0] = null;
            bases[1] = null;
            Baseball.incOuts(3);
            Visuals.appendArea("\n" + "Triple play! There are now " + Baseball.getOuts() + " outs.");
            atBat.incAbs();
        }
        //sac fly hit to outfielder
        else if(Baseball.getOuts() < 2 && to > 4 && to < 8 && (bases[1] != null || bases[2] != null) && Baseball.getAwayTeam().getLineup()[to].getField() > roll){
            Baseball.incOuts(1);
            bases = awaySacFly(bases, to, atBat);
        }
        //strikeout
        else if((Baseball.getHomeTeam().getAce().getAcc() * Baseball.getHomeTeam().getAce().getVelo()) / 2 > (atBat.getEye() * atBat.getHit()) / 2 && rand.nextInt() % 2 == 0){
            Baseball.incOuts(1);
            Visuals.appendArea("\n" + Baseball.getHomeTeam().getAce().getName() + " struck him out! Out number " + Baseball.getOuts() + ".");
            Baseball.getHomeTeam().getAce().incStrikeOuts();
            atBat.incStrikeOuts();
            atBat.incAbs();
        }
        //ordinary out
        else{
            Baseball.incOuts(1);
            Visuals.appendArea("\n" + "Out number " + Baseball.getOuts() + ".");
            atBat.incAbs();
        }

        Visuals.hideBatters();
        Visuals.adjustRunners(bases);
        return;
    }

    //method used to handle errors committed by home Team
    public static Batter[] awayError(Batter[] bases, Batter atBat, Batter to){
        int roll;
        roll = Math.abs(rand.nextInt() % 100);
        //one-base error
        if(roll <= 70){
            Visuals.appendArea("\n" + "Error by " + to.getName() + "! " + atBat.getName() + " made it to 1st base!");
            //hit methods used since errors function in exact same way
            bases = awaySingle(bases, atBat, to);
        }
        //two-base error
        else if(roll >= 71 && roll <= 90){
            Visuals.appendArea("\n" + "Error by " + to.getName() + "! " + atBat.getName() + " made it to 2nd base!");
            bases = awayDouble(bases, atBat, to);
        }
        //three-base error
        else if(roll >= 91 && roll <= 97){
            Visuals.appendArea("\n" + "Error by " + to.getName() + "! " + atBat.getName() + " made it to 3rd base!");
            bases = awayTriple(bases, atBat);
        }
        //four-base error
        else{
            Visuals.appendArea("\n" + "Error by " + to.getName() + "! " + atBat.getName() + " came all the way around to score!");
            bases = awayHR(bases, atBat);
        }

        Baseball.getAwayTeam().incErrors();
        Visuals.adjustRunners(bases);
        return bases;
    }

    //method to handle away Team walking
    public static Batter[] awayWalk(Batter[] bases, Batter atBat){
        //bases loaded
        if(bases[2] != null && bases[1] != null && bases[0] != null){
            bases[2].incRuns();
            Visuals.appendArea("\n" + bases[2].getName() + " has scored from third!");
            bases[2] = bases[1];
            bases[1] = bases[0];
            atBat.incRbis();
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

        //put Batter on 1st
        bases[0] = atBat;
        atBat.incWalks();
        Visuals.adjustRunners(bases);
        Visuals.hideBatters();
        return bases;
    }

    //method to handle away Team hitting a single
    public static Batter[] awaySingle(Batter[] bases, Batter atBat, Batter to){
        int i, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse order
        for(i = 2; i >= 0; i--){
            r = rand.nextInt() % 5;
            //Runner on 3rd comes in to score
            if(i == 2 && bases[i] != null){
                Visuals.appendArea("\n" + bases[i].getName() + " has scored from third!");
                bases[i].incRuns();
                bases[i] = null;
                atBat.incRbis();
                Baseball.getAwayTeam().incScore(1);
                Visuals.updateScoreArea();
            }
            //Runner on 2nd will try to score
            else if(bases[i] != null && r > 2 && i == 1 && bases[i].getSpeed() > 2 && to.getPos() > 4 && to.getPos() != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(to.getArm() > r && to.getArm() > bases[i].getSpeed()){
                    Baseball.incOuts(1);
                    Visuals.appendArea("\n" +bases[i].getName() + " was thrown out trying to come in to score from second! Out number " + Baseball.getOuts());
                    bases[i] = null;
                }
                //scores
                else{
                    bases[i].incRuns();
                    atBat.incRbis();
                    Baseball.getAwayTeam().incScore(1);
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

    //method to handle away Team hitting a double
    public static Batter[] awayDouble(Batter[] bases, Batter atBat, Batter to){
        int j, count = 0, r;
        r = rand.nextInt() % 5;
        //deal with each base in reverse
        for(j = 2; j >= 0; j--){
            r = rand.nextInt() % 5;
            //Runner on 2nd or 3rd come in to score
            if(j >= 1 && bases[j] != null){
                bases[j].incRuns();
                bases[j] = null;
                atBat.incRbis();
                Baseball.getAwayTeam().incScore(1);
                Visuals.updateScoreArea();
                count++;
            }
            //Runner on 1st will try to score
            else if(bases[j] != null && r > 2 && j == 0 && bases[j].getSpeed() > 3 && to.getPos() > 4 && to.getPos() != 8){
                r = rand.nextInt() % 5;
                //thrown out
                if(to.getArm() > r && to.getArm() > bases[j].getSpeed()){
                    Baseball.incOuts(1);
                    Visuals.appendArea("\n" + bases[j].getName() + " was thrown out trying to come in to score from first! Out number " + Baseball.getOuts());
                    bases[j] = null;
                }
                //scores
                else{
                    bases[j].incRuns();
                    atBat.incRbis();
                    Baseball.getAwayTeam().incScore(1);
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
        if(count == 3){
            Visuals.appendArea("\nIt's a bases-clearing double!\n" + count + " runs have scored!");
        }
        else if(count > 1){
            Visuals.appendArea("\n" + count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" + count + " run has scored!");
        }

        Visuals.hideBatters();
        return bases;
    }

    //method to handle away Team hitting a triple
    public static Batter[] awayTriple(Batter[] bases, Batter atBat){
        int j, count = 0;
        //go through each base, if any has a Runner they score
        for(j = 2; j >= 0; j--){
            if(bases[j] != null){
                bases[j].incRuns();
                bases[j] = null;
                atBat.incRbis();
                Baseball.getAwayTeam().incScore(1);
                Visuals.updateScoreArea();
                count++;
                Visuals.clearPlayers();
            }
        }
        //move Batter to 3rd
        bases[2] = atBat;

        //grammar check
        if(count == 3){
            Visuals.appendArea("\nIt's a bases-clearing triple!\n" + count + " runs have scored!");
        }
        else if(count > 1){
            Visuals.appendArea("\n" + count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\n" +count + " run has scored!");
        }

        Visuals.adjustRunners(bases);
        Visuals.hideBatters();
        return bases;
    }

    //method to handle away Team hitting a HR
    public static Batter[] awayHR(Batter[] bases, Batter atBat){
        int j, count = 0;
        //any Runner on base scores
        for(j = 0; j < 3; j++){
            if(bases[j] != null){
                bases[j].incRuns();
                bases[j] = null;
                atBat.incRbis();
                Baseball.getAwayTeam().incScore(1);
                Visuals.updateScoreArea();
                count++;
                Visuals.clearPlayers();
            }
        }
        //add one more for the Batter
        atBat.incRuns();
        atBat.incRbis();
        Baseball.getAwayTeam().incScore(1);
        Visuals.updateScoreArea();
        count++;

        //grammar check
        if(count == 4){
            Visuals.appendArea("\nGRAND SLAM!!\n" + count + " runs have scored!");
        }
        else if(count > 1){
            Visuals.appendArea("\nHR!\n" + count + " runs have scored!");
        }
        else if(count > 0){
            Visuals.appendArea("\nHR!\n" + count + " run has scored!");
        }

        Visuals.hideBatters();
        return bases;
    }
}
