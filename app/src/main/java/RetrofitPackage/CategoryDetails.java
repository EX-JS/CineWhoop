
package RetrofitPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class CategoryDetails implements Serializable {


    private List<String> category = new ArrayList<String>();


    public List<String> getCategory() {
        return category;
    }


    public void setCategory(List<String> category) {
        this.category = category;
    }
}
