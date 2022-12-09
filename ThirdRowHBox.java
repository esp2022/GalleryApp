package cs1302.gallery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/** Establishes third row hbox. */
public class ThirdRowHBox extends HBox {
    private ThumbsImageView[] imageViewArray = new ThumbsImageView[20];
    private GridPane imageGridPane = new ImageGridPane();

    /** Sets thumbs image view by index.                                                                                                                                                                                                      
     @param img                                                                                                                                                                                                                               
     @param index                                                                                                                                                                                                                             
    */
    public void setThumbsImageViewByIndex(Image img, int index) {
        this.imageViewArray[index].setImage(img);
    }

    /**Establishes image stage reset.  */
    public void imageStageReset() throws FileNotFoundException {
        for (int i = 0; i < this.imageViewArray.length; i++) {
            ImageView thumImageView = new ThumbsImageView(
                new Image(new FileInputStream("resources/default.png")));
            thumImageView.setFitHeight(100);
            thumImageView.setFitWidth(100);
            this.imageViewArray[i] = (ThumbsImageView) thumImageView;
        }
        int i = 0;
        for (int j = 0; j < 5 && i < this.imageViewArray.length; j++) {
            for (int k = 0; k < 5 && i < this.imageViewArray.length; k++) {
                this.imageGridPane.add(this.imageViewArray[i], k, j, 1, 1);
                i++;

            }
        }
    }

    /** Constructor. */
    ThirdRowHBox() throws FileNotFoundException {
        imageStageReset();
        this.getChildren().add(this.imageGridPane);
    }
}
