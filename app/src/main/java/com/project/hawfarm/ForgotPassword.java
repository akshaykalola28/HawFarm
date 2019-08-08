package com.project.hawfarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgotPassword extends AppCompatActivity {

    EditText emailField;
    Button sendButton;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailField = findViewById(R.id.input_email);
        sendButton = findViewById(R.id.forgot_password_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString().trim();

            }
        });
        setAnimations();
    }

    private void setAnimations()
    {
        CardView forgotPassCardview = findViewById(R.id.forgot_pass_cardview);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        forgotPassCardview.setAnimation(fromBottom);

        ImageView logoImageView=findViewById(R.id.company_logo);
        Animation fromtop=AnimationUtils.loadAnimation(this,R.anim.fromtop);
        logoImageView.setAnimation(fromtop);
    }
}
