package cs1302.gallery;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** Establishes fourth row. */
public class FourthRowHBox extends HBox {
    private DownloadProgressBar downloadProgressBar = new DownloadProgressBar();
    private static Label staticFooterLabel = new Label();

/** Sets download progress bar progress.                                                                                                                                                                                                      
 @param progress                                                                                                                                                                                                                              
*/
    public void setDownloadProgressBarProgress( double progress ) {
        this.downloadProgressBar.setProgress(progress);
    }

/** Contructor. */
    FourthRowHBox() {
        this.downloadProgressBar.setMinWidth(280);
        this.downloadProgressBar.setProgress(0);
        HBox.setMargin(this.downloadProgressBar, new Insets(1, 5, 1, 5));
        FourthRowHBox.staticFooterLabel.setText("Images provide by iTunes Search API");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(this.downloadProgressBar, FourthRowHBox.staticFooterLabel);
    }
}
