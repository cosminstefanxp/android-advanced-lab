package ro.example.lab.core;

import android.app.Application;

import ro.example.lab.data.Database;
import ro.example.lab.data.NotesRepository;
import ro.example.lab.data.ProfileRepository;
import ro.example.lab.data.WebService;

public class ServiceProvider {
    private static ServiceProvider INSTANCE;

    private final Application app;
    private final Database database;
    private final WebService webService;
    private final ProfileRepository profileRepository;
    private final NotesRepository notesRepository;
    private final AuthenticationManager authenticationManager;

    private ServiceProvider(Application app) {
        this.app = app;

        // Initialize the various "singleton" services, that should only be instantiated once and shared in the app
        database = Database.initialize(app);
        authenticationManager = new AuthenticationManager();
        webService = new WebService(authenticationManager);
        profileRepository = null;
        notesRepository = new NotesRepository(webService, database);
    }

    public Database getDatabase() {
        return database;
    }

    public WebService getWebService() {
        return webService;
    }

    public ProfileRepository getProfileRepository() {
        return profileRepository;
    }

    public NotesRepository getNotesRepository() {
        return notesRepository;
    }

    public static void initialize(Application app) {
        if (INSTANCE != null)
            throw new IllegalStateException("ServiceProvider is already initialized");
        INSTANCE = new ServiceProvider(app);
    }

    public static ServiceProvider getInstance() {
        return INSTANCE;
    }
}
