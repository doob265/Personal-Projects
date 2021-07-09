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
            App.getOutputTextArea().appendText(msg);
        } else {
            Platform.runLater(() -> App.getOutputTextArea().appendText(msg));
        }
    }

    public static void updateScoreArea(){
        if (Platform.isFxApplicationThread()) {
            if(Baseball.getIsTop()){
                App.getScoreArea().setText("Inning:\tTop " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore());
            } else{
                App.getScoreArea().setText("Inning:\tBottom " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t"+ Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore()); 
            }
        } else {
            if(Baseball.getIsTop()){
                Platform.runLater(() -> App.getScoreArea().setText("Inning:\tTop " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore()));
            } else{
                Platform.runLater(() -> App.getScoreArea().setText("Inning:\tBottom " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAwayTeam().getTeamName() + ": " + Baseball.getAwayTeam().getScore() + ", " + Baseball.getHomeTeam().getTeamName() + ": " + Baseball.getHomeTeam().getScore()));
            }
        }
    }

    public static void finalScoreArea(){
        if(Platform.isFxApplicationThread()){
            if(Baseball.getAwayTeam().getTeamName().length() >= 3 && Baseball.getHomeTeam().getTeamName().length() >= 3){
            App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else if(Baseball.getHomeTeam().getTeamName().length() < 3 && Baseball.getAwayTeam().getTeamName().length() >= 3){
            App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else if(Baseball.getHomeTeam().getTeamName().length() >= 3 && Baseball.getAwayTeam().getTeamName().length() < 3){
            App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t" + Baseball.getAwayTeam().getScore() + "\t\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else{
                App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t" + Baseball.getAwayTeam().getScore() + "\t\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()); 
            }
        } else{
        if(Baseball.getAwayTeam().getTeamName().length() >= 3 && Baseball.getHomeTeam().getTeamName().length() >= 3){
            Platform.runLater(() -> App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
        } else if(Baseball.getHomeTeam().getTeamName().length() < 3 && Baseball.getAwayTeam().getTeamName().length() >= 3){
            Platform.runLater(() -> App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
            } else if(Baseball.getHomeTeam().getTeamName().length() >= 3 && Baseball.getAwayTeam().getTeamName().length() < 3){
            Platform.runLater(() -> App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
            } else{
            Platform.runLater(() -> App.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors())); 
            }
        }
    }

    public static void updateAwayArea(){
        if(Platform.isFxApplicationThread()){
            App.getAwayTextArea().setText(Baseball.getAwayTeam().getTeamName() + " Lineup: ");
            for(Batter bat : Baseball.getAwayTeam().getLineup()){
                App.getAwayTextArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            App.getAwayTextArea().appendText("\n" + "P: " + Baseball.getAwayTeam().getAce().getName());
        } else{
            Platform.runLater(() -> App.getAwayTextArea().setText(Baseball.getAwayTeam().getTeamName() + " Lineup: "));
            for(Batter bat : Baseball.getAwayTeam().getLineup()){
                Platform.runLater(() -> App.getAwayTextArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                App.getAwayTextArea().appendText("\n" + "P: " + Baseball.getAwayTeam().getAce().getName());
            });
        }
    }

    public static void updateHomeArea(){
        if(Platform.isFxApplicationThread()){
            App.getHomeTextArea().setText(Baseball.getHomeTeam().getTeamName() + " Lineup: ");
            for(Batter bat : Baseball.getAwayTeam().getLineup()){
                App.getHomeTextArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            App.getHomeTextArea().appendText("\n" + "P: " + Baseball.getHomeTeam().getAce().getName());
        } else{
            Platform.runLater(() -> App.getHomeTextArea().setText(Baseball.getHomeTeam().getTeamName() + " Lineup: "));
            for(Batter bat : Baseball.getHomeTeam().getLineup()){
                Platform.runLater(() -> App.getHomeTextArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                App.getHomeTextArea().appendText("\n" + "P: " + Baseball.getHomeTeam().getAce().getName());
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
