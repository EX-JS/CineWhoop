
package RetrofitPackage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderDatail {

    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
    @SerializedName("ticket_value_link")
    @Expose
    private String ticketValueLink;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_name")
    @Expose
    private String orderName;
    @SerializedName("order_value_img")
    @Expose
    private String orderValueImg;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    private String order_time;
    private String order_format;



    /**
     * 
     * @return
     *     The orderSerial
     */
    public String getOrderSerial() {
        return orderSerial;
    }

    /**
     * 
     * @param orderSerial
     *     The order_serial
     */
    public void setOrderSerial(String orderSerial) {
        this.orderSerial = orderSerial;
    }

    /**
     * 
     * @return
     *     The ticketValueLink
     */
    public String getTicketValueLink() {
        return ticketValueLink;
    }

    /**
     * 
     * @param ticketValueLink
     *     The ticket_value_link
     */
    public void setTicketValueLink(String ticketValueLink) {
        this.ticketValueLink = ticketValueLink;
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
     *     The orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * 
     * @param orderName
     *     The order_name
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * 
     * @return
     *     The orderValueImg
     */
    public String getOrderValueImg() {
        return orderValueImg;
    }

    /**
     * 
     * @param orderValueImg
     *     The order_value_img
     */
    public void setOrderValueImg(String orderValueImg) {
        this.orderValueImg = orderValueImg;
    }

    /**
     * 
     * @return
     *     The orderType
     */
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_format() {
        return order_format;
    }

    public void setOrder_format(String order_format) {
        this.order_format = order_format;
    }
}
