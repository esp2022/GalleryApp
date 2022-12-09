package cs1302.gallery;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/** First row in scene graph. */
public class FirstRowHBox extends HBox {
    private Button playPauseButton = new PlayPauseButton();
    private PlayPauseButtonState playPauseButtonState;
    private Label searchLabel = new Label();
    private TextField searchTextField = new SearchText();
    private ComboBox<String> genreComboBox = new GenreComboBox<>();
    private Button getImageButton;

    /** Returns state of play pause button.                                                                                                                                                                                                   
@return gets state of play pause button                                                                                                                                                                                                       
     */
    public PlayPauseButtonState getPlayPauseButtonState() {
        return this.playPauseButtonState;
    }

    /** Set state of play pause button.                                                                                                                                                                                                       
        @param playPauseButtonState                                                                                                                                                                                                           
     */
    public void setPlayPauseButtonStateOnly(PlayPauseButtonState playPauseButtonState) {
        this.playPauseButtonState = playPauseButtonState;
    }

/** Set state of play pause button and the text.                                                                                                                                                                                              
@param playPauseButtonState                                                                                                                                                                                                                   
 */
    public void setPlayPauseButtonState(PlayPauseButtonState playPauseButtonState) {
        this.playPauseButtonState = playPauseButtonState;
        setPlayPauseButtonText(playPauseButtonState);
    }

    /** Set text of play pause button.                                                                                                                                                                                                        
@param state                                                                                                                                                                                                                                  
     */
    public void setPlayPauseButtonText(PlayPauseButtonState state) {
        //setPlayPauseButtonState(state);                                                                                                                                                                                                     
	this.playPauseButton.setText(state.name());
    }

    /** Get play pause button text.                                                                                                                                                                                                           
@return gets text of play pause button                                                                                                                                                                                                        
     */
    public String getPlayPauseButtonText() {
	return this.playPauseButton.getText();
    }

/** Get search text field text.   
@return gets text of search text field                                                                                                                                                                                                        
 */
    public String getSearchTextFieldText() {
        return this.searchTextField.getText();
    }

/** Set search text field text value.                                                                                                                                                                                                         
@param Text                                                                                                                                                                                                                                   
 */
    public void setSearchTextFieldSetText(String Text) {
        this.searchTextField.setText(Text);
    }

    /** Disables play pause button. */
    public void disablePlayPauseButton() {
        this.playPauseButton.setDisable(true);
    }

    /** Enables play pause button.                                                                                                                                                                                                            
     */
    public void enablePlayPauseButton() {
        this.playPauseButton.setDisable(false);
    }

    /** Get status of image button.                                                                                                                                                                                                           
     @return StatusofImageButton                                                                                                                                                                                                              
    */
    public boolean getStatusOfImageButton() {
        return this.getImageButton.isDisable();
    }
 /** Set image button action.                                                                                                                                                                                                              
     @param event                                                                                                                                                                                                                             
    */
    public void setGetImageButtonAction(EventHandler<ActionEvent>  event) {
        this.getImageButton.setOnAction(event);
    }

    /** Set play pause button action.                                                                                                                                                                                                         
     @param event                                                                                                                                                                                                                             
    */
    public void setPlayPauseButton(EventHandler<ActionEvent> event) {
        this.playPauseButton.setOnAction(event);
    }

    /** Dsiables get image button. */
    public void disableGetImageButton() {
        this.getImageButton.setDisable(true);
    }

    /** Enables get image button. */
    public void enableGetImageButton() {
        this.getImageButton.setDisable(false);
    }

    /** Get genre combo box value.    
     @return genreComboBox                                                                                                                                                                                                                    
    */
    public String getGenreComboBoxValue() {
        return this.genreComboBox.getValue();
    }

    /** Contructor. */
    FirstRowHBox() {

        this.playPauseButtonState = PlayPauseButtonState.Play;

        HBox.setMargin(this.playPauseButton, new Insets(10, 10, 10, 10));

        this.searchLabel.setText("Search: ");
        HBox.setMargin(this.searchTextField, new Insets(1, 5, 1, 5));

        this.genreComboBox.getSelectionModel().select(2);

        this.getImageButton = new GetImageButton();
        HBox.setMargin(this.getImageButton, new Insets(10, 10, 10, 10));

        this.setAlignment(Pos.BASELINE_CENTER);
        this.getChildren().addAll(this.playPauseButton, this.searchLabel,
                                  this.searchTextField, this.genreComboBox, this.getImageButton);
    }

}
