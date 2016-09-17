package br.com.friendlydonations.views.activities;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.utils.TypefaceMaker;
import br.com.friendlydonations.views.adapters.DynamicPageAdapterImages;
import br.com.friendlydonations.views.widgets.ScaleCircleNavigator;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 9/7/16.
 */
public class DonationDetailActivity extends BaseActivity{

    @BindView(R.id.tvDonateName) protected TextView tvDonateName;
    @BindView(R.id.tvDonateLocation) protected TextView tvDonateLocation;
    @BindView(R.id.tvDonateDescription) protected TextView tvDonateDescription;
    @BindView(R.id.tvDonateSeeMore) protected TextView tvDonateSeeMore;
    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @BindView(R.id.viewPager) protected ViewPager viewPager;
    @BindView(R.id.circlePage) protected MagicIndicator circlePage;
    SupportMapFragment mapFragment;

    @BindView(R.id.tvImInterestedText) protected TextView tvImInterestedText;


    protected DynamicPageAdapterImages dynamicPageAdapter;
    private List<View> mViews = new ArrayList<>();

    // Typefaces
    protected Typeface mRobotoRegular;
    protected Typeface mRobotoMedium;
    protected Typeface mRobotoLight;
    protected Typeface mMonserratRegular;
    protected Typeface mMonserratBold;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);
        ButterKnife.bind(this);
        setupTypefaces();
        initUI();
    }

    @Override
    public void initUI() {
        setupToolbar(toolbar, "", "", true, true );
        applyCloseMenu();
        tvDonateLocation.setText(String.format(getResources().getString(R.string.donate_detail_location), "São José dos Campos, SP, Brazil"));
        dynamicPageAdapter = new DynamicPageAdapterImages(mViews);


        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        circlePage.setNavigator(scaleCircleNavigator);


        viewPager.setAdapter(dynamicPageAdapter);
        ViewPagerHelper.bind(circlePage, viewPager);


        // Only to test
        mViews.clear();
        //Picasso.with(this).load(R.drawable.ic_picture_bike).into(mImageView);
        mViews.add(LayoutInflater.from(this).inflate(R.layout.image_view_pager, null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.image_view_pager, null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.image_view_pager, null));

        scaleCircleNavigator.setCircleCount(mViews.size());

        //mViews.add(mImageView);

        dynamicPageAdapter.notifyDataSetChanged();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng mCoords = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(mCoords).title("Marker in My Coords"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(mCoords));

                CameraPosition mCameraPosition = new CameraPosition.Builder()
                        .target(mCoords)
                        .zoom(15)
                        .tilt(45)
                        .bearing(0)
                        .build();

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
            }
        });
    }

    protected void applyCloseMenu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu_close_x));
            } catch (Exception ex) {

            }

        }
    }

    @Override
    public void setupTypefaces() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mMonserratRegular = TypefaceMaker.createTypeFace(DonationDetailActivity.this, TypefaceMaker.FontFamily.MontserratRegular);
                mMonserratBold = TypefaceMaker.createTypeFace(DonationDetailActivity.this, TypefaceMaker.FontFamily.MontserratBold);
                mRobotoRegular = TypefaceMaker.createTypeFace(DonationDetailActivity.this, TypefaceMaker.FontFamily.RobotoRegular);
                mRobotoMedium = TypefaceMaker.createTypeFace(DonationDetailActivity.this, TypefaceMaker.FontFamily.RobotoMedium);
                mRobotoLight = TypefaceMaker.createTypeFace(DonationDetailActivity.this, TypefaceMaker.FontFamily.RobotoLight);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setTypeface(mMonserratBold, tvDonateName);
                setTypeface(mRobotoLight, tvDonateLocation);
                setTypeface(mRobotoRegular, tvDonateDescription);
                setTypeface(mRobotoMedium, tvDonateSeeMore);
                setTypeface(mMonserratRegular, tvImInterestedText);
            }
        }.execute();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
