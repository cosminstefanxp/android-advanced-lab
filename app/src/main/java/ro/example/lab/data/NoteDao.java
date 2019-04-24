package ro.example.lab.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    public LiveData<List<Note>> getNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNotes(List<Note> notes);

    @Query("SELECT count(*) from note")
    Integer noteCount();
}
