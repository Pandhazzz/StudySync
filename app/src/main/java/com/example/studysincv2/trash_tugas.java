package com.example.studysincv2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

public class trash_tugas extends AppCompatActivity {

    Drawable drawable;
    Toolbar toolbarku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trash_tugas);
        toolbarku = findViewById(R.id.appbar);
        setSupportActionBar(toolbarku);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Baru Dihapus");

            drawable = ContextCompat.getDrawable(this, R.drawable.ic_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrash, menu);
        return true;
    }
}