package ro.example.lab.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import timber.log.Timber;

public class StationsRepository {

    private FirebaseDatabase database;

    private DatabaseReference countryStationsReference;
    private ValueEventListener countryStationsValueEventListener;
    private MutableLiveData<List<Station>> countryStationsLiveData;

    private List<Station> stations;
    private Executor backgroundExecutor = Executors.newCachedThreadPool();


    public StationsRepository() {
        database = FirebaseDatabase.getInstance();
        countryStationsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Station>> addCountryStationsListener(String country) { // country = RO /AT
        // Read from the database
        Timber.tag("MAP").e("ADD DB LISTENER");

        countryStationsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // DataSnapshot - Java object transformation can be expensive
                backgroundExecutor.execute(() -> {
                    stations = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Station station = snapshot.getValue(Station.class);
                        stations.add(station);
                    }
                    countryStationsLiveData.postValue(stations);
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Timber.tag("MAP").e(databaseError.toException(), "Failed to read value.");
            }
        };

        countryStationsReference = database.getReference("stations/" + country);
        countryStationsReference.addValueEventListener(countryStationsValueEventListener);

        return countryStationsLiveData;
    }

    public void removeListener() {
        Timber.tag("MAP").e("REMOVE DB LISTENER");
        if (countryStationsValueEventListener != null) {
            countryStationsReference.removeEventListener(countryStationsValueEventListener);
        }
    }
}
