package com.project.hawfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextView signInView;
    EditText nameField, emailField, passField, cpassField, mobileField, addressField, pincodeField;
    Button submitDataButton;
    String name, email, pass, cpass, address, mobileString, pincodeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameField = findViewById(R.id.input_name);
        emailField = findViewById(R.id.input_email);
        passField = findViewById(R.id.input_password);
        cpassField = findViewById(R.id.confirm_password);
        mobileField = findViewById(R.id.mo_number);
        addressField = findViewById(R.id.input_address);
        pincodeField = findViewById(R.id.input_pincode);
        submitDataButton = findViewById(R.id.btn_signup);

        signInView = findViewById(R.id.link_login);
        signInView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submitDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValidData()) {
                    submitData();
                }
            }
        });
    }

    private boolean getValidData() {
        boolean valid = false;
        name = nameField.getText().toString().trim();
        email = emailField.getText().toString().trim();
        pass = passField.getText().toString().trim();
        cpass = cpassField.getText().toString().trim();
        mobileString = mobileField.getText().toString().trim();
        address = addressField.getText().toString().trim();
        pincodeString = pincodeField.getText().toString().trim();

        if (name.isEmpty()) {
            nameField.setError("Enter Name");
            nameField.requestFocus();
        } else if (email.isEmpty()) {
            emailField.setError("Enter Email");
            emailField.requestFocus();
        } else if (pass.isEmpty()) {
            passField.setError("Enter Password");
            passField.requestFocus();
        } else if (cpass.isEmpty() || !pass.equals(cpass)) {
            cpassField.setError("Password are not match");
            cpassField.requestFocus();
        } else if (mobileString.isEmpty() || mobileString.length() != 10) {
            mobileField.setError("Enter valid Mobile Number");
            mobileField.requestFocus();
        } else if (address.isEmpty()) {
            addressField.setError("Enter Address");
            addressField.requestFocus();
        } else if (pincodeString.isEmpty()) {
            pincodeField.setError("Enter Pincode");
            pincodeField.requestFocus();
        } else {
            valid = true;
        }
        return valid;
    }

    private void submitData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerData.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            if (data.equals("ER_DUP_ENTRY")) {
                                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                        "User Already Exists. Please try to LogIn", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Log In", new View.OnClickListener() {
                                            public void onClick(View v) {
                                                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                            }
                                        }).show();
                            } else {
                                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                        "Registration Successful " + data, Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Log In", new View.OnClickListener() {
                                            public void onClick(View v) {
                                                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                            }
                                        }).show();
                            }
                            Toast.makeText(SignUpActivity.this, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                "Something is Wrong! Please try again.", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", mobileString);
                params.put("password", pass);
                //TODO: Change the user type
                params.put("user_type", "buyer");
                params.put("address", address);
                params.put("pincode", pincodeString);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(stringRequest);
    }
}
