package com.example.applauncher;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LauncherBehavior implements Initializable {

    private int WIDTH = 600;
    private int HEIGHT = 600;
    private final int BUTTON_WIDTH = 200;

    private final int appCount = 4;

    @FXML
    private ImageView apps;
    @FXML
    private ImageView apps2;
    @FXML
    private ImageView apps3;
    @FXML
    private ImageView apps4;
    @FXML
    private HBox allApps;
    @FXML
    private HBox main;
    private ArrayList<Image> sources;
    private int sourceIndex = 0;
    @FXML
    private VBox left;
    @FXML
    private VBox right;
    @FXML
    private ScrollPane scroller;

    private double appWidth;
    private double appSpacing;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        appWidth = screenBounds.getWidth() - (BUTTON_WIDTH * 2);
        appSpacing = (appWidth/2) - (WIDTH/2);

        HBox.setHgrow(allApps, Priority.ALWAYS);
        HBox.setHgrow(main, Priority.ALWAYS);

        sources = new ArrayList<>();

        File f = new File("src/main/resources/images/game.jpg");
        Image image = new Image(f.toURI().toString());

        File f2 = new File("src/main/resources/images/game2.png");
        Image image2 = new Image(f2.toURI().toString());

        sources.add(image);
        sources.add(image2);

        apps.setImage(sources.get(0));
        apps2.setImage(sources.get(1));
        apps3.setImage(sources.get(0));
        apps4.setImage(sources.get(1));

        apps.setFitWidth(WIDTH);
        apps.setFitHeight(HEIGHT);

        apps2.setFitWidth(WIDTH);
        apps2.setFitHeight(HEIGHT);

        apps3.setFitWidth(WIDTH);
        apps3.setFitHeight(HEIGHT);

        apps4.setFitWidth(WIDTH);
        apps4.setFitHeight(HEIGHT);

        VBox.setVgrow(left,Priority.SOMETIMES);
        VBox.setVgrow(right,Priority.SOMETIMES);
        left.setMinWidth(BUTTON_WIDTH);
        right.setMinWidth(BUTTON_WIDTH);

        allApps.setSpacing(appSpacing-(WIDTH/2));
        allApps.setPadding(new Insets(0,appSpacing,0,appSpacing));
    }

    @FXML
    public void onImageClick(MouseEvent event){

        Node node = (Node) event.getSource() ;
        String data = (String) node.getUserData();
        int dir = Integer.parseInt(data);

        animateToScrollPos(scroller,sourceIndex);

        sourceIndex += dir;

        if (sourceIndex >= appCount){
            sourceIndex = 0;
        }else if (sourceIndex < 0){
            sourceIndex = appCount - 1;
        }
    }


    private void animateToScrollPos(ScrollPane pane,int app){
        //calculate to from percentage
        // pos(from-to) / (Get total width of scrollable)
        // set the T value of KeyValue in animation.

        double appsWidth =  appCount * (WIDTH); //Total number of width of app
        double spacingWidth =  (appCount-1) * (appSpacing-(WIDTH/2)); // Total number of spacing
        double leftRightPadding = (appSpacing*2); // Total left and right padding.
        double totalWidth = appsWidth + spacingWidth + leftRightPadding;
        totalWidth -= (appSpacing + (WIDTH*1.5) + (appSpacing-(WIDTH/2))); // Adjust

        double ratio = (appSpacing+(WIDTH/2))/totalWidth;

        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(0.25),
                        new KeyValue(pane.hvalueProperty(), ratio*app)));
        animation.play();
    }
}