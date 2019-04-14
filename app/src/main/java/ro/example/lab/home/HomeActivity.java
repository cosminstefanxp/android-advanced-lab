package ro.example.lab.home;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import ro.example.lab.R;
import ro.example.lab.core.BaseActivity;

public class HomeActivity extends BaseActivity {

    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Prepare the ViewModel
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }
}
