package exousiatech.cinewhoop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jagteshwar on 19-02-2016.
 */
public class PaymentPaypal extends AppCompatActivity {
    private static final String CONFIG_CLIENT_ID = "AbAYo9I6KjzDHZlRZLhwKod8RYWK7ILtVVu0NQtZuBZ8Dxzz9k9CmD8PFmpWhQYzMY8sscj52l50sBXS";
//    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_eway);

    }
}
