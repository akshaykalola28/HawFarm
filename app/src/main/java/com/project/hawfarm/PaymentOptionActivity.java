package com.project.hawfarm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentOptionActivity extends AppCompatActivity {

    private static final String TAG = "PaymentOptionActivity";
    RadioButton paytm, cod, debitcard, creaditcard;
    String grandTotal, paymentMethod;
    TextView orderItem;
    private View view;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        Bundle extra = getIntent().getExtras();

        try {
            setDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValidData()) {
                    try {
                        mDialog = new ProgressDialog(PaymentOptionActivity.this);
                        mDialog.setMessage("Please Wait...");
                        mDialog.show();
                        submitOrder();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean getValidData() {
        boolean valid = false;

        if (paymentMethod == null) {
            Toast.makeText(getApplicationContext(), "Please Select Payment Option", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }
        return valid;
    }

    private void setDetails() throws JSONException {
        paytm = findViewById(R.id.radio_paytm);
        cod = findViewById(R.id.radio_cod);
        debitcard = findViewById(R.id.radio_dc);
        creaditcard = findViewById(R.id.radio_cc);
    }

    private void submitOrder() throws JSONException {
        JSONObject orderParams = new JSONObject();

        String buyerName = CartData.currentCustomerData.getString("name");
        String buyerEmail = CartData.currentCustomerData.getString("email");
        String buyerAddress = CartData.currentCustomerData.getJSONArray("address").getJSONObject(0).getString("address");
        Log.d("BuyerAddress", buyerAddress);
        String buyerNumber = CartData.currentCustomerData.getString("phone");
        grandTotal = getIntent().getStringExtra("GrandTotal");

        //Fetch hawkerData
        String sellerName = CartData.hawkerDataInCart.getString("name");
        String sellerEmail = CartData.hawkerDataInCart.getString("email");
        String sellerAddress = CartData.hawkerDataInCart.getJSONArray("address").getJSONObject(0).getString("address");
        Log.d("SellerAddress", sellerAddress);
        String sellerNumber = CartData.hawkerDataInCart.getString("phone");
        String sellerId = CartData.hawkerDataInCart.getString("userId");

        Log.d("cartData:", String.valueOf(CartData.cartItemList));
        JSONArray quantityArray = new JSONArray();

        for (int i = 0; i < CartData.cartItemList.size(); i++) {
            JSONObject jsonObject = CartData.cartItemList.get(i);
            JSONObject quantitySingleObject = new JSONObject();
            quantitySingleObject.put("veg_name", jsonObject.getString("veg_name"));
            quantitySingleObject.put("quantity", jsonObject.getString("weight"));
            quantityArray.put(quantitySingleObject);
        }
        orderParams.put("buyerName", buyerName);
        orderParams.put("buyerEmail", buyerEmail);
        orderParams.put("buyerAddress", buyerAddress);
        orderParams.put("buyerNumber", buyerNumber);
        orderParams.put("grandTotal", grandTotal);
        orderParams.put("sellerId", sellerId);
        orderParams.put("sellerName", sellerName);
        orderParams.put("sellerEmail", sellerEmail);
        orderParams.put("sellerAddress", sellerAddress);
        orderParams.put("sellerNumber", sellerNumber);
        orderParams.put("paymentMethod", paymentMethod);
        orderParams.put("orderQuantity", quantityArray);

        Log.d(TAG, "setDetails: " + quantityArray);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ServerData.ADD_ORDER_URL, orderParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("responseSuccess");
                    Log.d("rseponse", String.valueOf(response));
                    if (success.equals("true")) {
                        mDialog.dismiss();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentOptionActivity.this);
                        alertDialogBuilder.setMessage("Order Added Successfully").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    } else {
                        mDialog.dismiss();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentOptionActivity.this);
                        alertDialogBuilder.setMessage("Failed to Place Order.").setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                } catch (JSONException e) {
                    mDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mDialog.dismiss();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(PaymentOptionActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void onRadioButtonClicked(View view) {
        this.view = view;
        boolean checked = ((RadioButton) view).isChecked();
        String str = "";
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_paytm:
                if (checked) str = "Paytm";
                break;
            case R.id.radio_cod:
                if (checked) str = "COD";
                break;
            case R.id.radio_cc:
                if (checked) str = "Credit-Card";
                break;
            case R.id.radio_dc:
                if (checked) str = "Debit-Card";
                break;
        }
        paymentMethod = str;
        Log.d("value", str);
    }
}
