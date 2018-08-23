package app.animation;

import app.cell_object.Cell;
import app.buttons.CustomButton;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class PathAnimator {

    public static void animatePath(List<Cell> coordinates, Image horseSelected, CustomButton[] buttonsHorse,
                            GridPane gridPane, Button setHorse, Button setDestination, Button calculate) {

        ImageView imageView = new ImageView(horseSelected);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(128, 128);
        stackPane.getChildren().add(imageView);

        Node node = gridPane.getChildren().get(1);

        int buttonCoordinates = (coordinates.get(0).getX() * 3) + coordinates.get(0).getY();
        CustomButton starterButton = buttonsHorse[buttonCoordinates];
        double startX = starterButton.getLayoutX()/Integer.MAX_VALUE;
        double startY = starterButton.getLayoutY()/Integer.MAX_VALUE;

        SequentialTransition sequentialTransition = new SequentialTransition();

        gridPane.add(stackPane, (int) startX, (int) startY);
        stackPane.toFront();

        for (int i = 0; i < coordinates.size() - 1; i++) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(2), stackPane);
            transition.setFromX(((node.getBoundsInParent().getMaxY() / 3) * (coordinates.get(i).getY()   -1)));
            transition.setFromY(((node.getBoundsInParent().getMaxX() / 3) * (coordinates.get(i).getX()   -1)));
            transition.setToX(((node.getBoundsInParent().getMaxY() / 3) * (coordinates.get(i + 1).getY() -1)));
            transition.setToY(((node.getBoundsInParent().getMaxX() / 3) * (coordinates.get(i + 1).getX() -1)));
            sequentialTransition.getChildren().add(transition);
        }
        sequentialTransition.play();

        sequentialTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cell holder = (coordinates.get(coordinates.size() - 1));
                buttonsHorse[(holder.getX() * 3 + holder.getY())].setGraphic(new ImageView(horseSelected));
                buttonsHorse[(holder.getX() * 3 + holder.getY())].setOpacity(1);
                gridPane.getChildren().remove(stackPane);
                setHorse.setDisable(false);
                setDestination.setDisable(false);
                calculate.setDisable(false);
            }
        });

    }
}
