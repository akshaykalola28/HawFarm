package com.project.hawfarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";

    TextView forgotPasswordField, signUpField;
    EditText emailField, passField;
    String email, pass;
    Button bttn_login;
    SharedPreferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        //for selecting language (only for once)
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean("langSelectKey", false);
        if (!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("langSelectKey", Boolean.TRUE);
            edit.apply();
            startActivity(new Intent(this, SelectLangActivity.class));
            finish();
        }
        //End of Select Language activity

        emailField = findViewById(R.id.input_email);
        passField = findViewById(R.id.input_password);
        bttn_login = (Button) findViewById(R.id.btn_login);

        // shared preferences
        mPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        checkSharedPreferences();

        bttn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValidData()) {
                    Log.d(TAG, "Button Clicked!!");
                    userLogin();
                }
            }
        });

        forgotPasswordField = findViewById(R.id.forgot_pass);
        forgotPasswordField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, ForgotPassword.class));
            }
        });

        signUpField = findViewById(R.id.link_signup);
        signUpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

        setAnimations();
    }

    private void setAnimations() {
        CardView loginCardview = findViewById(R.id.login_CardView);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        loginCardview.setAnimation(fromBottom);

        signUpField.setAnimation(fromBottom);

        ImageView logoImageView = findViewById(R.id.company_logo);
        Animation fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        logoImageView.setAnimation(fromtop);


    }

    private boolean getValidData() {
        boolean valid = false;
        email = emailField.getText().toString().trim();
        pass = passField.getText().toString().trim();

        if (email.isEmpty()) {
            emailField.setError("Enter E-mail");
            emailField.requestFocus();
        } else if (pass.isEmpty()) {
            passField.setError("Enter Password");
            passField.requestFocus();
        } else {
            valid = true;
        }
        return valid;
    }

    private void userLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerData.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "in Responce");
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("responseSuccess");
                            if (success.equals("true")) {
                                String data = jsonObject.getString("data");
                                Log.d(TAG, "data: " + data);

                                savePreferences(data);
                                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                                intent.putExtra("userData", data);
                                startActivity(intent);
                                finish();
                            } else {
                                String data = jsonObject.getString("data");
                                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                        data, Snackbar.LENGTH_INDEFINITE).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LogInActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LogInActivity.this);
        requestQueue.add(stringRequest);
    }

    private void savePreferences(String data) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("userDataStringKey", data);
        editor.apply();
    }

    private void checkSharedPreferences() {

        if (mPreferences.contains("userDataStringKey")) {
            String userDataString = mPreferences.getString("userDataStringKey", "");
            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
            intent.putExtra("userData", userDataString);
            startActivity(intent);
            finish();
        }
    }
}