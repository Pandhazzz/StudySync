package com.example.studysincv2;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.studysincv2.db.DatabaseHelper;
import com.example.studysincv2.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_catatan extends AppCompatActivity {

    Drawable drawable;
    Toolbar toolbarku;
    EditText judul, deskripsi;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_catatan);
        toolbarku = findViewById(R.id.appbar);
        judul = findViewById(R.id.judul_catatan);
        deskripsi = findViewById(R.id.isi_catatan);

        setSupportActionBar(toolbarku);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Catatan Baru");

            drawable = ContextCompat.getDrawable(this, R.drawable.ic_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        databaseHelper = new DatabaseHelper(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadd_catatan, menu);
        return true;
    }
    public void simpanCatatan(MenuItem item) {
        //startActivity(new Intent(add_catatan.this, CActivity.class));
        String title = judul.getText().toString().trim();
        String description = deskripsi.getText().toString().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        if (!title.isEmpty() && !description.isEmpty()) {
            Note note = new Note(title, description, date);
            boolean isInserted = databaseHelper.addNote(note);
            if (isInserted) {
                Toast.makeText(this, "Berhasil menyimpan catatan", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "gagal menyimpan catatan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Judul dan deskripsi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }
    }

}