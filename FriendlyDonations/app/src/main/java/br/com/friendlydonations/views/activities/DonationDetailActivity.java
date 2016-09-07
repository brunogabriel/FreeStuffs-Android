package br.com.friendlydonations.views.activities;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.utils.TypefaceMaker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 9/7/16.
 */
public class DonationDetailActivity extends BaseActivity {

    @BindView(R.id.tvDonateName)
    protected TextView tvDonateName;

    @BindView(R.id.tvDonateLocation)
    protected TextView tvDonateLocation;

    @BindView(R.id.tvDonateDescription)
    protected TextView tvDonateDescription;

    @BindView(R.id.tvDonateSeeMore)
    protected TextView tvDonateSeeMore;

    @BindView(R.id.viewPager) protected ViewPager viewPager;
    @BindView(R.id.circlePage) protected CirclePageIndicator circlePage;
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
        tvDonateLocation.setText(String.format(getResources().getString(R.string.donate_detail_location), "São José dos Campos, SP, Brazil"));
        dynamicPageAdapter = new DynamicPageAdapterImages(mViews);
        viewPager.setAdapter(dynamicPageAdapter);
        circlePage.setViewPager(viewPager);

        // Only to test
        mViews.clear();
        //Picasso.with(this).load(R.drawable.ic_picture_bike).into(mImageView);
        mViews.add(LayoutInflater.from(this).inflate(R.layout.image_view_pager, null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.image_view_pager, null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.image_view_pager, null));

        //mViews.add(mImageView);

        dynamicPageAdapter.notifyDataSetChanged();
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
            }
        }.execute();
    }

    class DynamicPageAdapterImages extends PagerAdapter {
        private List<View> mViews;

        public DynamicPageAdapterImages(List<View> mViews) {
            this.mViews = mViews;
        }

        @Override
        public int getCount() {
            return this.mViews == null ? 0 : this.mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mViews.get(position), 0);
            return mViews.get(position);
        }
    }
}
