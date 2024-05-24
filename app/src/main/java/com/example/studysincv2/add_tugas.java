package com.example.studysincv2;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class add_tugas extends AppCompatActivity {

    Drawable drawable;
    Toolbar toolbarku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tugas);
        toolbarku = findViewById(R.id.appbar);
        setSupportActionBar(toolbarku);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tugas Baru");

            drawable = ContextCompat.getDrawable(this, R.drawable.ic_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadd_tugas, menu);
        return true;
    }
}