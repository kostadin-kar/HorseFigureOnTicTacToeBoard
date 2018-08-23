package app.engine;

import app.animation.PathAnimator;
import app.buttons.CustomButton;
import app.cell_object.Cell;
import app.contracts.IEngine;
import app.movement_logic.PathCalculator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Engine extends Application implements EventHandler<ActionEvent>, IEngine {

    private static final int MAX_BUTTONS = 9;
    private static final String MEDIA1 = "src\\app\\audio_files\\Horse_neigh1.wav";
    private static final String MEDIA2 = "src\\app\\audio_files\\Horse_neigh2.wav";
    private static final String CSS_PATH = "..\\buttons\\CustomCSSButtons.css";

    private Image unselected;
    private Image horseSelected;
    private Image destinationSelected;

    private Scene scene;
    private GridPane gridPane;
    private CustomButton[] buttonsHorse;
    private CustomButton[] buttonsDest;
    private Button setHorse;
    private Button setDestination;
    private Button calculate;
    private TextArea textArea;
    private TextArea coordinatesArea;
    private StackPane stackPane;

    private int optionToDrawImageOnButton = 0;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private boolean isHorseSet;
    private boolean isDestSet;

    private void addCursorEffect(Button button) {
        DropShadow shadow = new DropShadow();
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> button.setEffect(shadow));
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> button.setEffect(null));
    }

    private void vBoxInitializationMethod() {
        VBox vbButtons = new VBox();
        vbButtons.setSpacing(20);
        vbButtons.setPadding(new Insets(10, 20, 10, 20));
        vbButtons.getChildren().addAll(textArea, setHorse, setDestination, calculate, coordinatesArea);
        gridPane.add(vbButtons, 1, 0);
    }

    private void textAreasInitializationMethod() {
        textArea = new TextArea();
        textArea.setPrefRowCount(6);
        textArea.setPrefColumnCount(4);
        textArea.setDisable(true);
        textArea.setOpacity(1);
        textArea.setStyle("-fx-control-inner-background: #7DB9B3; -fx-text-fill: #000000; -fx-highlight-text-fill: #000000; -fx-highlight-fill: #000000;");
        coordinatesArea = new TextArea();
        coordinatesArea.setPrefRowCount(6);
        coordinatesArea.setPrefColumnCount(4);
        coordinatesArea.setDisable(true);
        coordinatesArea.setOpacity(1);
        coordinatesArea.setStyle("-fx-control-inner-background: #34495E; -fx-text-fill: #FDFEFE; -fx-highlight-text-fill: #FDFEFE; -fx-highlight-fill: #FDFEFE; -fx-font-size: 12px;");
    }

    private void buttonsOnTheRightInitializationMethod() {
        setHorse = new Button("Set Horse");
        setDestination = new Button("Set Destination");
        calculate = new Button("Calculate");
        addCursorEffect(setHorse);
        addCursorEffect(setDestination);
        addCursorEffect(calculate);
        setHorse.setOnAction(this);
        setDestination.setOnAction(this);
        calculate.setOnAction(this);
        setHorse.setMaxWidth(Double.MAX_VALUE);
        setDestination.setMaxWidth(Double.MAX_VALUE);
        calculate.setMaxWidth(Double.MAX_VALUE);

        unselected = new Image("http://icons.iconarchive.com/icons/kyo-tux/aeon/128/Filetype-Blank-icon.png");
        horseSelected = new Image("http://icons.iconarchive.com/icons/artua/mac/128/Chess-icon.png");
        destinationSelected = new Image("http://icons.iconarchive.com/icons/everaldo/crystal-clear/128/Action-button-stop-icon.png");
    }

    private void panesInitializationMethod() {
        GridPane horseGrid = new GridPane();
        horseGrid.setId("horse");
        GridPane destinationGrid = new GridPane();
        destinationGrid.setId("destination");

        int buttonIndex = 0;
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int colIndex = 0; colIndex < 3; colIndex++) {
                horseGrid.add(buttonsHorse[buttonIndex], colIndex, rowIndex);
                destinationGrid.add(buttonsDest[buttonIndex], colIndex, rowIndex);
                buttonIndex++;
            }
        }

        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        stackPane = new StackPane();
        stackPane.getChildren().addAll(destinationGrid, horseGrid);
        gridPane.add(stackPane, 0, 0);
    }

    private void buttonInitializationMethod() {
        buttonsHorse = new CustomButton[MAX_BUTTONS];
        ToggleGroup horseGroup = new ToggleGroup();
        buttonsDest = new CustomButton[MAX_BUTTONS];
        ToggleGroup destinationGroup = new ToggleGroup();

        for (int i = 0; i < MAX_BUTTONS; i++) {
            buttonsHorse[i] = new CustomButton();
            buttonsDest[i] = new CustomButton();
            buttonsHorse[i].getStylesheets().add(this.getClass()
                    .getResource(CSS_PATH)
                    .toExternalForm());
            buttonsDest[i].getStylesheets().add(this.getClass()
                    .getResource(CSS_PATH)
                    .toExternalForm());

            buttonsHorse[i].setPrefSize(128, 128);
            buttonsDest[i].setPrefSize(128, 128);
            buttonsHorse[i].setOnAction(this);
            buttonsDest[i].setOnAction(this);
            buttonsHorse[i].setToggleGroup(horseGroup);
            buttonsDest[i].setToggleGroup(destinationGroup);

            buttonsHorse[i].setOpacity(0.2);
            buttonsDest[i].setOpacity(0.2);
        }
    }

    private void setHorseMethod() {
        textArea.setStyle("-fx-control-inner-background: #7DB9B3;");
        scene.setCursor(new ImageCursor(new Image("http://icons.iconarchive.com/icons/artua/mac/32/Chess-icon.png")));
        textArea.setText("Setting Horse");
        textArea.appendText("\nposition...");

        if (stackPane.getChildren().get(1).getId().equals("destination")) {
            Node topNode = stackPane.getChildren().get(1);
            topNode.toBack();
        }

        optionToDrawImageOnButton = 1;

        if (fromX != -1 && fromY != -1) {
            buttonsHorse[fromX * 3 + fromY].setOpacity(0.2);
            buttonsHorse[fromX * 3 + fromY].setGraphic(new ImageView(unselected));
            buttonsHorse[fromX * 3 + fromY].setHasBeenClicked(false);
            isHorseSet = false;
            fromX = -1;
            fromY = -1;
        }
    }

    private void setDestinationMethod() {
        textArea.setStyle("-fx-control-inner-background: #7DB9B3;");
        scene.setCursor(new ImageCursor(new Image("http://icons.iconarchive.com/icons/visualpharm/must-have/32/Stock-Index-Down-icon.png")));
        textArea.setText("Setting");
        textArea.appendText("\nDestination...");

        if (stackPane.getChildren().get(1).getId().equals("horse")) {
            Node topNode = stackPane.getChildren().get(1);
            topNode.toBack();
        }

        optionToDrawImageOnButton = 2;

        if (toX != -1 && toY != -1) {
            buttonsDest[toX * 3 + toY].setOpacity(0.2);
            buttonsDest[toX * 3 + toY].setGraphic(new ImageView(unselected));
            buttonsDest[toX * 3 + toY].setHasBeenClicked(false);
            isDestSet = false;
            toX = -1;
            toY = -1;
        }
    }

    private void calculateMethod() {
        textArea.setStyle("-fx-control-inner-background: #7DB9B3;");
        if (!isHorseSet || !isDestSet || fromX == -1 || fromY == -1 || toX == -1 || toY == -1) {
            textArea.setStyle("-fx-control-inner-background: #E53E3E");
            textArea.setText("Missing\nelement...");
            scene.setCursor(Cursor.DEFAULT);
            optionToDrawImageOnButton = 0;
            return;
        }

        if (fromX == toX && fromY == toY) {
            textArea.setText("Horse\nposition and\ndestination\ncoincide");
            coordinatesArea.setText("["+fromX+","+fromY+"]");
            optionToDrawImageOnButton = 0;
            scene.setCursor(Cursor.DEFAULT);
            return;
        }

        setHorse.setDisable(true);
        setDestination.setDisable(true);
        calculate.setDisable(true);

        textArea.setText("Calculation\nstarted...");
        scene.setCursor(Cursor.WAIT);

        if (stackPane.getChildren().get(1).getId().equals("destination")) {
            Node topNode = stackPane.getChildren().get(1);
            topNode.toBack();
        }

        List<Cell> coordinates = PathCalculator.calculatePath(fromX, fromY, toX, toY);
        if (coordinates == null) {
            textArea.setStyle("-fx-control-inner-background: #E53E3E");
            textArea.setText("Horse figure\nor destination\nare in the\nmiddle!\nImpossible\nmove");
            setHorse.setDisable(false);
            setDestination.setDisable(false);
            calculate.setDisable(false);
        } else {
            for (int i = 0; i < coordinates.size(); i++) {
                if (i % 3 == 0) {
                    coordinatesArea.appendText("\n");
                }
                coordinatesArea.appendText(coordinates.get(i).toString() + (coordinates.size() - 1 > i ? ", " : ""));
            }

            for (int i = 0; i < MAX_BUTTONS; i++) {
                buttonsHorse[i].setOpacity(0.2);
                buttonsHorse[i].setHasBeenClicked(false);
                buttonsHorse[i].setGraphic(new ImageView(unselected));
            }

            playSound();
            PathAnimator.animatePath(coordinates, horseSelected, buttonsHorse, gridPane, setHorse, setDestination, calculate);
            fromX = toX;
            fromY = toY;
        }

        scene.setCursor(Cursor.DEFAULT);

        optionToDrawImageOnButton = 0;
    }

    private void playSound() {
        int random = (Math.random() <= 0.5) ? 1 : 2;
        try {
            File soundFile1 = new File(MEDIA1);
            File soundFile2 = new File(MEDIA2);
            AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(soundFile1.toURI().toURL());
            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(soundFile2.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(random == 1 ? audioIn1 : audioIn2);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void gridButtonsMethod(ActionEvent event) {
        textArea.setStyle("-fx-control-inner-background: #7DB9B3;");
        if (optionToDrawImageOnButton == 1) {
            for (int i = 0; i < MAX_BUTTONS; i++) {
                if (event.getSource() == buttonsHorse[i] && !buttonsHorse[i].hasBeenClicked()) {
                    buttonsHorse[i].setOpacity(1);
                    buttonsHorse[i].setHasBeenClicked(true);
                    buttonsHorse[i].setGraphic(new ImageView(horseSelected));
                    isHorseSet = true;
                    fromX = i / 3;
                    fromY = i % 3;
                } else {
                    if (buttonsHorse[i].hasBeenClicked()) {
                        fromX = -1;
                        fromY = -1;
                        isHorseSet = false;
                        buttonsHorse[i].setHasBeenClicked(false);
                    }
                    buttonsHorse[i].setOpacity(0.2);
                    buttonsHorse[i].setGraphic(new ImageView(unselected));
                }
            }

        } else if (optionToDrawImageOnButton == 2) {
            for (int i = 0; i < MAX_BUTTONS; i++) {
                if (event.getSource() == buttonsDest[i] && !buttonsDest[i].hasBeenClicked()) {
                    buttonsDest[i].setOpacity(1);
                    buttonsDest[i].setHasBeenClicked(true);
                    buttonsDest[i].setGraphic(new ImageView(destinationSelected));
                    isDestSet = true;
                    toX = i / 3;
                    toY = i % 3;
                } else {
                    if (buttonsDest[i].hasBeenClicked()) {
                        toX = -1;
                        toY = -1;
                        isDestSet = false;
                        buttonsDest[i].setHasBeenClicked(false);
                    }
                    buttonsDest[i].setOpacity(0.2);
                    buttonsDest[i].setGraphic(new ImageView(unselected));
                }
            }
        }

    }

    @Override
    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) {
        arg0.setResizable(false);

        buttonInitializationMethod();

        panesInitializationMethod();

        buttonsOnTheRightInitializationMethod();

        textAreasInitializationMethod();

        vBoxInitializationMethod();

        scene = new Scene(gridPane);
        arg0.setTitle("Horse figure on tic tac toe board");
        arg0.setScene(scene);
        arg0.sizeToScene();
        arg0.show();
    }

    @Override
    public void handle(ActionEvent event) {
        coordinatesArea.setText("");
        if (event.getSource() == setHorse) {
            setHorseMethod();
        } else if (event.getSource() == setDestination) {
            setDestinationMethod();
        } else if (event.getSource() == calculate) {
            calculateMethod();
        } else {
            gridButtonsMethod(event);
        }
    }
}
