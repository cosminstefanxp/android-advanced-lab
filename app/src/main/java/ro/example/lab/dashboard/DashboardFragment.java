package ro.example.lab.dashboard;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import ro.example.lab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private DashboardViewModel mViewModel;

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Prepare the ViewModel
        mViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        mRecyclerView = view.findViewById(R.id.recycler_view);

        DashboardAdapter dashboardAdapter = new DashboardAdapter();
        mRecyclerView.setAdapter(dashboardAdapter);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        return view;
    }

}
