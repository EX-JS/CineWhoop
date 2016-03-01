
package RetrofitPackage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Credit {

    @SerializedName("credit")
    @Expose
    private String credit;

    /**
     * 
     * @return
     *     The credit
     */
    public String getCredit() {
        return credit;
    }

    /**
     * 
     * @param credit
     *     The credit
     */
    public void setCredit(String credit) {
        this.credit = credit;
    }

}
