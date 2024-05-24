package com.example.studysincv2;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LTActivity extends AppCompatActivity {

    FloatingActionButton fab,add_alarm,recyle_bin;
    Animation fabOpen,fabClose,rotateForward, rotateBackward;
    boolean isOpen = false;
    Toolbar toolbarku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tugas);
        toolbarku = findViewById(R.id.appbar);
        fab=findViewById(R.id.fab);
        add_alarm=findViewById(R.id.add_alarm);
        recyle_bin=findViewById(R.id.reycle_bin);
        fabOpen= AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        fabClose= AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        rotateForward= AnimationUtils.loadAnimation(this, R.anim.forward_rotate_anim);
        rotateBackward= AnimationUtils.loadAnimation(this, R.anim.backward_rotate_anim);

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
                Intent addActivity = new Intent(LTActivity.this, add_tugas.class);
                startActivity(addActivity);
            }
        });
        recyle_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent trashActivity = new Intent(LTActivity.this, trash_tugas.class);
                startActivity(trashActivity);
            }
        });

        setSupportActionBar(toolbarku);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Daftar Tugas");
        }
    }
    public void openCActivity(MenuItem item) {
        Intent intent = new Intent(this, CActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menutugas,menu);
        return true;
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
}