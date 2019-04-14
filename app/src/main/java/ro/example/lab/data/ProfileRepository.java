package ro.example.lab.data;

public class ProfileRepository {

    private final ProfileDao profileDao;
    private final WebService webService;

    public ProfileRepository(ProfileDao profileDao, WebService webService) {
        this.profileDao = profileDao;
        this.webService = webService;
    }

    public Profile getProfile(){
        // TODO:
        throw new UnsupportedOperationException();
    }
}
