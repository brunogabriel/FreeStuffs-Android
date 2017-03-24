package br.com.friendlydonations.application.account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;

import java.io.File;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.donate.DonateFragment;
import br.com.friendlydonations.application.webview.ActivityWebView;
import br.com.friendlydonations.shared.BaseActivity;
import br.com.friendlydonations.shared.CameraGalleryHelper;
import br.com.friendlydonations.shared.PermissionsHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

import static br.com.friendlydonations.application.webview.ActivityWebView.PATH_EXTRA;
import static br.com.friendlydonations.application.webview.ActivityWebView.TITLE_EXTRA;
import static br.com.friendlydonations.shared.CameraGalleryHelper.CAMERA_CODE;
import static br.com.friendlydonations.shared.CameraGalleryHelper.GALLERY_CODE;

/**
 * Created by brunogabriel on 16/03/17.
 */
public class CreateAccountActivity extends BaseActivity implements AccountView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.name_text)
    AppCompatEditText nameText;

    @BindView(R.id.name_layout)
    TextInputLayout nameLayout;

    @BindView(R.id.email_text)
    AppCompatEditText emailText;

    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;

    @BindView(R.id.who_text)
    AppCompatEditText whoText;

    @BindView(R.id.who_layout)
    TextInputLayout whoLayout;

    @BindView(R.id.agree_checkbox)
    AppCompatCheckBox agreeCheckbox;

    @BindView(R.id.terms_text)
    TextView termsText;

    @BindView(R.id.form_linear_layout)
    LinearLayout formLinearLayout;

    @BindView(R.id.create_account_button)
    AppCompatButton createAccountButton;


    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private AccountPresenter presenter;
    private CameraGalleryHelper cameraGalleryHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar, getString(R.string.create_account_title), null, true, true);
        initUI();
        presenter = new AccountPresenter(this);
        cameraGalleryHelper = new CameraGalleryHelper(this,
                new PermissionsHelper(this),
                () -> presenter.openCamera(),
                () -> presenter.openGallery());
    }

    private void initUI() {
        setupTerms();
    }

    // View Actions
    @OnClick(R.id.terms_text)
    protected void onClickTerms () {
        startActivity(new Intent(this, ActivityWebView.class)
                .putExtra(TITLE_EXTRA, getString(R.string.terms_privacy_title))
                .putExtra(PATH_EXTRA, getString(R.string.terms_privacy_path)));
    }

    @OnClick(R.id.profile_image)
    protected void onClickProfileImage() {
        presenter.tryToChangeProfileImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Custom
    private void setupTerms() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(getString(R.string.account_agree_not_filled))
                .append(" " + getString(R.string.terms), new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/Roboto-Bold.ttf")), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .append(" " + getString(R.string.and))
                .append(" " + getString(R.string.privacy), new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/Roboto-Bold.ttf")), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsText.setText(builder);
    }

    @Override
    public void showImageUpdater() {
        cameraGalleryHelper.showChooseCameraOrGallery();
    }

    @Override
    public void openDeviceCamera() {
        startActivityForResult(cameraGalleryHelper.createCameraIntent(), CAMERA_CODE);
    }

    @Override
    public void openDeviceGallery() {
        startActivityForResult(cameraGalleryHelper.createGalleryIntent(), GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CODE:
                    File cameraFile = cameraGalleryHelper.createMediaFile();
                    break;
                case GALLERY_CODE:
                    int y = 10;
                    break;
            }
        }
    }

    private void callCrop(Uri sourceUri, Uri destinyUri) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        UCrop.of(sourceUri, destinyUri)
                .withAspectRatio(4, 3)
                .withOptions(options)
                .start(this);
    }
}
