package ro.example.lab;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import ro.example.lab.core.ServiceProvider;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        AndroidThreeTen.init(this);

        ServiceProvider.initialize(this);

        Timber.d("App initialized at %s", LocalDateTime.now());
    }
}
