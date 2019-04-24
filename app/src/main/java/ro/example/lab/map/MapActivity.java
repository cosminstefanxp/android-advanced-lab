package ro.example.lab.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import ro.example.lab.R;
import ro.example.lab.core.BaseActivity;

public class MapActivity extends BaseActivity {

    private static final String ARG_COUNTRY_ID = "ARG_COUNTRY_ID";
    private MapViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // Prepare the ViewModel
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
    }

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(ARG_COUNTRY_ID, id);
        context.startActivity(intent);
    }
}
