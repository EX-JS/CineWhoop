
package RetrofitPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieTimmingDetails implements Serializable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_id")
    @Expose
    private String statusId;
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The statusId
     */
    public String getStatusId() {
        return statusId;
    }

    /**
     * 
     * @param statusId
     *     The status_id
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

}
