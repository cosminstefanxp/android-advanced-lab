package ro.example.lab.map;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import ro.example.lab.R;
import ro.example.lab.core.BaseActivity;

public class MapActivity extends BaseActivity {

    private MapViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // Prepare the ViewModel
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
    }
}
