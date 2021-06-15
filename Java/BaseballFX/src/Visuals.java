import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

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
                Baseball.getScoreArea().setText("Inning:\tTop " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAway().getTeamName() + ": " + Baseball.getAway().getScore() + ", " + Baseball.getHome().getTeamName() + ": " + Baseball.getHome().getScore());
            } else{
                Baseball.getScoreArea().setText("Inning:\tBottom " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t"+ Baseball.getAway().getTeamName() + ": " + Baseball.getAway().getScore() + ", " + Baseball.getHome().getTeamName() + ": " + Baseball.getHome().getScore()); 
            }
        } else {
            if(Baseball.getIsTop()){
                Platform.runLater(() -> Baseball.getScoreArea().setText("Inning:\tTop " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAway().getTeamName() + ": " + Baseball.getAway().getScore() + ", " + Baseball.getHome().getTeamName() + ": " + Baseball.getHome().getScore()));
            } else{
                Platform.runLater(() -> Baseball.getScoreArea().setText("Inning:\tBottom " + Baseball.getInning() + "\tOuts:\t" + Baseball.getOuts() + "\nScore:\t" + Baseball.getAway().getTeamName() + ": " + Baseball.getAway().getScore() + ", " + Baseball.getHome().getTeamName() + ": " + Baseball.getHome().getScore()));
            }
        }
    }

    public static void finalScoreArea(){
        if(Platform.isFxApplicationThread()){
            if(Baseball.getAway().getTeamName().length() >= 3 && Baseball.getHome().getTeamName().length() >= 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAway().getScore() + "\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors());
            } else if(Baseball.getHome().getTeamName().length() < 3 && Baseball.getAway().getTeamName().length() >= 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAway().getScore() + "\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName() + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors());
            } else if(Baseball.getHome().getTeamName().length() >= 3 && Baseball.getAway().getTeamName().length() < 3){
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName() + "\t" + Baseball.getAway().getScore() + "\t\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors());
            } else{
            Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName() + "\t" + Baseball.getAway().getScore() + "\t\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName() + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors()); 
            }
        } else{
        if(Baseball.getAway().getTeamName().length() >= 3 && Baseball.getHome().getTeamName().length() >= 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAway().getScore() + "\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors()));
        } else if(Baseball.getHome().getTeamName().length() < 3 && Baseball.getAway().getTeamName().length() >= 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName().substring(0, 3) + "\t\t" + Baseball.getAway().getScore() + "\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName() + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors()));
            } else if(Baseball.getHome().getTeamName().length() >= 3 && Baseball.getAway().getTeamName().length() < 3){
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName() + "\t\t" + Baseball.getAway().getScore() + "\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName().substring(0, 3) + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors()));
            } else{
            Platform.runLater(() -> Baseball.getScoreArea().setText("\t\tR\tH\tE\n" + Baseball.getAway().getTeamName() + "\t\t" + Baseball.getAway().getScore() + "\t" + Baseball.getAway().getHits() + "\t" + Baseball.getAway().getErrors() + "\n" +  Baseball.getHome().getTeamName() + "\t\t" + Baseball.getHome().getScore() + "\t" + Baseball.getHome().getHits() + "\t" + Baseball.getHome().getErrors())); 
            }
        }
    }

    public static void updateAwayArea(){
        if(Platform.isFxApplicationThread()){
            Baseball.getAwayArea().setText(Baseball.getAway().getTeamName() + " Lineup: ");
            for(Batter bat : Baseball.getAway().getLineup()){
                Baseball.getAwayArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            Baseball.getAwayArea().appendText("\n" + "P: " + Baseball.getAway().getAce().getName());
        } else{
            Platform.runLater(() -> Baseball.getAwayArea().setText(Baseball.getAway().getTeamName() + " Lineup: "));
            for(Batter bat : Baseball.getAway().getLineup()){
                Platform.runLater(() -> Baseball.getAwayArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                Baseball.getAwayArea().appendText("\n" + "P: " + Baseball.getAway().getAce().getName());
            });
        }
    }

    public static void updateHomeArea(){
        if(Platform.isFxApplicationThread()){
            Baseball.getHomeArea().setText(Baseball.getHome().getTeamName() + " Lineup: ");
            for(Batter bat : Baseball.getAway().getLineup()){
                Baseball.getHomeArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
            }
            Baseball.getHomeArea().appendText("\n" + "P: " + Baseball.getHome().getAce().getName());
        } else{
            Platform.runLater(() -> Baseball.getHomeArea().setText(Baseball.getHome().getTeamName() + " Lineup: "));
            for(Batter bat : Baseball.getHome().getLineup()){
                Platform.runLater(() -> Baseball.getHomeArea().appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs()));
            }
            Platform.runLater(()->{
                Baseball.getHomeArea().appendText("\n" + "P: " + Baseball.getHome().getAce().getName());
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
