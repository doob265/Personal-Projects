import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

public class App extends Application {
    String awayName;
    String homeName;

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

    @Override public void start(Stage stage) throws InterruptedException{

        Group root = new Group();
        Text text = new Text(10, 40, "BaseballFX");
        text.setFont(new Font(40));
        Scene scene = new Scene(root, 1000, 800);
        stage.setResizable(false);

        Button button = new Button("Start");

        Text invalid = new Text(10, 40, "Invalid Name. Only enter letters.");
        invalid.setStyle("-fx-font-color: red");

        stage.setTitle("BaseballFX"); 
        stage.setScene(scene); 
        stage.sizeToScene(); 
        stage.show(); 

        VBox pane = new VBox(10);
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        root.getChildren().addAll(pane);
        pane.getChildren().addAll(text, button);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: cyan;");

        TextArea textArea = new TextArea();
        textArea.setMaxWidth(150);
        textArea.setMaxHeight(5);

        TextArea homeArea = new TextArea();
        homeArea.setEditable(false);
        homeArea.setMaxWidth(175);
        homeArea.setMaxHeight(210);
        homeArea.setStyle("text-area-background: green;");

        TextArea awayArea = new TextArea();
        awayArea.setEditable(false);
        awayArea.setMaxWidth(175);
        awayArea.setMaxHeight(210);
        awayArea.setStyle("text-area-background: green;");

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-image: url(\"diamond.png\");" + "-fx-background-size: contain;");
        borderPane.prefWidthProperty().bind(stage.widthProperty());
        borderPane.prefHeightProperty().bind(stage.heightProperty());

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(100);
        outputArea.setPrefColumnCount(2);

        TextArea bottomArea = new TextArea();
        bottomArea.setEditable(false);
        bottomArea.setPrefHeight(90);

        ImageView pitcher = new ImageView("stick_figure.png");
        pitcher.setX(490);
        pitcher.setY(430);
        pitcher.setFitWidth(30);
        pitcher.setFitHeight(60);

        ImageView firstBase = new ImageView("stick_figure.png");
        firstBase.setX(670);
        firstBase.setY(360);
        firstBase.setFitWidth(30);
        firstBase.setFitHeight(60);

        ImageView secondBase = new ImageView("stick_figure.png");
        secondBase.setX(580);
        secondBase.setY(230);
        secondBase.setFitWidth(30);
        secondBase.setFitHeight(60);

        ImageView thirdBase = new ImageView("stick_figure.png");
        thirdBase.setX(320);
        thirdBase.setY(360);
        thirdBase.setFitWidth(30);
        thirdBase.setFitHeight(60);

        ImageView shortStop = new ImageView("stick_figure.png");
        shortStop.setX(400);
        shortStop.setY(230);
        shortStop.setFitWidth(30);
        shortStop.setFitHeight(60);

        ImageView leftField = new ImageView("stick_figure.png");
        leftField.setX(250);
        leftField.setY(180);
        leftField.setFitWidth(30);
        leftField.setFitHeight(60);

        ImageView centerField = new ImageView("stick_figure.png");
        centerField.setX(490);
        centerField.setY(100);
        centerField.setFitWidth(30);
        centerField.setFitHeight(60);

        ImageView rightField = new ImageView("stick_figure.png");
        rightField.setX(730);
        rightField.setY(180);
        rightField.setFitWidth(30);
        rightField.setFitHeight(60);

        ImageView lhb = new ImageView("stick_figure.png");
        lhb.setX(520);
        lhb.setY(630);
        lhb.setFitWidth(30);
        lhb.setFitHeight(60);
        lhb.setVisible(false);

        ImageView rhb = new ImageView("stick_figure.png");
        rhb.setX(456);
        rhb.setY(630);
        rhb.setFitWidth(30);
        rhb.setFitHeight(60);
        rhb.setVisible(false);

        ImageView firstRunner = new ImageView("stick_figure.png");
        firstRunner.setX(670);
        firstRunner.setY(430);
        firstRunner.setFitWidth(30);
        firstRunner.setFitHeight(60);
        firstRunner.setVisible(false);

        ImageView secondRunner = new ImageView("stick_figure.png");
        secondRunner.setX(490);
        secondRunner.setY(250);
        secondRunner.setFitWidth(30);
        secondRunner.setFitHeight(60);
        secondRunner.setVisible(false);

        ImageView thirdRunner = new ImageView("stick_figure.png");
        thirdRunner.setX(310);
        thirdRunner.setY(430);
        thirdRunner.setFitWidth(30);
        thirdRunner.setFitHeight(60);
        thirdRunner.setVisible(false);

        ImageView homeOnDeck = new ImageView("stick_figure.png");
        homeOnDeck.setX(650);
        homeOnDeck.setY(650);
        homeOnDeck.setFitWidth(30);
        homeOnDeck.setFitHeight(60);
        homeOnDeck.setVisible(false);

        ImageView awayOnDeck = new ImageView("stick_figure.png");
        awayOnDeck.setX(327);
        awayOnDeck.setY(650);
        awayOnDeck.setFitWidth(30);
        awayOnDeck.setFitHeight(60);
        awayOnDeck.setVisible(false);

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(button.getText().equals("Start")){
                    text.setText("Please enter the team name for the away team.");
                    button.setText("Next");
                    pane.getChildren().remove(button);
                    pane.getChildren().add(textArea);
                    pane.getChildren().add(button);
                } else if(button.getText().equals("Next")){
                    if(strCheck(textArea.getText().toLowerCase())){
                    text.setText("Please enter the team name for the home team.");
                    button.setText("Play");
                    awayName = textArea.getText();
                    textArea.setText("");
                    pane.getChildren().remove(invalid);
                    } else if(!pane.getChildren().contains(invalid)){
                        pane.getChildren().add(invalid);
                    }
                } else if(button.getText().equals("Play")){
                    if(strCheck(textArea.getText().toLowerCase())){
                        homeName = textArea.getText();
                        pane.getChildren().removeAll(pane.getChildren());
                        root.getChildren().remove(pane);
                        root.getChildren().add(borderPane);

                        // create new baseball instance passing in team names
                        baseball b = new baseball(homeName, awayName, outputArea, bottomArea, awayArea, homeArea, lhb, rhb, firstRunner, secondRunner, thirdRunner, awayOnDeck, homeOnDeck);
                        team home = b.getHome();
                        batter[] homeLineup = home.getLineup();
                        homeArea.setText(home.getTeamName()+ " Lineup: ");

                        team away = b.getAway();
                        batter[] awayLineup = away.getLineup();
                        awayArea.setText(away.getTeamName() + " Lineup: ");

                        pane.setAlignment(Pos.TOP_CENTER);

                        borderPane.setLeft(awayArea);
                        borderPane.setRight(homeArea);
                        borderPane.setBottom(bottomArea);
                        borderPane.setTop(outputArea);

                        root.getChildren().addAll(pitcher, lhb, rhb, firstRunner, secondRunner, thirdRunner, firstBase, secondBase, thirdBase, shortStop, leftField, centerField, rightField, awayOnDeck, homeOnDeck);

                        for(batter bat : homeLineup){
                            Platform.runLater(()->{
                                homeArea.appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
                            });
                        }
    
                        Platform.runLater(()->{
                            homeArea.appendText("\n" + "P: " + home.getAce().getName());
                        });
    
                        for(batter bat : awayLineup){
                            Platform.runLater(()->{
                                awayArea.appendText("\n" + (bat.getPos() - 1) + ". " + bat.getName() + " (" + bat.getBHand() + ") " + bat.getHits() + " - " + bat.getAbs());
                            });
                        }
    
                        Platform.runLater(()->{
                            awayArea.appendText("\n" + "P: " + away.getAce().getName());
                        });

                        bottomArea.setText("Inning:\tTop 1\tOuts:\t0\nScore:\t" + awayName + ": " + away.getScore() + ", " + homeName + ": " + home.getScore());

                        Thread thread = new Thread(() ->{
                            b.playBall();
                        });
                        thread.start();

                    } else if(!pane.getChildren().contains(invalid)){
                        pane.getChildren().add(invalid);
                    }
                } 
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}