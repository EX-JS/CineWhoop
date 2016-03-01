
package RetrofitPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderAdult {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_value")
    @Expose
    private String ticketValue;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("movie_name")
    @Expose
    private String movieName;

    /**
     * 
     * @return
     *     The ticketId
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * 
     * @param ticketId
     *     The ticket_id
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * 
     * @return
     *     The ticketValue
     */
    public String getTicketValue() {
        return ticketValue;
    }

    /**
     * 
     * @param ticketValue
     *     The ticket_value
     */
    public void setTicketValue(String ticketValue) {
        this.ticketValue = ticketValue;
    }

    /**
     * 
     * @return
     *     The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     *     The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * @return
     *     The movieName
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * 
     * @param movieName
     *     The movie_name
     */
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

}
