package ro.example.lab.home;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ro.example.lab.core.ServiceProvider;
import ro.example.lab.data.Note;
import ro.example.lab.data.NotesRepository;
import timber.log.Timber;

public class HomeViewModel extends ViewModel {

    private NotesRepository notesRepository = ServiceProvider.getInstance().getNotesRepository();

    private MutableLiveData<Boolean> isFavorite;

    public HomeViewModel() {
        Timber.d("Home view model started");
    }

    public void onRefreshData() {
        notesRepository.syncNotes();
    }

    public void onAddRandomNote() {
        Note note = new Note("Test User",
                             "This is a sample: " + new Random().nextLong(),
                             Instant.now().toString());

        notesRepository.addNote(note);
    }

    public LiveData<List<Note>> getNotes() {
        return notesRepository.getNotes();
    }
}
