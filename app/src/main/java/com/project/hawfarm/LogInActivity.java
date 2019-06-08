package com.project.hawfarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";

    TextView forgotPasswordField, signUpField;

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
    }
}