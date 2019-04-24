package ro.example.lab.home;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import ro.example.lab.R;
import ro.example.lab.core.BaseActivity;
import ro.example.lab.map.MapActivity;
import timber.log.Timber;

public class HomeActivity extends BaseActivity {

    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Prepare the ViewModel
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        viewModel.getNotes().observe(this, notes -> {
            Timber.i("New notes available: %s", notes);
        });

        findViewById(R.id.refreshButton).setOnClickListener(v -> {
            viewModel.onRefreshData();
        });
        findViewById(R.id.addButton).setOnClickListener(v -> {
            viewModel.onAddRandomNote();
        });
    }
}
