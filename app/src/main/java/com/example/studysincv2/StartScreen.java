package com.example.studysincv2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class StartScreen extends AppCompatActivity {

    TextView teksv1, teksv2;
    ImageView imgv1;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String pertamakali = preferences.getString("Install Pertama Kali", "");

        if (pertamakali.equals("")) {
            setContentView(R.layout.activity_start_screen);
            teksv1 = findViewById(R.id.judulapk2);
            teksv2 = findViewById(R.id.tv1);
            imgv1 = findViewById(R.id.gambarlogo);
            btn1 = findViewById(R.id.btnmulai);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Install Pertama Kali", "Benar");
                    editor.apply();

                    Intent IntentStartScreen = new Intent(StartScreen.this, LTActivity.class);
                    startActivity(IntentStartScreen);
                    finish();
                }
            });
        } else {
            Intent IntentStartScreen = new Intent(StartScreen.this, LTActivity.class);
            startActivity(IntentStartScreen);
            finish();
        }
    }
}