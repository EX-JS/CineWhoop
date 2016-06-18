
package RetrofitPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable{

    @SerializedName("cinema_id")
    @Expose
    private String cinemaId;
    @SerializedName("cinema_name")
    @Expose
    private String cinemaName;
    @SerializedName("cinema_time")
    @Expose
    private String cinemaTime;

    /**
     * 
     * @return
     *     The cinemaId
     */
    public String getCinemaId() {
        return cinemaId;
    }

    /**
     * 
     * @param cinemaId
     *     The cinema_id
     */
    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    /**
     * 
     * @return
     *     The cinemaName
     */
    public String getCinemaName() {
        return cinemaName;
    }

    /**
     * 
     * @param cinemaName
     *     The cinema_name
     */
    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    /**
     * 
     * @return
     *     The cinemaTime
     */
    public String getCinemaTime() {
        return cinemaTime;
    }

    /**
     * 
     * @param cinemaTime
     *     The cinema_time
     */
    public void setCinemaTime(String cinemaTime) {
        this.cinemaTime = cinemaTime;
    }

}
