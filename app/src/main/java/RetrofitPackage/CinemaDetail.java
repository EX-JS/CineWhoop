
package RetrofitPackage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class CinemaDetail implements Serializable {

    private String cinema_id;
    private String cinema_name;

    private String logo;
    private String Adult_ticket_price;
    private String Child_ticket_price;
    private String ticket_status;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getCinemaId() {
        return cinema_id;
    }

    public void setCinemaId(String cinema_id) {
        this.cinema_id = cinema_id;
    }

    public String getCinemaName() {
        return cinema_name;
    }

    public void setCinemaName(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getLogo() {
        return logo;
    }


    public void setLogo(String logo) {
        this.logo = logo;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }


    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getAdult_ticket_price() {
        return Adult_ticket_price;
    }

    public void setAdult_ticket_price(String adult_ticket_price) {
        Adult_ticket_price = adult_ticket_price;
    }

    public String getChild_ticket_price() {
        return Child_ticket_price;
    }

    public void setChild_ticket_price(String child_ticket_price) {
        Child_ticket_price = child_ticket_price;
    }

    public String getTicket_status() {
        return ticket_status;
    }

    public void setTicket_status(String ticket_status) {
        this.ticket_status = ticket_status;
    }
}
