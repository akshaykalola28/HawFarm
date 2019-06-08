package com.project.hawfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectLangActivity extends AppCompatActivity {

    Button eng_lang, guj_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lang);

        eng_lang = findViewById(R.id.english_lang);
        guj_lang = findViewById(R.id.gujrati_lang);

        eng_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLangActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
