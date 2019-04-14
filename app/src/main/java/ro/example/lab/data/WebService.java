package ro.example.lab.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class WebService {

    private Api api;
    private static final String API_BASE_URL = "http://example.org";

    public WebService() {
        api = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);
    }

    public interface Api {

        @GET("/profile")
        Profile fetchProfile();

    }
}
