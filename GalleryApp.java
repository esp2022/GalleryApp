package cs1302.gallery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**                                                                                                                                                                                                                                           
 * Represents an iTunes Gallery App.                                                                                                                                                                                                          
 */
public class GalleryApp extends Application {

    private static int previous_result_count = 0;
    private Stage stage;
    private Scene scene;
    private HBox root;
    private static HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2) // uses HTTP protocol version 2 where possible                                                                                                                                                
            .followRedirects(HttpClient.Redirect.NORMAL) // always redirects, except from                                                                                                                                                     
        // HTTPS to HTTP                                                                                                                                                                                                                      
            .build(); // builds and returns an HttpClient                                                                                                                                                                                     
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static String limit = "200";

    /**                                                                                                                                                                                                                                       
     * Constructs a {@code GalleryApp} object}.                                                                                                                                                                                               
     */
   public GalleryApp() {
        this.stage = null;
        this.scene = null;
        this.root = new HBox();
    } // GalleryApp                                                                                                                                                                                                                           

    /** {@inheritDoc} */
    @Override
    public void init() {
        // feel free to modify this method                                                                                                                                                                                                    
        // System.out.println("init() called");                                                                                                                                                                                               
    } // init                                                                                                                                                                                                                                 

    /**                                                                                                                                                                                                                                       
     * {@inheritDoc}                                                                                                                                                                                                                          
     *                                                                                                                                                                                                                                        
     * @throws FileNotFoundException                                                                                                                                                                                                          
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        this.stage = stage;
        this.scene = new Scene(this.root);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.setTitle("GalleryApp!");

        // Modifiy start                                                                                                                                                                                                                      
        FirstRowHBox firstRow = new FirstRowHBox();
        SecondRowHBox secondRow = new SecondRowHBox();
        ThirdRowHBox thirdRow = new ThirdRowHBox();
        FourthRowHBox fourthRow = new FourthRowHBox();
        EventHandler<ActionEvent> handler = event2 -> swapImages(thirdRow);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        setGetImageButtonInitialAction(firstRow, secondRow, thirdRow, fourthRow, timeline);
        setPlayPauseButtonInitialAction(firstRow, thirdRow, timeline);
        VBox multiRows = new VBox();
        multiRows.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow);
        root.getChildren().add(multiRows);
        // Modify end                                                                                                                                                                                                                         

        this.stage.setScene(this.scene);
        this.stage.sizeToScene();
        this.stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
    } // start                                                                                                                                                                                                                                

    /** Set play pause button initial action.                                                                                                                                                                                                 
    @param firstRow                                                                                                                                                                                                                           
    @param thirdRow                                                                                                                                                                                                                           
    @param timeline                                                                                                                                                                                                                           
    */
    private void setPlayPauseButtonInitialAction(FirstRowHBox firstRow,
                                                 ThirdRowHBox thirdRow, Timeline timeline) {

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent event) {
                // System.out.println(firstRow.getPlayPauseButtonState());                                                                                                                                                                    
                if (firstRow.getPlayPauseButtonState() == PlayPauseButtonState.Play) {

                    firstRow.setPlayPauseButtonState(PlayPauseButtonState.Pause);
                    timeline.play();

                } else if (firstRow.getPlayPauseButtonState() == PlayPauseButtonState.Pause) {

                    timeline.pause();
                    firstRow.setPlayPauseButtonState(PlayPauseButtonState.Play);
                }
            }
        };
        firstRow.setPlayPauseButton(event);
    }

    /** Swap images.                                                                                                                                                                                                                          
     @param thirdRow                                                                                                                                                                                                                          
    */
    protected void swapImages(ThirdRowHBox thirdRow) {
        int randomNumberImageGrid = ThreadLocalRandom.current().nextInt(0, 20);
        int numberOfFilesAvailable = new File("resources/download/").list().length;
        // System.out.println(numberOfFilesAvailable);                                                                                                                                                                                        
        int randomNumberImageDownloaded =
            ThreadLocalRandom.current().nextInt(20, numberOfFilesAvailable);
        try {
            thirdRow.setThumbsImageViewByIndex(
               new Image(
                            new FileInputStream("resources/download/default_"
                                                + randomNumberImageDownloaded + ".png")),
                    randomNumberImageGrid);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Get image button initial action.                                                                                                                                                                                                      
    @param firstRow                                                                                                                                                                                                                           
    @param secondRow                                                                                                                                                                                                                          
    @param thirdRow                                                                                                                                                                                                                           
    @param fourthRow                                                                                                                                                                                                                          
    @param timeline                                                                                                                                                                                                                           
    */
    void setGetImageButtonInitialAction(
        FirstRowHBox firstRow,
            SecondRowHBox secondRow,
            ThirdRowHBox thirdRow,
            FourthRowHBox fourthRow,
            Timeline timeline) {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    timeline.stop();
                    firstRow.disablePlayPauseButton();
                    firstRow.setPlayPauseButtonState(PlayPauseButtonState.Play);
                    firstRow.disableGetImageButton();
                    secondRow.setStatusLabelText("Getting images...");
                    fourthRow.setDownloadProgressBarProgress(0);
                    String term = firstRow.getSearchTextFieldText();
                    String media = firstRow.getGenreComboBoxValue();
                    ExecutorService service = Executors.newFixedThreadPool(1);
                    Future<ItunesResponse> response = service
                      .submit(new ApiConsumeTask(term, media, GalleryApp.limit));
                    ItunesResponse obj = null;
                    try {
                        obj = response.get();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ExecutionException e2) {
                        Alert a = new Alert(AlertType.NONE);
                        if (GalleryApp.previous_result_count > 20) {
                            firstRow.enablePlayPauseButton();
                            fourthRow.setDownloadProgressBarProgress(100);
                        }
                        secondRow.setStatusLabelText("Last attempt to get images failed...");
                        firstRow.enableGetImageButton();
                        String query = String.format("?term=%s&media=%s&limit=%s",
                                firstRow.getSearchTextFieldText(),
                                firstRow.getGenreComboBoxValue(), GalleryApp.limit);
                        StringBuilder sb = new StringBuilder("URI: https://itunes.apple.com/search" + query);
                        sb.append(System.getProperty("line.separator"));
                        sb.append("Exception: " + e2.getCause().getClass().getName() + ": "
                                + e2.getCause().getMessage());
                        a.setAlertType(AlertType.ERROR);
                        a.setContentText(sb.toString());
                        a.show();
                    }
                    service.shutdown();
                    downloadImages(obj, firstRow, secondRow, thirdRow, fourthRow);
                } catch (IllegalArgumentException e) {
                    Alert a = new Alert(AlertType.NONE);
                   if (GalleryApp.previous_result_count > 20) {
                            firstRow.enablePlayPauseButton();
                            fourthRow.setDownloadProgressBarProgress(100);
                        }
                        secondRow.setStatusLabelText("Last attempt to get images failed...");
                        firstRow.enableGetImageButton();
                        String query = String.format("?term=%s&media=%s&limit=%s",
                                firstRow.getSearchTextFieldText(),
                                firstRow.getGenreComboBoxValue(), GalleryApp.limit);
                        StringBuilder sb = new StringBuilder("URI: https://itunes.apple.com/search" + query);
                        sb.append(System.getProperty("line.separator"));
                        sb.append("Exception: " + e2.getCause().getClass().getName() + ": "
                                + e2.getCause().getMessage());
                        a.setAlertType(AlertType.ERROR);
                        a.setContentText(sb.toString());
                        a.show();
                    }
                    service.shutdown();
                    downloadImages(obj, firstRow, secondRow, thirdRow, fourthRow);
                } catch (IllegalArgumentException e) {
                    Alert a = new Alert(AlertType.NONE);
                    secondRow.setStatusLabelText("Last attempt to get images failed...");
                    firstRow.enableGetImageButton();
                    String query = String.format("?term=%s&media=%s&limit=%s",
                                                 firstRow.getSearchTextFieldText(),
                            firstRow.getGenreComboBoxValue(), GalleryApp.limit);
                    StringBuilder sb = new StringBuilder("URI: https://itunes.apple.com/search" + query);
                    sb.append(System.getProperty("line.separator"));
                    sb.append(System.getProperty("line.separator"));
                    sb.append("Exception: " + e.getClass().getName() + ": " + e.getMessage());
                    a.setAlertType(AlertType.ERROR);
                    a.setContentText(sb.toString());
                    a.show();
                }
            }
        };
        firstRow.setGetImageButtonAction(event);
    }

    /** Deletes images. */
    private static void deleteImages() {
        File dir = new File("resources/download");
        for ( File file : dir.listFiles() ) {
            if ( !file.isDirectory() ) {
                file.delete();
            }
        }
    }

    /** Downloads images.          
     @param obj                                                                                                                                                                                                                                
    @param firstRow                                                                                                                                                                                                                           
    @param secondRow                                                                                                                                                                                                                          
    @param thirdRow                                                                                                                                                                                                                           
    @param fourthRow                                                                                                                                                                                                                          
    */
    private static void downloadImages(
        final ItunesResponse obj, FirstRowHBox firstRow, SecondRowHBox secondRow,
            ThirdRowHBox thirdRow,
            FourthRowHBox fourthRow) {
        if (obj != null) {
            ItunesResult[] uniqueResult =
                Arrays.stream(obj.results).distinct().toArray(ItunesResult[]::new);
            int length = uniqueResult.length;
            if (length < 20) {
                if (GalleryApp.previous_result_count > 20) {
                    firstRow.enablePlayPauseButton();
                    fourthRow.setDownloadProgressBarProgress(100);
                }
                throw new IllegalArgumentException
                (length + " distinct results found, but 21 or more are needed.");
            }
            GalleryApp.previous_result_count = length;
            runNow(() -> {
                try {
                    Files.createDirectories(Paths.get("resources/download"));
                    deleteImages();
                    for (int i = 0; i < length; i++) {
                        ItunesResult result = uniqueResult[i];
                        HttpRequest request = HttpRequest.newBuilder().uri
                            (URI.create(result.artworkUrl100))
                           .build();
                        HttpResponse<InputStream> response = HTTP_CLIENT.send(request,
                                HttpResponse.BodyHandlers.ofInputStream());
                        InputStream in = response.body();
                        Files.copy(in, Paths.get("resources/download/default_" + i
                                + ".png"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        // System.out.println(1.0 * ((double) i / (double) length));                                                                                                                                                          
                        setProgressBarProgress(1.0 * ((double) i / (double) length), fourthRow);
                    }
                    setProgressBarProgress(100, fourthRow);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                Runnable ImageDisplayAndSceneReset = () -> {
                    for (int i = 0; i < 20; i++) {
                        try {
                            thirdRow.setThumbsImageViewByIndex(
                                    new Image(new FileInputStream
                                    ("resources/download/default_" + i + ".png")), i);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    firstRow.enableGetImageButton();
                    firstRow.enablePlayPauseButton();
                    String query = String.format("?term=%s&media=%s&limit=%s",
                            firstRow.getSearchTextFieldText(),
                                                  firstRow.getGenreComboBoxValue(), GalleryApp.limit);
                    secondRow.setStatusLabelText("https://itunes.apple.com/search" + query);
                };
                Platform.runLater(ImageDisplayAndSceneReset);

            });
        }
    }

    /** Run now.                                                                                                                                                                                                                              
     @param target                                                                                                                                                                                                                            
    */
    private static void runNow(Runnable target) {
        Thread t = new Thread(target);
        t.setDaemon(true);
        t.start();
    } // runNow                                                                                                                                                                                                                               

    /** Set progress bar progress.                                                                                                                                                                                                            
     @param progress                                                                                                                                                                                                                          
     @param fourthRow                                                                                                                                                                                                                         
    */
    private static void setProgressBarProgress(final double progress, FourthRowHBox fourthRow) {
        Platform.runLater(() -> fourthRow.setDownloadProgressBarProgress(progress));
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {                                                                                                                                                                                                   
        // System.out.println("stop() called");                                                                                                                                                                                               
    } // stop                                                                                                                                                                                                                                 

} // GalleryApp  
