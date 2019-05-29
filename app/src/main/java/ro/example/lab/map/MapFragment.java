package ro.example.lab.map;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import ro.example.lab.R;
import ro.example.lab.data.Station;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 8080;

    private TextView mTextViewPeekTitle;
    private TextView mTextViewPeekOpen;
    private TextView mTextViewPeekTime;
    private TextView mTextViewContentAddress;
    private TextView mTextViewContentOpenHours;
    private TextView mTextViewContentCall;
    private BottomSheetBehavior mBottomSheetBehavior;

    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private List<Station> mStations;
    private MapViewModel viewModel;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // Init BottomSheet for Info Station
        View bottomSheet = rootView.findViewById(R.id.bottom_sheet);
        mTextViewPeekTitle = bottomSheet.findViewById(R.id.textView_peek_title);
        mTextViewPeekOpen = bottomSheet.findViewById(R.id.textView_peek_open);
        mTextViewPeekTime = bottomSheet.findViewById(R.id.textView_peek_time);
        mTextViewContentAddress = bottomSheet.findViewById(R.id.textView_content_address);
        mTextViewContentOpenHours = bottomSheet.findViewById(R.id.textView_content_open_hours);
        mTextViewContentCall = bottomSheet.findViewById(R.id.textView_content_call);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mStations = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(10);
        locationRequest.setInterval(60000); // Update location every 1 minute
        locationRequest.setFastestInterval(10000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                if (locationResult.getLastLocation() != null) {
                    LatLng current = new LatLng(locationResult.getLastLocation().getLatitude(),
                                                locationResult.getLastLocation().getLongitude());
                    mMap.addMarker(new MarkerOptions().position(current));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 12));
                }

            }
        };

        getLocationPermission();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //configureViewModel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
        getCurrentLocation();
        updateLocationUI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        configureViewModel();
        mMap.setOnMarkerClickListener(this);

        // Add a marker in Sydney, Australia, and move the camera.
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onMarkerClick(final Marker marker) {
        Integer stationId = (Integer) marker.getTag();
        if (stationId != null) {
            Toast.makeText(getActivity(), "Clicked " + stationId, Toast.LENGTH_SHORT).show();
            mTextViewPeekTitle.setText(String.format("%s Station %s",
                                                     mStations.get(stationId).getBrand_id(),
                                                     mStations.get(stationId).getNational_code()));
            mTextViewPeekOpen.setText(parseStringOpenClose(mStations.get(stationId).getOpening_hours()));
            mTextViewPeekTime.setText(String.format("Estimated Time from your Location - %d mins", 10));

            mTextViewContentAddress.setText(String.format("%s; %s",
                                                          mStations.get(stationId).getAddress_l(),
                                                          mStations.get(stationId).getTown_l()));
            mTextViewContentOpenHours.setText(parseStringOpeningHours(mStations.get(stationId).getOpen_hours()));
            mTextViewContentCall.setText(mStations.get(stationId).getTelnr());

            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        return false;
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                                              android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                                              new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                              PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                                              android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        } else {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Timber.tag("Exception: %s").e(e);
        }
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        viewModel.init("RO");
        viewModel.getCountryStations().observe(this, this::updateUIWithData);
    }

    private void updateUIWithData(List<Station> stations) {
        if (stations != null) {
            mStations.clear();
            mStations.addAll(stations);
            for (int i = 0; i < mStations.size(); i++) {
                LatLng latLng = new LatLng(mStations.get(i).getY(), mStations.get(i).getX());
                mMap.addMarker(new MarkerOptions()
                                       .position(latLng)
                                       .icon(BitmapDescriptorFactory.fromResource(R.drawable.gasstation)))
                    .setTag(i);
            }
        }
    }

    private String parseStringOpeningHours(String openHours) {
        return openHours.replace(";", "\n\n");
    }

    private String parseStringOpenClose(String openingHours) {
        Calendar calendar = Calendar.getInstance();
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String[] daysTokens = openingHours.split("#");
        String[] currentDayTokens = daysTokens[currentDayOfWeek - 1].split(",");

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(calendar.getTime());
        String startTime = currentDayTokens[2].substring(5);
        String endTime = currentDayTokens[3].substring(3);

        String infoOpenClose;
        try {
            if (currentDayTokens[1].equals("closed=FALSE") &&
                dateFormat.parse(currentTime).after(dateFormat.parse(startTime)) &&
                dateFormat.parse(currentTime).before(dateFormat.parse(endTime))) {
                infoOpenClose = "Open - Closes " + endTime;
            } else {
                infoOpenClose = "Close";
            }
        } catch (ParseException e) {
            infoOpenClose = "No info";
            Timber.e("Time Parse Exception!!!");
        }

        return infoOpenClose;
    }
}
