package com.example.studysincv2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studysincv2.adapter.DeletedNoteAdapter;
import com.example.studysincv2.db.DatabaseHelper;
import com.example.studysincv2.model.Note;

import java.util.ArrayList;
import java.util.List;

public class trash_catatan extends AppCompatActivity {

    Drawable drawable;
    Toolbar toolbarku;

    private RecyclerView recyclerViewDeletedNotes;
    private DeletedNoteAdapter deletedNoteAdapter;
    private ImageView delete_button, restore_button;
    private List<Note> deletedNoteList;
    private boolean isSelectionMode = false;
    private List<Note> selectedNotes = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private CardView viewHapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trash_catatan);
        toolbarku = findViewById(R.id.appbar);
        setSupportActionBar(toolbarku);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Baru Dihapus");

            drawable = ContextCompat.getDrawable(this, R.drawable.ic_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewHapus = findViewById(R.id.delete_view);
        restore_button = findViewById(R.id.icon_restore);
        delete_button = findViewById(R.id.icon_hapus);
        recyclerViewDeletedNotes = findViewById(R.id.recyclerViewDeletedNotes);
        recyclerViewDeletedNotes.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        loadDeletedNotes();

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedNotes.isEmpty()){
                    viewHapus.setVisibility(View.GONE);
                    deleteSelectedNotes();
                }else{
                    Toast.makeText(trash_catatan.this, "Tidak ada item yang dipilih",Toast.LENGTH_SHORT).show();
                }
            }
        });

        restore_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedNotes.isEmpty()){
                    viewHapus.setVisibility(View.GONE);
                    restoreNote();
                }else{
                    Toast.makeText(trash_catatan.this, "Tidak ada item yang dipilih",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteSelectedNotes() {
        for (Note note : selectedNotes) {
            boolean isDeleted = databaseHelper.permanentDeleteNoteById(note.getId());
            if (isDeleted) {
                deletedNoteList.remove(note);
            }
        }
        selectedNotes.clear();
        deletedNoteAdapter.notifyDataSetChanged();

        hideSelectionViews();
        Toast.makeText(trash_catatan.this, "Berhasil menghapus catatan",Toast.LENGTH_SHORT).show();
    }

    private void restoreNote() {
        for (Note note : selectedNotes) {
            databaseHelper.addNote(note);
            boolean isDeleted = databaseHelper.permanentDeleteNoteById(note.getId());
            if (isDeleted) {
                deletedNoteList.remove(note);
            }
        }
        selectedNotes.clear();
        deletedNoteAdapter.notifyDataSetChanged();

        hideSelectionViews();
        Toast.makeText(trash_catatan.this, "Berhasil mengembalikan catatan",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrash, menu);
        return true;
    }

    private void loadDeletedNotes() {
        deletedNoteList = databaseHelper.getAllDeletedNotes();
        deletedNoteAdapter = new DeletedNoteAdapter(this, deletedNoteList);
        recyclerViewDeletedNotes.setAdapter(deletedNoteAdapter);
    }

    public void startSelectionMode(){
        isSelectionMode = !isSelectionMode;
        if (isSelectionMode) {

            showSelectionViews();
        } else {

            hideSelectionViews();
        }
    }

    private void showSelectionViews() {
        for (Note note : deletedNoteList) {
            note.setSelectable(true);
        }
        deletedNoteAdapter.notifyDataSetChanged();
        viewHapus.setVisibility(View.VISIBLE);
    }

    private void hideSelectionViews() {
        for (Note note : deletedNoteList) {
            note.setSelected(false);
            note.setSelectable(false);
        }
        deletedNoteAdapter.notifyDataSetChanged();
        viewHapus.setVisibility(View.GONE);
    }

    public void addSelectedNote(Note note) {
        selectedNotes.add(note);
    }

    public void removeSelectedNote(Note note) {
        selectedNotes.remove(note);
    }
}