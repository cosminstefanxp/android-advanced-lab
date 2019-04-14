package ro.example.lab.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ro.example.lab.core.ServiceProvider;
import ro.example.lab.data.Profile;

class HomeViewModel extends ViewModel {

    private MutableLiveData<Profile> profile = new MutableLiveData<>();

    public HomeViewModel() {
        Profile currentProfile = ServiceProvider.getInstance().getProfileRepository().getProfile();
        profile.setValue(currentProfile);
    }
}
