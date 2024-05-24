package com.example.studysincv2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studysincv2.CActivity;
import com.example.studysincv2.DateUtils;
import com.example.studysincv2.R;
import com.example.studysincv2.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context context;
    public List<Note> noteList;

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notelist, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        // Format the date before setting it to the TextView
        String formattedDate = DateUtils.formatDate(note.getDate());
        holder.textViewDate.setText(formattedDate);

        if (note.isSelectable()) {
            holder.checkBoxNote.setVisibility(View.VISIBLE);
            holder.checkBoxNote.setChecked(note.isSelected());
            holder.checkBoxNote.setOnCheckedChangeListener((buttonView, isChecked) -> {
                note.setSelected(isChecked);
                if (isChecked) {
                    ((CActivity) context).addSelectedNote(note);
                } else {
                    ((CActivity) context).removeSelectedNote(note);
                }
            });
        } else {
            holder.checkBoxNote.setVisibility(View.GONE);
        }

        holder.itemView.setOnLongClickListener(v -> {
            ((CActivity) context).startSelectionMode();
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            if (note.isSelectable()) {
                holder.checkBoxNote.setChecked(!note.isSelected());
            } else {
                // Handle normal click if needed
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription, textViewDate;
        CheckBox checkBoxNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textjudul);
            textViewDescription = itemView.findViewById(R.id.textdeskripsi);
            textViewDate = itemView.findViewById(R.id.tanggal);
            checkBoxNote = itemView.findViewById(R.id.checkBoxNote);
        }
    }
}

