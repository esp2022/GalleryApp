package cs1302.gallery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/** Establishes genre combo box. */
public class GenreComboBox <T> extends ComboBox<String> {

    static ObservableList<String> chocieListString = FXCollections.observableArrayList(
        "movie",
        "podcast",
        "music",
        "musicVideo",
        "audiobook",
        "shortFilm",
        "tvShow",
        "software",
        "ebook",
        "all");

    /** Constructor. */
    GenreComboBox() {

        super(chocieListString);
    }
}
