/*Mark Dubin
  6/15/21
  BaseballFX - Visuals Class*/

import javafx.application.Platform;

public class Visuals {

    public static boolean strCheck(String word){
        int i;
        //if a single space is entered, or nothing is entered, return false
        if(word.length() == 0 || word.codePointAt(0) == 32 || (word.length() == 1 && word.codePointAt(0) == 32)){
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

    public static void appendArea(String msg){
        if (Platform.isFxApplicationThread()) {
            Baseball.getOutputTextArea().appendText(msg);
        } else {
            Platform.runLater(() -> Baseball.getOutputTextArea().appendText(msg));
        }
    }

    public static void updateScoreArea(){
        if (Platform.isFxApplicationThread()) {
            if(Baseball.getIsTop()){
                Baseball.getScoreArea().setText("Inning:\tTop " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore());
            } else{
                Baseball.getScoreArea().setText("Inning:\tBottom " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t"+ Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore()); 
            }
        } else {
            if(Baseball.getIsTop()){
                Platform.runLater(() -> Baseball.getScoreArea().setText("Inning:\tTop " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore()));
            } else{
                Platform.runLater(() -> Baseball.getScoreArea().setText("Inning:\tBottom " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore()));
            }
        }
    }

    public static void finalScoreArea(){
        if(Platform.isFxApplicationThread()){
            if(Baseball.getAwayTeam().getTeamName().length() >= 3 && Baseball.getHomeTeam().getTeamName().length() >= 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else if(Baseball.getHomeTeam().getTeamName().length() < 3 && Baseball.getAwayTeam().getTeamName().length() >= 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else if(Baseball.getHomeTeam().getTeamName().length() >= 3 && Baseball.getAwayTeam().getTeamName().length() < 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t" + Baseball.getAwayTeam().getScore() + "\t\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else{
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t" + Baseball.getAwayTeam().getScore() + "\t\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()); 
            }
        } else{
        if(Baseball.getAwayTeam().getTeamName().length() >= 3 && Baseball.getHomeTeam().getTeamName().length() >= 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
        } else if(Baseball.getHomeTeam().getTeamName().length() < 3 && Baseball.getAwayTeam().getTeamName().length() >= 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
            } else if(Baseball.getHomeTeam().getTeamName().length() >= 3 && Baseball.getAwayTeam().getTeamName().length() < 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
            } else{
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors())); 
            }
        }
    }

    public static void updateAwayArea(){
        if(Platform.isFxApplicationThread()){
            Baseball.getAwayArea().setText(Baseball.getAwayTeam().getTeamName() + " Lineup: ");
            for(Batter bat : Baseball.getAwayTeam().getLineup()){
                Baseball.getAwayArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            Baseball.getAwayArea().appendText("\n" + "P: " + Baseball.getAwayTeam().getAce().getName());
        } else{
            Platform.runLater(() -> Baseball.getAwayArea().setText(Baseball.getAwayTeam().getTeamName() + " Lineup: "));
            for(Batter bat : Baseball.getAwayTeam().getLineup()){
                Platform.runLater(() -> Baseball.getAwayArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                Baseball.getAwayArea().appendText("\n" + "P: " + Baseball.getAwayTeam().getAce().getName());
            });
        }
    }

    public static void updateHomeArea(){
        if(Platform.isFxApplicationThread()){
            Baseball.getHomeArea().setText(Baseball.getHomeTeam().getTeamName() + " Lineup: ");
            for(Batter bat : Baseball.getAwayTeam().getLineup()){
                Baseball.getHomeArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            Baseball.getHomeArea().appendText("\n" + "P: " + Baseball.getHomeTeam().getAce().getName());
        } else{
            Platform.runLater(() -> Baseball.getHomeArea().setText(Baseball.getHomeTeam().getTeamName() + " Lineup: "));
            for(Batter bat : Baseball.getHomeTeam().getLineup()){
                Platform.runLater(() -> Baseball.getHomeArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                Baseball.getHomeArea().appendText("\n" + "P: " + Baseball.getHomeTeam().getAce().getName());
            });
        }
    }

    public static void clearPlayers(){
        App.getThirdRunner().setVisible(false);
        App.getSecondRunner().setVisible(false);
        App.getFirstRunner().setVisible(false);
        App.getLhb().setVisible(false);
        App.getRhb().setVisible(false);
        App.getAwayOnDeck().setVisible(false);
        App.getHomeOnDeck().setVisible(false);
    }

    public static void hideBatters(){
        App.getLhb().setVisible(false);
        App.getRhb().setVisible(false);
        App.getAwayOnDeck().setVisible(false);
        App.getHomeOnDeck().setVisible(false);
    }

    public static void handedness(Batter atBat){
        if(atBat.getBHand() == 'R'){
            App.getLhb().setVisible(false);
            App.getRhb().setVisible(true);
        } else{
            App.getLhb().setVisible(true);
            App.getRhb().setVisible(false);
        }
        if(Baseball.getIsTop()){
            App.getAwayOnDeck().setVisible(true);
        } else{
            App.getHomeOnDeck().setVisible(true);
        }
    }

    public static void adjustRunners(Batter[] bases){
        if(bases[0] != null){
            App.getFirstRunner().setVisible(true);
        } else{
            App.getFirstRunner().setVisible(false);
        }
        if(bases[1] != null){
            App.getSecondRunner().setVisible(true);
        } else{
            App.getSecondRunner().setVisible(false);
        }
        if(bases[2] != null){
            App.getThirdRunner().setVisible(true);
        } else{
            App.getThirdRunner().setVisible(false);
        }
    }
}
