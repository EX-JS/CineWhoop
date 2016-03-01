
package RetrofitPackage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Adult {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_value")
    @Expose
    private String ticketValue;

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

}
