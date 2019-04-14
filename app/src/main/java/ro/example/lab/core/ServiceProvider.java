package ro.example.lab.core;

import android.app.Application;

import ro.example.lab.data.Database;
import ro.example.lab.data.ProfileRepository;
import ro.example.lab.data.WebService;

public class ServiceProvider {
    private static ServiceProvider INSTANCE;

    private final Application app;
    private final Database database;
    private final WebService webService;
    private final ProfileRepository profileRepository;

    private ServiceProvider(Application app) {
        this.app = app;

        // Initialize the various "singleton" services, that should only be instantiated once and shared in the app
        database = new Database(); // TODO: Replace with proper Room initialization
        webService = new WebService();
        profileRepository = new ProfileRepository(database.getProfileDao(), webService);
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

    public static void initialize(Application app) {
        if (INSTANCE != null)
            throw new IllegalStateException("ServiceProvider is already initialized");
        INSTANCE = new ServiceProvider(app);
    }

    public static ServiceProvider getInstance() {
        return INSTANCE;
    }
}
