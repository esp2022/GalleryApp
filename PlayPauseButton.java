package cs1302.gallery;

import javafx.scene.control.Button;

/** Establishes play pause button. */
public class PlayPauseButton extends Button {

    /** Constructor. */
    PlayPauseButton() {
        setText(PlayPauseButtonState.Play.name());
        setDisable(true);
    }

}
