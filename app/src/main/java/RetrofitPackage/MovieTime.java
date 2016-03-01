package RetrofitPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jagteshwar on 27-01-2016.
 */
public class MovieTime implements Serializable {

    private List<String> cinema_1 = new ArrayList<String>();
    private List<String> cinema_2 = new ArrayList<String>();
    private List<String> cinema_3 = new ArrayList<String>();
    private List<String> cinema_4 = new ArrayList<String>();
    private List<String> cinema_5 = new ArrayList<String>();
    private List<String> cinema_6 = new ArrayList<String>();


    public List<String> getCinema1() {
        return cinema_1;
    }


    public void setCinema1(List<String> cinema1) {
        this.cinema_1 = cinema1;
    }


    public List<String> getCinema2() {
        return cinema_2;
    }


    public void setCinema2(List<String> cinema2) {
        this.cinema_2 = cinema2;
    }


    public List<String> getCinema3() {
        return cinema_3;
    }


    public void setCinema3(List<String> cinema3) {
        this.cinema_3 = cinema3;
    }


    public List<String> getCinema4() {
        return cinema_4;
    }


    public void setCinema4(List<String> cinema4) {
        this.cinema_4 = cinema4;
    }


    public List<String> getCinema5() {
        return cinema_5;
    }


    public void setCinema5(List<String> cinema5) {
        this.cinema_5 = cinema5;
    }

    public List<String> getCinema6() {
        return cinema_6;
    }


    public void setCinema6(List<String> cinema6) {
        this.cinema_6 = cinema6;
    }
}
