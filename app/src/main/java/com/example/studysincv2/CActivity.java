package com.example.studysincv2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studysincv2.adapter.NoteAdapter;
import com.example.studysincv2.db.DatabaseHelper;
import com.example.studysincv2.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private TextView text_jumlah;
    private Button button_hapus;
    private Button button_cancel_hapus;
    private ImageView button_confirm_hapus;
    private CardView viewHapus, viewConfirmHapus;

    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private boolean isSelectionMode = false;
    private List<Note> selectedNotes = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    FloatingActionButton fab,add_alarm,recyle_bin;
    Animation fabOpen,fabClose,rotateForward, rotateBackward;
    boolean isOpen = false;
    Toolbar toolbarku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catatan);
        toolbarku = findViewById(R.id.appbar);
        fab=findViewById(R.id.fab);
        add_alarm=findViewById(R.id.add_alarm);
        recyle_bin=findViewById(R.id.reycle_bin);
        text_jumlah = findViewById(R.id.text_selected_count);
        button_hapus = findViewById(R.id.button_hapus);
        button_cancel_hapus = findViewById(R.id.button_batal_hapus);
        button_confirm_hapus = findViewById(R.id.icon_hapus);
        viewHapus = findViewById(R.id.delete_view);
        viewConfirmHapus = findViewById(R.id.delete_confirm);
        fabOpen= AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        fabClose= AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        rotateForward= AnimationUtils.loadAnimation(this, R.anim.forward_rotate_anim);
        rotateBackward= AnimationUtils.loadAnimation(this, R.anim.backward_rotate_anim);

        recyclerViewNotes = findViewById(R.id.rv1);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
        add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent addActivity = new Intent(CActivity.this, add_catatan.class);
                startActivity(addActivity);
            }
        });
        recyle_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent addActivity = new Intent(CActivity.this, trash_catatan.class);
                startActivity(addActivity);
            }
        });

        setSupportActionBar(toolbarku);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Catatan");
        }

        button_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHapus.setVisibility(View.GONE);
                viewConfirmHapus.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                deleteSelectedNotes();
            }
        });

        button_confirm_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedNotes.isEmpty()){
                    viewHapus.setVisibility(View.GONE);
                    viewConfirmHapus.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);
                }else{
                    Toast.makeText(CActivity.this, "Tidak ada item yang dipilih",Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_cancel_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewConfirmHapus.setVisibility(View.GONE);
                viewHapus.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menucatatan,menu);
        return true;
    }

    private void showSelectionViews() {
        for (Note note : noteList) {
            note.setSelectable(true);
        }
        noteAdapter.notifyDataSetChanged();
        viewHapus.setVisibility(View.VISIBLE);
        viewConfirmHapus.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    private void hideSelectionViews() {
        for (Note note : noteList) {
            note.setSelected(false);
            note.setSelectable(false);
        }
        noteAdapter.notifyDataSetChanged();
        viewHapus.setVisibility(View.GONE);
        viewConfirmHapus.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }

    public void openLTActivity(MenuItem item) {
        Intent intent = new Intent(this, LTActivity.class);
        startActivity(intent);
        finish();
    }

    public void startSelectionMode(){
        isSelectionMode = !isSelectionMode;
        if (isSelectionMode) {

            showSelectionViews();
        } else {

            hideSelectionViews();
        }
    }

    private void animateFab(){
        if (isOpen){
            fab.startAnimation(rotateBackward);
            add_alarm.startAnimation(fabClose);
            recyle_bin.startAnimation(fabClose);
            add_alarm.setVisibility(View.GONE);
            recyle_bin.setVisibility(View.GONE);
            add_alarm.setClickable(false);
            recyle_bin.setClickable(false);
            isOpen=false;
        }else{
            fab.startAnimation(rotateForward);
            add_alarm.startAnimation(fabOpen);
            recyle_bin.startAnimation(fabOpen);
            add_alarm.setVisibility(View.VISIBLE);
            recyle_bin.setVisibility(View.VISIBLE);
            add_alarm.setClickable(true);
            recyle_bin.setClickable(true);
            isOpen=true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        noteList = databaseHelper.getAllNotes();
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(this, noteList);
            recyclerViewNotes.setAdapter(noteAdapter);
        } else {
            noteAdapter.noteList = noteList;
            noteAdapter.notifyDataSetChanged();
        }
    }

    public void addSelectedNote(Note note) {
        selectedNotes.add(note);
        text_jumlah.setText(selectedNotes.size()+" Item terpilih");
    }

    public void removeSelectedNote(Note note) {
        selectedNotes.remove(note);
        text_jumlah.setText("Hapus "+selectedNotes.size()+" item?");
    }

    private void deleteSelectedNotes() {
        for (Note note : selectedNotes) {
            databaseHelper.addNoteToDeletedNotes(note);
            boolean isDeleted = databaseHelper.deleteNoteById(note.getId());
            if (isDeleted) {
                noteList.remove(note);
            }
        }
        selectedNotes.clear();
        noteAdapter.notifyDataSetChanged();

        hideSelectionViews();
        Toast.makeText(CActivity.this, "Berhasil menghapus catatan",Toast.LENGTH_SHORT).show();
    }

}