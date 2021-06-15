/*Mark Dubin
  6/15/21
  BaseballFX - Visuals Class*/

import javafx.application.Platform;

public class Visuals {

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
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else if(Baseball.getHomeTeam().getTeamName().length() < 3 && Baseball.getAwayTeam().getTeamName().length() >= 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else if(Baseball.getHomeTeam().getTeamName().length() >= 3 && Baseball.getAwayTeam().getTeamName().length() < 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t" + Baseball.getAwayTeam().getScore() + "\t\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors());
            } else{
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t" + Baseball.getAwayTeam().getScore() + "\t\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t" + Baseball.getHomeTeam().getScore() + "\t\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()); 
            }
        } else{
        if(Baseball.getAwayTeam().getTeamName().length() >= 3 && Baseball.getHomeTeam().getTeamName().length() >= 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
        } else if(Baseball.getHomeTeam().getTeamName().length() < 3 && Baseball.getAwayTeam().getTeamName().length() >= 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName() + "\t\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
            } else if(Baseball.getHomeTeam().getTeamName().length() >= 3 && Baseball.getAwayTeam().getTeamName().length() < 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAwayTeam().getTeamName() + "\t\t" + Baseball.getAwayTeam().getScore() + "\t" + Baseball.getAwayTeam().getHits() + "\t" + Baseball.getAwayTeam().getErrors() + "\n" +  Baseball.getHomeTeam().getTeamName().substring(0, 3) + "\t" + Baseball.getHomeTeam().getScore() + "\t" + Baseball.getHomeTeam().getHits() + "\t" + Baseball.getHomeTeam().getErrors()));
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
        Baseball.getThirdRunner().setVisible(false);
        Baseball.getSecondRunner().setVisible(false);
        Baseball.getFirstRunner().setVisible(false);
        Baseball.getLhb().setVisible(false);
        Baseball.getRhb().setVisible(false);
        Baseball.getAwayOnDeck().setVisible(false);
        Baseball.getHomeOnDeck().setVisible(false);
    }

    public static void hideBatters(){
        Baseball.getLhb().setVisible(false);
        Baseball.getRhb().setVisible(false);
        Baseball.getAwayOnDeck().setVisible(false);
        Baseball.getHomeOnDeck().setVisible(false);
    }

    public static void handedness(Batter atBat){
        if(atBat.getBHand() == 'R'){
            Baseball.getLhb().setVisible(false);
            Baseball.getRhb().setVisible(true);
        } else{
            Baseball.getLhb().setVisible(true);
            Baseball.getRhb().setVisible(false);
        }
        if(Baseball.getIsTop()){
            Baseball.getAwayOnDeck().setVisible(true);
        } else{
            Baseball.getHomeOnDeck().setVisible(true);
        }
    }

    public static void adjustRunners(Batter[] bases){
        if(bases[0] != null){
            Baseball.getFirstRunner().setVisible(true);
        } else{
            Baseball.getFirstRunner().setVisible(false);
        }
        if(bases[1] != null){
            Baseball.getSecondRunner().setVisible(true);
        } else{
            Baseball.getSecondRunner().setVisible(false);
        }
        if(bases[2] != null){
            Baseball.getThirdRunner().setVisible(true);
        } else{
            Baseball.getThirdRunner().setVisible(false);
        }
    }
}
