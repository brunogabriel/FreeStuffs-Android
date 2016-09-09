package br.com.friendlydonations.views.fragments;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseFragment;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class MapLocationFragment extends BaseFragment {

    // Views
    protected View rootView;


    MapView mMapView;
    private GoogleMap googleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        initUI();
        return rootView;
    }

    @Override
    public void initUI() {


        try {
            mMapView.onResume();// needed to get the map to display immediately
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_homefragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            /* case R.id.action_exit:
                mainActivity.doExit();
                return true; */
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mMapView!=null)
            mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mMapView!=null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMapView!=null)
            mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mMapView!=null)
            mMapView.onLowMemory();
    }


}
