package ro.example.lab.offers;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ro.example.lab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferDetailsFragment extends Fragment {

    private TextView mTitleTxtView;
    private TextView mDescriptionTxtView;
    private TextView mPointsInfoTxtView;
    private TextView mProgressPointsTxtView;
    private TextView mDurationTxtView;

    private ImageView mTitleImgView;
    private ImageView mBackArrowImgView;

    private List<Offer> mOffers;

    private OfferDetailsViewModel viewModel;

    public OfferDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_offer_details, container, false);

        initVariables(v);

        viewModel.getDetails(1);//this.getArguments().getInt("id"));
        observeViewModel(viewModel);
        return v;
    }

    private void observeViewModel(OfferDetailsViewModel viewModel) {
        viewModel.getObservableOfferDetails().observe(this, new Observer<Offer>() {
            @Override
            public void onChanged(Offer offer) {
                initOffer(offer);
            }
        });
    }

    private void initOffer(Offer offer) {

        mTitleTxtView.setText(offer.getTitle());
        mDescriptionTxtView.setText(offer.getDescription());
        mPointsInfoTxtView.setText(offer.getInfoPoints());

        String duration = offer.getStartDate().get(Calendar.DAY_OF_MONTH) + "." + offer.getStartDate().get(Calendar.MONTH)
                          + "." + offer.getStartDate().get(Calendar.YEAR) + "-" + offer.getEndDate().get(Calendar.DAY_OF_MONTH)
                          + "." + offer.getEndDate().get(Calendar.MONTH)
                          + "." + offer.getEndDate().get(Calendar.YEAR) ;
        String progress = offer.getGainedPoints() + " / " + offer.getRequiredPoints()
                          + " puncte";
        mDurationTxtView.setText(duration);
        mProgressPointsTxtView.setText(progress);

        Picasso.with(getContext()).load(offer.getImageUrl())
               .into(mTitleImgView);
    }

    private void initVariables(View v) {
        mTitleImgView = v.findViewById(R.id.mainTitleImgView);
        mBackArrowImgView = v.findViewById(R.id.backArrowImgView);

        mBackArrowImgView.setOnClickListener(
                v1 -> Toast.makeText(getContext(), "Back button pressed", Toast.LENGTH_SHORT).show());

        mTitleTxtView = v.findViewById(R.id.titleTxtView);
        mDescriptionTxtView = v.findViewById(R.id.descriptionTxtView);
        mProgressPointsTxtView = v.findViewById(R.id.accumulatedPointsTxtView);
        mDurationTxtView = v.findViewById(R.id.availableDateTxtView);
        mPointsInfoTxtView = v.findViewById(R.id.infoPointsTxtView);

        viewModel = ViewModelProviders.of(this).get(OfferDetailsViewModel.class);
    }

    public static Fragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);

        Fragment fragment = new OfferDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

}
