
package RetrofitPackage;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderDetails {

    @SerializedName("Order_datail")
    @Expose
    private List<OrderDatail> OrderDatail = new ArrayList<OrderDatail>();
    @SerializedName("Total_credit")
    @Expose
    private List<TotalCredit> TotalCredit = new ArrayList<TotalCredit>();

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
    public List<TotalCredit> getTotalCredit() {
        return TotalCredit;
    }

    /**
     * 
     * @param TotalCredit
     *     The Total_credit
     */
    public void setTotalCredit(List<TotalCredit> TotalCredit) {
        this.TotalCredit = TotalCredit;
    }

}
