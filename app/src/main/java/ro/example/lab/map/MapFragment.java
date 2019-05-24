package ro.example.lab.map;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import ro.example.lab.R;
import ro.example.lab.data.Station;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private TextView mTextViewStations;


    private MapViewModel viewModel;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mTextViewStations = view.findViewById(R.id.textView_stations);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureViewModel();
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        viewModel.init("RO");
        viewModel.getCountryStations().observe(this, this::updateUIWithData);
    }

    private void updateUIWithData(List<Station> stations) {
        if (stations != null) {
            mTextViewStations.setText(String.valueOf(stations.size()));
        }
    }

}
