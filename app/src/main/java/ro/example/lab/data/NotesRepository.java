package ro.example.lab.data;

import org.threeten.bp.Instant;
import org.threeten.bp.temporal.ChronoUnit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NotesRepository {

    private WebService webService;
    private Database database;
    private Executor backgroundExecutor = Executors.newCachedThreadPool();

    private Instant notesSyncedAt = Instant.EPOCH;
    // TODO: Switch to SingleLiveEvent
    // See https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
    private MutableLiveData<NoteError> errorsLiveData = new MutableLiveData<>();

    public NotesRepository(WebService webService, Database database) {
        this.webService = webService;
        this.database = database;
    }

    public void syncNotes() {
        if (notesSyncedAt.isAfter(Instant.now().minus(10, ChronoUnit.SECONDS))) {
            Timber.d("Notes just synchronized, skipping...");
            return;
        }

        webService.fetchNotes()
                  .enqueue(new Callback<List<Note>>() {
                      @Override
                      public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                          if (response.isSuccessful()) {
                              notesSyncedAt = Instant.now();
                              backgroundExecutor.execute(() -> {
                                  database.getNoteDao().insertNotes(response.body());
                              });
                          } else {
                              errorsLiveData.postValue(new NoteError(null, NoteError.Type.SYNC_NOTES_FAILED));
                          }
                      }

                      @Override
                      public void onFailure(Call<List<Note>> call, Throwable t) {
                          Timber.e(t, "Failed to fetch notes:");
                          errorsLiveData.postValue(new NoteError(t, NoteError.Type.SYNC_NOTES_FAILED));
                      }
                  });
    }

    public void fetchMoreData() {
        int currentCount = database.getNoteDao().noteCount();
        int nextPage = (currentCount / 100) + 1;
        // TODO: webService.getApi().fetchNotes(authManager.getAuthToken(), nextPage, 100)
    }

    public LiveData<List<Note>> getNotes() {
        return database.getNoteDao().getNotes();
    }

    public MutableLiveData<NoteError> getErrorsLiveData() {
        return errorsLiveData;
    }

    public void addNote(Note note) {
        backgroundExecutor.execute(() -> {
            // First persist data in DB
            database.getNoteDao().insertNote(note);

            // Then store on server
            try {
                webService.saveNote(note).execute();
            } catch (IOException e) {
                Timber.e(e, "Failed to add note:");
                errorsLiveData.postValue(new NoteError(e, NoteError.Type.ADD_NOTE_FAILED));
            }
        });

    }

    public static class NoteError {
        Throwable throwable;
        Type type;

        NoteError(Throwable throwable, Type type) {
            this.throwable = throwable;
            this.type = type;
        }

        public enum Type {
            ADD_NOTE_FAILED,
            SYNC_NOTES_FAILED
        }
    }

}
