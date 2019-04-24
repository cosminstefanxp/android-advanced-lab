package ro.example.lab.data;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import ro.example.lab.core.AuthenticationManager;

public class WebService {

    private static final String API_BASE_URL = "https://backend-dot-fsloyalty-20da2.appspot.com";

    private final AuthenticationManager authManager;
    private final Api api;

    public WebService(AuthenticationManager authenticationManager) {
        authManager = authenticationManager;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        api = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                                .callTimeout(10, TimeUnit.SECONDS)
                                .addInterceptor(loggingInterceptor)
                                .build())
                .build()
                .create(Api.class);
    }

    public Call<List<Note>> fetchNotes() {
        return api.fetchNotes(authManager.getAuthToken());
    }

    public Call<Void> saveNote(Note note) {
        return api.saveNote(authManager.getAuthToken(), note);
    }

    public interface Api {

        @GET("/notes")
        Call<List<Note>> fetchNotes(@Header("Authorization") String token);

        @PUT("/notes")
        Call<Void> saveNote(@Header("Authorization") String token,
                            @Body Note note);
    }
}
