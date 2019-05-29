package ro.example.lab.offers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ro.example.lab.core.ServiceProvider;

public class OfferDetailsViewModel extends AndroidViewModel {
    private OfferDetailsRepository offerDetailsRepository = ServiceProvider.getInstance().getOfferDetailsRepository();
    private LiveData<Offer> response;

    public OfferDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Offer> getObservableOfferDetails() {
        return response;
    }

    public void getDetails(int id) {
        response = offerDetailsRepository.getDetails(id);
    }
}
