
package RetrofitPackage;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TransactionStatus {

    @SerializedName("Transactions")
    @Expose
    private List<Transaction> Transactions = new ArrayList<Transaction>();
    @SerializedName("Errors")
    @Expose
    private String Errors;

    /**
     * 
     * @return
     *     The Transactions
     */
    public List<Transaction> getTransactions() {
        return Transactions;
    }

    /**
     * 
     * @param Transactions
     *     The Transactions
     */
    public void setTransactions(List<Transaction> Transactions) {
        this.Transactions = Transactions;
    }

    /**
     * 
     * @return
     *     The Errors
     */
    public String getErrors() {
        return Errors;
    }

    /**
     * 
     * @param Errors
     *     The Errors
     */
    public void setErrors(String Errors) {
        this.Errors = Errors;
    }

}
