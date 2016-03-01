
package RetrofitPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Coupon {

    @SerializedName("coupon_off")
    @Expose
    private String couponOff;

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

}
