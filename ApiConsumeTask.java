package cs1302.gallery;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** Retrieves hyperlinks to images searched by user. */
public class ApiConsumeTask implements Callable<ItunesResponse> {
    /** API URL details. */
    private static final String ITUNES_API = "https://itunes.apple.com/search";

    /** HTTP client. */
    private static HttpClient HTTP_CLIENT = HttpClient.newBuilder().version
        (HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).build();

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private String terms;
    private String medias;
    private String sizes;

/** Constructor.                                                                                                                                                                                                                              
@param term                                                                                                                                                                                                                                   
@param media                                                                                                                                                                                                                                  
@param size                                                                                                                                                                                                                                   
 */
    ApiConsumeTask(String term, String media, String size) {
        this.terms = term;
        this.medias = media;
        this.sizes = size;
    }

    private volatile ItunesResponse itunesResponse;

/** Get method for itunes response.                                                                                                                                                                                                           
@return returns itunes search results                                                                                                                                                                                                         
 */
    public ItunesResponse getItunesResponse() {
        return itunesResponse;
    }

/** Set method for itunes response.                                                                                                                                                                                                           
    @param itunesResponse                                                                                                                                                                                                                     
*/

    public void setItunesResponse(ItunesResponse itunesResponse) {
        this.itunesResponse = itunesResponse;
    }

    @Override
     public ItunesResponse call() {
        try {
            // form URI                                                                                                                                                                                                                       
            String term = URLEncoder.encode(terms, StandardCharsets.UTF_8);
            String media = URLEncoder.encode(medias, StandardCharsets.UTF_8);
            String limit = URLEncoder.encode(sizes, StandardCharsets.UTF_8);
            String query = String.format("?term=%s&media=%s&limit=%s", term, media, limit);
            String uri = ITUNES_API + query;

            // build request                                                                                                                                                                                                                  
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

            // send request / receive response in the form of a String                                                                                                                                                                        
            HttpResponse<String> response = HTTP_CLIENT.send(request, BodyHandlers.ofString());

            // ensure the request is okay                                                                                                                                                                                                     
            if (response.statusCode() != 200) {
                throw new RuntimeException(response.toString());
               // throw new IOException(response.toString());                                                                                                                                                                                 
            }

            String jsonString = response.body();
           // System.out.println("********** RAW JSON STRING: **********");                                                                                                                                                                   
            //System.out.println(jsonString.trim());                                                                                                                                                                                          

            // parse the JSON-formatted string using GSON                                                                                                                                                                                     
            itunesResponse = GSON.fromJson(jsonString, ItunesResponse.class);

        } catch (IOException | InterruptedException exp) {
          //  System.err.println(exp);                                                                                                                                                                                                        
            exp.printStackTrace();
        }
      //  System.out.println("********** API CALL DONE **********");                                                                                                                                                                          

        return itunesResponse;
    }
}
