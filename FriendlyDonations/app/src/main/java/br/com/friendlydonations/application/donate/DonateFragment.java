package br.com.friendlydonations.application.donate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.jakewharton.rxbinding.view.RxView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseFragment;
import br.com.friendlydonations.shared.views.ApplicationDialogFragment;
import br.com.friendlydonations.shared.views.RxTextViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class DonateFragment extends BaseFragment implements DonateView {

    private static final String TAG = DonateFragment.class.getSimpleName();
    private static final int PLACE_LOCATION_RESULT = 1000;

    protected View rootView;
    @BindView(R.id.recycler_view_pictures)
    RecyclerView recyclerViewPictures;
    @BindView(R.id.recycler_view_donations)
    RecyclerView recyclerViewDonations;
    @BindView(R.id.item_text)
    AppCompatEditText itemText;
    @BindView(R.id.item_layout)
    TextInputLayout itemLayout;
    @BindView(R.id.description_text)
    AppCompatEditText descriptionText;
    @BindView(R.id.description_layout)
    TextInputLayout descriptionLayout;
    @BindView(R.id.location_text)
    AppCompatEditText locationText;
    @BindView(R.id.location_layout)
    TextInputLayout locationLayout;
    @BindView(R.id.delivery_text)
    AppCompatEditText deliveryText;
    @BindView(R.id.delivery_layout)
    TextInputLayout deliveryLayout;

    private DonatePresenter presenter;
    private Context context;

    public DonateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_donate, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();
        presenter = new DonatePresenter(this);
        initialize();
        return rootView;
    }

    private void initialize() {
        RxView.clicks(locationText).subscribe(onPlaceIntentAction, throwableLocation);
    }

    private Action1<Throwable> throwableLocation = throwable -> presenter.createGooglePlayServicesDialogError(throwable);

    private Action1<Void> onPlaceIntentAction = result -> {
        Intent intent = null;
        try {
            intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(getActivity());
            startActivityForResult(intent, PLACE_LOCATION_RESULT);
        } catch (GooglePlayServicesRepairableException e) {
            throwableLocation.call(e);
        } catch (GooglePlayServicesNotAvailableException e) {
            throwableLocation.call(e);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PLACE_LOCATION_RESULT:
                verifyPlaceAutocompleteAnswer(resultCode, data);
                break;
        }
    }

    private void verifyPlaceAutocompleteAnswer(int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                presenter.onPlaceApiAnswer(PlaceAutocomplete.getPlace(context, data));
                break;
            case PlaceAutocomplete.RESULT_ERROR:
                presenter.onPlaceApiAnswerError(PlaceAutocomplete.getStatus(context, data));
                break;
            case Activity.RESULT_CANCELED:
                presenter.onPlaceApiAnswerCanceled();
                break;
            default:
                presenter.onPlaceApiAnswerUnknown();
                break;
        }
    }

    @Override
    public void emitGooglePlayServicesError(int titleId, int contentId, int yesId, boolean isScrollable) {
        ApplicationDialogFragment applicationDialogFragment =
                new ApplicationDialogFragment(getString(titleId), getString(contentId), null, getString(yesId), isScrollable);
        applicationDialogFragment.show(getActivity().getFragmentManager(), TAG);
    }

}
