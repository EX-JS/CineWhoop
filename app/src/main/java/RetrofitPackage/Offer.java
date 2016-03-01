
package RetrofitPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Offer {

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("coupon")
    @Expose
    private String coupon;

    /**
     * 
     * @return
     *     The offerId
     */
    public String getOfferId() {
        return offerId;
    }

    /**
     * 
     * @param offerId
     *     The offer_id
     */
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    /**
     * 
     * @return
     *     The coupon
     */
    public String getCoupon() {
        return coupon;
    }

    /**
     * 
     * @param coupon
     *     The coupon
     */
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

}
