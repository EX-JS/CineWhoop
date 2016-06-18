package RetrofitPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CineWhoopDetail implements Serializable {


    private String movie_id;
    private String title;
    private String description;
    private String category;
    private String movie_lenght;
    private String release_date;
    private String director;
    private String actor;
    private String rating;
    private List<String> featured_image = new ArrayList<>();
    private String cinema_id;
    private String Movie_expiry_date;
    private MovieTime movie_time;
    private String youtube_url;
    private String imdb_url;
    private String terms;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getMovieId() {
        return movie_id;
    }

    public void setMovieId(String movieId) {
        this.movie_id = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String Category) {
        this.category = Category;
    }

    public String getMovieLenght() {
        return movie_lenght;
    }

    public void setMovieLenght(String movieLenght) {
        this.movie_lenght = movieLenght;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(String cinema_id) {
        this.cinema_id = cinema_id;
    }

    public List<String> getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(List<String> featured_image) {
        this.featured_image = featured_image;
    }

    public MovieTime getMovie_time() {
        return movie_time;
    }

    public void setMovie_time(MovieTime movie_time) {
        this.movie_time = movie_time;
    }

    public String getMovie_expiry_date() {
        return Movie_expiry_date;
    }

    public void setMovie_expiry_date(String movie_expiry_date) {
        Movie_expiry_date = movie_expiry_date;
    }

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

    public String getImdb_url() {
        return imdb_url;
    }

    public void setImdb_url(String imdb_url) {
        this.imdb_url = imdb_url;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}