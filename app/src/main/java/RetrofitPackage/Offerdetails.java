
package RetrofitPackage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Offerdetails {

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("offer_name")
    @Expose
    private String offerName;
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
     *     The offerName
     */
    public String getOfferName() {
        return offerName;
    }

    /**
     *
     * @param offerName
     *     The offer_name
     */
    public void setOfferName(String offerName) {
        this.offerName = offerName;
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
