package app.buttons;

import app.contracts.ICustomButton;
import javafx.scene.control.ToggleButton;

public class CustomButton extends ToggleButton implements ICustomButton {

    private boolean hasBeenClicked;

    public CustomButton() {
        this.hasBeenClicked = false;
    }

    @Override
    public boolean hasBeenClicked() {
        return hasBeenClicked;
    }

    @Override
    public void setHasBeenClicked(boolean hasBeenClicked) {
        this.hasBeenClicked = hasBeenClicked;
    }
}
