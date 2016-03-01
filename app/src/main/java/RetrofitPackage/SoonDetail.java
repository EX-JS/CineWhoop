
package RetrofitPackage;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SoonDetail {

    @SerializedName("movie_id")
    @Expose
    private String movieId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("imdb_url")
    @Expose
    private String imdbUrl;
    @SerializedName("image")
    @Expose
    private List<String> image = new ArrayList<String>();

    /**
     * 
     * @return
     *     The movieId
     */
    public String getMovieId() {
        return movieId;
    }

    /**
     * 
     * @param movieId
     *     The movie_id
     */
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The imdbUrl
     */
    public String getImdbUrl() {
        return imdbUrl;
    }

    /**
     * 
     * @param imdbUrl
     *     The imdb_url
     */
    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }

    /**
     * 
     * @return
     *     The image
     */
    public List<String> getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(List<String> image) {
        this.image = image;
    }

}
