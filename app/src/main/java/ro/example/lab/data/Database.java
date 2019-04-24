package ro.example.lab.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Note.class},
                        version = Database.VERSION)
public abstract class Database extends RoomDatabase {

    static final int VERSION = 2;

    public abstract NoteDao getNoteDao();

    public static Database initialize(Context context) {
        return Room.databaseBuilder(context, Database.class, "AwesomeDatabase")
                   .fallbackToDestructiveMigration()
                   .build();
    }
}
