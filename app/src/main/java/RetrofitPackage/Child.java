
package RetrofitPackage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Child {

    @SerializedName("child_ticket_id")
    @Expose
    private String childTicketId;
    @SerializedName("child_ticket_value")
    @Expose
    private String childTicketValue;

    /**
     * 
     * @return
     *     The childTicketId
     */
    public String getChildTicketId() {
        return childTicketId;
    }

    /**
     * 
     * @param childTicketId
     *     The child_ticket_id
     */
    public void setChildTicketId(String childTicketId) {
        this.childTicketId = childTicketId;
    }

    /**
     * 
     * @return
     *     The childTicketValue
     */
    public String getChildTicketValue() {
        return childTicketValue;
    }

    /**
     * 
     * @param childTicketValue
     *     The child_ticket_value
     */
    public void setChildTicketValue(String childTicketValue) {
        this.childTicketValue = childTicketValue;
    }

}
