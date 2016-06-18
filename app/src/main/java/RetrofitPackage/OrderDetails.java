
package RetrofitPackage;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderDetails {

    @SerializedName("Order_detail")
    @Expose
    private List<OrderDatail> OrderDatail = new ArrayList<OrderDatail>();
    @SerializedName("Total_credit")
    @Expose
    private TotalCredit TotalCredit = new TotalCredit();

    @SerializedName("status")
    @Expose
    private String status;
    /**
     * 
     * @return
     *     The OrderDatail
     */
    public List<OrderDatail> getOrderDatail() {
        return OrderDatail;
    }

    /**
     * 
     * @param OrderDatail
     *     The Order_datail
     */
    public void setOrderDatail(List<OrderDatail> OrderDatail) {
        this.OrderDatail = OrderDatail;
    }

    /**
     * 
     * @return
     *     The TotalCredit
     */
    public TotalCredit getTotalCredit() {
        return TotalCredit;
    }

    /**
     * 
     * @param TotalCredit
     *     The Total_credit
     */
    public void setTotalCredit(TotalCredit TotalCredit) {
        this.TotalCredit = TotalCredit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
