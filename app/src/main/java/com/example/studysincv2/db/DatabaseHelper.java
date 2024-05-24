package com.example.studysincv2.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.studysincv2.model.Note;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";
    private static final String DELETED_TABLE_NAME = "deleted_notes";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT)";

        String createDeletedNotesTable = "CREATE TABLE " + DELETED_TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT)";

        db.execSQL(createTable);
        db.execSQL(createDeletedNotesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DELETED_TABLE_NAME);
        onCreate(db);
    }

    public boolean addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, note.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, note.getDescription());
        contentValues.put(COLUMN_DATE, note.getDate());

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);

                // Check if columns are present
                if (idIndex >= 0 && titleIndex >= 0 && descriptionIndex >= 0 && dateIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    String date = cursor.getString(dateIndex);

                    Note note = new Note(title, description, date);
                    note.setId(id);
                    notes.add(note);
                } else {
                    // Handle the error: missing columns
                    Log.e("DatabaseHelper", "One or more columns are missing in the database");
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        return notes;
    }

    // Metode untuk menambahkan catatan ke tabel deleted_notes
    public void addNoteToDeletedNotes(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DATE, note.getDate());
        db.insert(DELETED_TABLE_NAME, null, values);
        db.close();
    }

    public void restoreToDeletedNotes(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DATE, note.getDate());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Metode untuk menghapus catatan dari tabel notes berdasarkan id
    public boolean deleteNoteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean permanentDeleteNoteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(DELETED_TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public List<Note> getAllDeletedNotes() {
        List<Note> deletedNotes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DELETED_TABLE_NAME, null, null, null, null, null, COLUMN_DATE + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                Note note = new Note(title, description, date);
                note.setId(id);
                deletedNotes.add(note);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return deletedNotes;
    }
}
