package cs1302.gallery;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** Establishes second row hbox. */
public class SecondRowHBox extends HBox {
    private Label statusLabel = new StatusLabel();

    /** Sets status label text.                                                                                                                                                                                                               
     @param Text                                                                                                                                                                                                                              
    */
    public void setStatusLabelText(String Text) {
        this.statusLabel.setText(Text);
    }

/** Constructor. */
    SecondRowHBox() {
        this.getChildren().add(this.statusLabel);
    }

}
