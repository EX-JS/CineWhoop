
package RetrofitPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Coupon {

    @SerializedName("coupon_off")
    @Expose
    private String couponOff;

    @SerializedName("status")
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The couponOff
     */
    public String getCouponOff() {
        return couponOff;
    }

    /**
     * 
     * @param couponOff
     *     The coupon_off
     */
    public void setCouponOff(String couponOff) {
        this.couponOff = couponOff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
