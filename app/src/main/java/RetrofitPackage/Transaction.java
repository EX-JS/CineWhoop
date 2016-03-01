
package RetrofitPackage;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Transaction {

    @SerializedName("AuthorisationCode")
    @Expose
    private String AuthorisationCode;
    @SerializedName("ResponseCode")
    @Expose
    private String ResponseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String ResponseMessage;
    @SerializedName("InvoiceNumber")
    @Expose
    private String InvoiceNumber;
    @SerializedName("InvoiceReference")
    @Expose
    private String InvoiceReference;
    @SerializedName("TotalAmount")
    @Expose
    private Integer TotalAmount;
    @SerializedName("TransactionID")
    @Expose
    private Integer TransactionID;
    @SerializedName("TransactionStatus")
    @Expose
    private Boolean TransactionStatus;
    @SerializedName("TokenCustomerID")
    @Expose
    private Object TokenCustomerID;

    @SerializedName("Options")
    @Expose
    private List<Object> Options = new ArrayList<Object>();


    @SerializedName("CustomerNote")
    @Expose
    private Object CustomerNote;

    @SerializedName("Capture")
    @Expose
    private Boolean Capture;

    /**
     * 
     * @return
     *     The AuthorisationCode
     */
    public String getAuthorisationCode() {
        return AuthorisationCode;
    }

    /**
     * 
     * @param AuthorisationCode
     *     The AuthorisationCode
     */
    public void setAuthorisationCode(String AuthorisationCode) {
        this.AuthorisationCode = AuthorisationCode;
    }

    /**
     * 
     * @return
     *     The ResponseCode
     */
    public String getResponseCode() {
        return ResponseCode;
    }

    /**
     * 
     * @param ResponseCode
     *     The ResponseCode
     */
    public void setResponseCode(String ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    /**
     * 
     * @return
     *     The ResponseMessage
     */
    public String getResponseMessage() {
        return ResponseMessage;
    }

    /**
     * 
     * @param ResponseMessage
     *     The ResponseMessage
     */
    public void setResponseMessage(String ResponseMessage) {
        this.ResponseMessage = ResponseMessage;
    }

    /**
     * 
     * @return
     *     The InvoiceNumber
     */
    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    /**
     * 
     * @param InvoiceNumber
     *     The InvoiceNumber
     */
    public void setInvoiceNumber(String InvoiceNumber) {
        this.InvoiceNumber = InvoiceNumber;
    }

    /**
     * 
     * @return
     *     The InvoiceReference
     */
    public String getInvoiceReference() {
        return InvoiceReference;
    }

    /**
     * 
     * @param InvoiceReference
     *     The InvoiceReference
     */
    public void setInvoiceReference(String InvoiceReference) {
        this.InvoiceReference = InvoiceReference;
    }

    /**
     * 
     * @return
     *     The TotalAmount
     */
    public Integer getTotalAmount() {
        return TotalAmount;
    }

    /**
     * 
     * @param TotalAmount
     *     The TotalAmount
     */
    public void setTotalAmount(Integer TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    /**
     * 
     * @return
     *     The TransactionID
     */
    public Integer getTransactionID() {
        return TransactionID;
    }

    /**
     * 
     * @param TransactionID
     *     The TransactionID
     */
    public void setTransactionID(Integer TransactionID) {
        this.TransactionID = TransactionID;
    }

    /**
     * 
     * @return
     *     The TransactionStatus
     */
    public Boolean getTransactionStatus() {
        return TransactionStatus;
    }

    /**
     * 
     * @param TransactionStatus
     *     The TransactionStatus
     */
    public void setTransactionStatus(Boolean TransactionStatus) {
        this.TransactionStatus = TransactionStatus;
    }

    /**
     * 
     * @return
     *     The TokenCustomerID
     */
    public Object getTokenCustomerID() {
        return TokenCustomerID;
    }

    /**
     * 
     * @param TokenCustomerID
     *     The TokenCustomerID
     */
    public void setTokenCustomerID(Object TokenCustomerID) {
        this.TokenCustomerID = TokenCustomerID;
    }

    /**
     * 
     * @return
     *     The BeagleScore
     */


    /**
     * 
     * @return
     *     The Options
     */
    public List<Object> getOptions() {
        return Options;
    }

    /**
     * 
     * @param Options
     *     The Options
     */
    public void setOptions(List<Object> Options) {
        this.Options = Options;
    }



    public Object getCustomerNote() {
        return CustomerNote;
    }

    /**
     * 
     * @param CustomerNote
     *     The CustomerNote
     */
    public void setCustomerNote(Object CustomerNote) {
        this.CustomerNote = CustomerNote;
    }

    /**
     * 
     * @return
     *     The ShippingAddress
     */

    public Boolean getCapture() {
        return Capture;
    }

    /**
     * 
     * @param Capture
     *     The Capture
     */
    public void setCapture(Boolean Capture) {
        this.Capture = Capture;
    }

}
