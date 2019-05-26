package ro.example.lab.map;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ro.example.lab.core.ServiceProvider;
import ro.example.lab.data.Station;
import ro.example.lab.data.StationsRepository;

public class MapViewModel extends ViewModel {

    private LiveData<List<Station>> stationsLiveData;
    private StationsRepository stationsRepository = ServiceProvider.getInstance().getStationsRepository();

    void init(String country) {
        if (this.stationsLiveData != null) {
            return;
        }
        stationsLiveData = stationsRepository.addCountryStationsListener(country);
    }

    LiveData<List<Station>> getCountryStations() {
        return this.stationsLiveData;
    }

    @Override
    protected void onCleared() {
        stationsRepository.removeListener();
    }

}
